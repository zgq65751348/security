package org.peak.myth.security.aspect;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.peak.myth.security.enums.ResultEnum;
import org.peak.myth.security.exception.ServerException;
import org.peak.myth.security.response.DealController;
import org.peak.myth.security.utils.JWTUtil;
import org.peak.myth.security.utils.JsonStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>
 * 验证token 获取请求服务器的主机IP
 * </p>
 * windows 7  旗舰版
 *
 * @author 墨茗琦妙
 * @since 2019/10/23 0023 下午 4:39
 */
@Component
@Aspect
@Slf4j
public class AuthorizeAspect {

	@Value("${spring.profiles.active}")
	private String env;//当前激活的配置文件

	@Resource
	private HttpServletRequest httpServletRequest;

	private static final String tokenName = "Authorization";

	/**
	 * 拦截接口除了登录/注册接口
	 */
	@Pointcut("execution(public * org.peak.myth.security.controller.*Controller.*(..))")
            /*"&& !execution(public * com.company.fastsbapi.controller.UserController.userLogin(..))" +
            "&& !execution(public * com.company.fastsbapi.controller.UserController.userRegister(..))" )*/
	public void verifyAuthorize() {
	}

	@Before("verifyAuthorize()")
	public void doVerify() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		request.setAttribute("start_time", System.currentTimeMillis());
		if (!"dev".equals(env)) { //开发环境不进行auth认证
			try {
				if (JWTUtil.parseJWT(request.getHeader(tokenName)) == null) {
					log.warn("[墨茗琦妙] 温馨提示：签名认证失败！________________请求路径：{}________________请求IP：{}______________请求参数：{}"
							, request.getRequestURI(), getIpAddress(request), JSON.toJSONString(request.getParameterMap()));
					throw new ServerException(ResultEnum.AUTH_ERROR);
				}
			} catch (IllegalArgumentException e) { //说明没有传Header-Authorization
				throw new ServerException(403, "没有认证");
			} catch (SignatureException e) {
				throw new ServerException(403, "token有误");
			} catch (ExpiredJwtException e) {
				throw new ServerException(403, "认证失效已过期，请重新登录");
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServerException(ResultEnum.AUTH_ERROR);
			}
		}
	}

	@After("verifyAuthorize()")
	public void afterVerify() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		long startTime = (long) request.getAttribute("start_time");
		request.removeAttribute("start_time");
		long endTime = System.currentTimeMillis();
		Map<String, Object> map = DealController.getParameterMap(httpServletRequest);
		System.out.println("\n通过Map.entrySet使用iterator遍历key和value: ");
		System.out.println(map.size());
		Iterator map1it = map.entrySet().iterator();
		while (map1it.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) map1it.next();
			System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
		}
		log.info("[墨茗琦妙] 实时监听-->主机地址:<**{}**>_______ 请求方式:**[{}]**_______请求路径:** [{}] **_______处理时间：**{}**ms_______", getIpAddress(request), request.getMethod(), request.getRequestURI(), endTime - startTime);
		log.info("[墨茗琦妙] 实时监听-->请求编码:**{}**_______ 请求协议:**[{}]**_______响应语言** [{}] **_请求参数：{}", request.getCharacterEncoding(), request.getScheme(), request.getLocale(), JSON.toJSONString(request.getParameterMap()));
	}


	private String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		// 如果是多级代理，那么取第一个ip为客户端ip
		if (ip != null && ip.indexOf(",") != -1) {
			ip = ip.substring(0, ip.indexOf(",")).trim();
		}

		return ip;
	}


}
