package org.peak.myth.security.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 *
 * </p>
 * windows 7  旗舰版
 *
 * @author 墨茗琦妙
 * @since 2019/10/23 0023 下午 4:42
 */
@AllArgsConstructor
@Getter
public enum UserRoleEnum {

    ADMIN(0, "管理员"),
    NORMAL(1, "普通用户"),
    ;

    private Integer code;

    private String message;
}
