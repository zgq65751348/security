package org.peak.myth.security.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 * windows 7  旗舰版
 *
 * @author 墨茗琦妙
 * @since 2019/10/23 0023 下午 5:00
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseBody<T> {

    private static final long serialVersionUID = -3032060746893382446L;

    // 错误码
    private Integer code;

    // 提示信息
    private String msg;

    // 具体内容
    private T data;
}
