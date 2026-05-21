package com.gzlg.exception;

import lombok.Getter;

/**
 * 自定义业务异常
 * <p>
 * 用于封装业务逻辑中可预见的异常情况，例如：参数不合法、数据不存在、操作不允许等。
 * 与系统异常（如 NullPointerException、数据库连接失败）不同，业务异常是"预期内"的错误，
 * 通常由开发人员主动抛出，由 {@link GlobalExceptionHandler} 统一捕获后返回友好的错误提示。
 * <p>
 * 使用示例：
 * <pre>
 *     if (dept == null) {
 *         throw new BusinessException("部门不存在");
 *     }
 * </pre>
 *
 * @author gzlg
 * @see GlobalExceptionHandler
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 错误码，默认为 0（表示业务失败）
     * <p>
     * 可根据业务需要扩展不同的错误码，例如：
     * - 0: 通用业务失败
     * - 1001: 用户未登录
     * - 1002: 权限不足
     */
    private final Integer code;

    /**
     * 仅指定错误消息，错误码默认为 0
     *
     * @param message 错误描述信息，会返回给前端展示
     */
    public BusinessException(String message) {
        super(message);
        this.code = 0;
    }

    /**
     * 指定错误码和错误消息
     *
     * @param code    自定义错误码，便于前端根据错误码做差异化处理
     * @param message 错误描述信息
     */
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 指定错误消息和原始异常原因
     * <p>
     * 当业务异常由其他异常触发时使用，保留原始异常堆栈便于排查
     *
     * @param message 错误描述信息
     * @param cause   原始异常
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code = 0;
    }
}
