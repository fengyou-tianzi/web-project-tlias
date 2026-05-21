package com.gzlg.exception;

import com.gzlg.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.stream.Collectors;

/**
 * 全局异常处理器
 * <p>
 * 基于 Spring 的 @RestControllerAdvice 机制，统一拦截 Controller 层抛出的所有异常，
 * 避免在每个 Controller 方法中重复编写 try-catch，保证所有接口返回统一的 {@link Result} 格式。
 * <p>
 * 处理优先级（从具体到通用）：
 * <ol>
 *   <li>{@link BusinessException} — 业务异常（预期内），返回用户友好的错误提示</li>
 *   <li>{@link MethodArgumentNotValidException} — 参数校验失败（@Valid 触发）</li>
 *   <li>{@link MethodArgumentTypeMismatchException} — 参数类型不匹配（如期望 Integer 传入 String）</li>
 *   <li>{@link NoResourceFoundException} — 请求的资源路径不存在</li>
 *   <li>{@link IllegalArgumentException} — 非法参数</li>
 *   <li>{@link Exception} — 兜底处理，捕获所有未预期的系统异常</li>
 * </ol>
 * <p>
 * 日志记录策略：
 * - 业务类异常（预期内）：使用 WARN 级别，仅记录消息不记录堆栈
 * - 系统类异常（未预期）：使用 ERROR 级别，记录完整堆栈便于排查
 *
 * @author gzlg
 * @see BusinessException
 * @see Result
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     * <p>
     * 业务异常是开发人员主动抛出的"预期内"异常，不需要记录堆栈，
     * 仅以 WARN 级别记录消息即可，避免日志刷屏
     *
     * @param e 业务异常
     * @return 统一响应结果，包含异常消息
     */
    @ExceptionHandler(BusinessException.class)
    public Result handleBusinessException(BusinessException e) {
        log.warn("业务异常: {}", e.getMessage());
        return Result.error(e.getMessage());
    }

    /**
     * 处理参数校验失败异常
     * <p>
     * 当使用 @Valid + 校验注解（如 @NotBlank、@Size）校验请求参数时，
     * 校验不通过会抛出此异常。将所有字段错误拼接后返回
     *
     * @param e 校验异常
     * @return 统一响应结果，包含所有字段的校验错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleValidationException(MethodArgumentNotValidException e) {
        String errors = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining("; "));
        log.warn("参数校验失败: {}", errors);
        return Result.error("参数校验失败: " + errors);
    }

    /**
     * 处理参数类型不匹配异常
     * <p>
     * 例如接口期望接收 Integer 类型参数，但前端传入了非数字字符串
     *
     * @param e 类型不匹配异常
     * @return 统一响应结果，提示参数类型错误
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.warn("参数类型不匹配: 参数名={}, 期望类型={}", e.getName(), e.getRequiredType());
        return Result.error("参数类型不匹配: " + e.getName());
    }

    /**
     * 处理请求资源不存在异常
     * <p>
     * 当请求的 URL 路径没有对应的 Controller 映射时抛出
     *
     * @param e 资源不存在异常
     * @return 统一响应结果，提示资源不存在
     */
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result handleNoResourceFoundException(NoResourceFoundException e) {
        log.warn("请求资源不存在: {}", e.getResourcePath());
        return Result.error("请求资源不存在");
    }

    /**
     * 处理非法参数异常
     * <p>
     * 通常由手动抛出 new IllegalArgumentException("xxx") 触发
     *
     * @param e 非法参数异常
     * @return 统一响应结果，包含异常消息
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("非法参数: {}", e.getMessage());
        return Result.error(e.getMessage());
    }

    /**
     * 兜底异常处理 — 捕获所有未预期的系统异常
     * <p>
     * 这是最后一道防线，任何未被上面方法捕获的异常都会走到这里。
     * 必须以 ERROR 级别记录完整堆栈，因为这类异常通常是 Bug，需要通过日志定位问题。
     * <p>
     * 注意：不要将异常堆栈信息返回给前端，避免泄露服务器内部信息（安全风险）
     *
     * @param e 未预期的异常
     * @return 统一响应结果，返回通用错误提示
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleException(Exception e) {
        log.error("系统异常: ", e);
        return Result.error("服务器内部错误，请联系管理员");
    }
}
