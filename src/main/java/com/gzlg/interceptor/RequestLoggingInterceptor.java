package com.gzlg.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 请求日志拦截器
 * <p>
 * 基于 Spring MVC 的 HandlerInterceptor 机制，在每个 HTTP 请求的入口和出口自动记录日志，
 * 无需在每个 Controller 方法中手动编写日志，减少重复代码。
 * <p>
 * 记录内容：
 * <ul>
 *   <li>请求开始：HTTP 方法、请求路径、客户端 IP</li>
 *   <li>请求完成：HTTP 方法、请求路径、响应状态码、耗时（毫秒）</li>
 *   <li>请求异常：同上，额外附带异常堆栈</li>
 * </ul>
 * <p>
 * 日志输出示例：
 * <pre>
 *   INFO  >>> 请求开始: GET /emps | IP: 192.168.1.100
 *   INFO  <<< 请求完成: GET /emps | 状态: 200 | 耗时: 45ms
 * </pre>
 *
 * @author gzlg
 * @see com.gzlg.config.WebConfig
 */
@Slf4j
@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    /**
     * 请求开始时间的 request 属性键名
     * <p>
     * 在 preHandle 中存入，在 afterCompletion 中取出用于计算耗时
     */
    private static final String START_TIME_ATTR = "requestStartTime";

    /**
     * 请求处理前回调
     * <p>
     * 记录请求开始时间和基本信息，返回 true 表示继续执行后续拦截器和 Controller
     *
     * @param request  当前 HTTP 请求
     * @param response 当前 HTTP 响应
     * @param handler  即将执行的 Controller 方法
     * @return true-继续执行后续流程；false-中断请求
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute(START_TIME_ATTR, System.currentTimeMillis());
        log.info(">>> 请求开始: {} {} | IP: {}", request.getMethod(), request.getRequestURI(), getClientIp(request));
        return true;
    }

    /**
     * 请求完成回调（无论成功或异常都会执行）
     * <p>
     * 计算请求总耗时，根据是否有异常分别以不同级别记录日志：
     * - 正常完成：INFO 级别
     * - 发生异常：ERROR 级别（附带异常堆栈）
     *
     * @param request  当前 HTTP 请求
     * @param response 当前 HTTP 响应
     * @param handler  执行的 Controller 方法
     * @param ex       请求处理过程中抛出的异常（无异常时为 null）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Long startTime = (Long) request.getAttribute(START_TIME_ATTR);
        long duration = startTime != null ? System.currentTimeMillis() - startTime : -1;
        if (ex != null) {
            log.error("<<< 请求异常: {} {} | 状态: {} | 耗时: {}ms", request.getMethod(), request.getRequestURI(), response.getStatus(), duration, ex);
        } else {
            log.info("<<< 请求完成: {} {} | 状态: {} | 耗时: {}ms", request.getMethod(), request.getRequestURI(), response.getStatus(), duration);
        }
    }

    /**
     * 获取客户端真实 IP 地址
     * <p>
     * 优先从反向代理头中获取真实 IP，兼容 Nginx 等反向代理场景：
     * 1. X-Forwarded-For：标准代理头，可能包含多个 IP（取第一个）
     * 2. X-Real-IP：Nginx 常用的代理头
     * 3. request.getRemoteAddr()：直连时的客户端 IP
     *
     * @param request HTTP 请求
     * @return 客户端 IP 地址
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
