package org.peak.myth.security.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import org.peak.myth.security.core.AbstractEntity;
import org.peak.myth.security.enums.UserRoleEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * <p>
 *
 * </p>
 * windows 7  旗舰版
 *
 * @author 墨茗琦妙
 * @since 2019/10/23 0023 下午 4:53
 */
@Entity
@DynamicUpdate
@Table
@Getter
@Setter
public class User extends AbstractEntity {

    @NotNull(message = "用户名不能为空")
    @Column(name = "username", columnDefinition = "varchar(200)  COMMENT '用户名'",updatable=false)
    private String username;

    @NotNull(message = "密码不能为空")
    @Length(min=6, message="密码长度不能少于6位")
    @Column(name = "password", columnDefinition = "varchar(200)  COMMENT '密码'")
    private String password;

    @Column(name = "email", columnDefinition = "varchar(200)  COMMENT '邮箱地址'")
    private String email;

    @Column(name = "phone", columnDefinition = "varchar(200)  COMMENT '电话号码'")
    private String phone;

    @NotNull(message = "请选择登录角色权限")
    @Column(name = "role", columnDefinition = "tinyint(2)  COMMENT '用户权限  0 超级管理员  1 普通用户'")
    private Integer role = UserRoleEnum.NORMAL.getCode();
}
