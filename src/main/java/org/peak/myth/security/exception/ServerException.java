package org.peak.myth.security.exception;

import lombok.Getter;
import org.peak.myth.security.enums.ResultEnum;

/**
 * <p>
 *  响应异常处理
 * </p>
 * windows 7  旗舰版
 *
 * @author 墨茗琦妙
 * @since 2019/10/23 0023 下午 4:43
 */
@Getter
public class ServerException extends RuntimeException {

    private Integer code;

    public ServerException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public ServerException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
