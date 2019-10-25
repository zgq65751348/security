package org.peak.myth.security.response;

import org.peak.myth.security.enums.ResultEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  响应客户端工具类
 * </p>
 * windows 7  旗舰版
 *
 * @author 墨茗琦妙
 * @since 2019/10/23 0023 下午 5:04
 */

public class ResponseUtil {

    public static ResponseBody returnSuccess(Object object) {
        ResponseBody body = new ResponseBody();
        body.setCode(0);
        body.setMsg("success");
        body.setData(object);
        return body;
    }

    public static ResponseBody returnSuccess(String key, Object object) {
        ResponseBody body = new ResponseBody();
        body.setCode(0);
        body.setMsg("success");
        Map<String, Object> map = new HashMap<>();
        map.put(key, object);
        body.setData(map);
        return body;
    }

    public static ResponseBody returnSuccess() {
        return returnSuccess(null);
    }

    public static ResponseBody returnFail(Integer code, String msg) {
        ResponseBody body = new ResponseBody();
        body.setCode(code);
        body.setMsg(msg);
        return body;
    }

    public static ResponseBody returnFail() {
        return returnFail(ResultEnum.FAIL);
    }

    public static ResponseBody returnFail(ResultEnum resultEnum) {
        ResponseBody body = new ResponseBody();
        body.setCode(resultEnum.getCode());
        body.setMsg(resultEnum.getMessage());
        return body;
    }
}
