package org.peak.myth.security.utils;

import org.peak.myth.security.exception.ServerException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * <p>
 *  请求对象验证工具
 * </p>
 * windows 7  旗舰版
 *
 * @author 墨茗琦妙
 * @since 2019/10/23 0023 下午 6:20
 */

public class RequestVerifyUtil {

    public static void valid(BindingResult result) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                throw new ServerException(6000, error.getDefaultMessage());
            }
        }
    }
}
