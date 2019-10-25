package org.peak.myth.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.peak.myth.security.enums.ResultEnum;
import org.peak.myth.security.exception.ServerException;
import org.peak.myth.security.response.ResponseUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *
 * </p>
 * windows 7  旗舰版
 *
 * @author 墨茗琦妙
 * @since 2019/10/23 0023 下午 5:02
 */
@ControllerAdvice
@Slf4j
public class AllExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public org.peak.myth.security.response.ResponseBody handlerException(HttpServletRequest request, Exception e) {
        log.error("【异常拦截】" + "[" + request.getRequestURI() + "]" + "访问出现错误,错误原因：" + e.getMessage());
        if (e instanceof ServerException) { //业务异常 如账号密码错误
            return ResponseUtil.returnFail(((ServerException) e).getCode(), e.getMessage());
        } else if (e instanceof NoHandlerFoundException) { //404接口不存在
            return ResponseUtil.returnFail(ResultEnum.NO_HANDLER.getCode(), ResultEnum.NO_HANDLER.getMessage());
        } else if (e instanceof ServerException) { //400接口报错
            return ResponseUtil.returnFail(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMessage());
        } else { //500错误
            log.error("", e);
            return ResponseUtil.returnFail(ResultEnum.SERVER_ERROR.getCode(), ResultEnum.SERVER_ERROR.getMessage());
        }
    }

}
