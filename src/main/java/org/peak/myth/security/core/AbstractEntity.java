package org.peak.myth.security.core;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * <p>
 *
 * </p>
 * windows 7  旗舰版
 *
 * @author 墨茗琦妙
 * @since 2019/10/23 0023 下午 4:20
 */
@Data
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    private static final long serialVersionUID = -4505117821220216969L;

    @Id
    @GeneratedValue(generator = "PrimaryKey")
    @GenericGenerator(name = "PrimaryKey", strategy = "org.peak.myth.security.core.IDGenerator")
    @Basic(optional = false)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GTM+8")
    @Column(name = "create_time", columnDefinition = "datetime  COMMENT '创建时间'",updatable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GTM+8")
    @Column(name = "update_time", columnDefinition = "datetime  COMMENT '修改时间'")
    private Date updateTime;

    /**
     * 数据插入前的操作
     */
    @PrePersist
    public void setInsertBefore() {
        this.createTime = new Date();
        this.updateTime = new Date();
    }

    /**
     * 数据修改前的操作
     */
    @PreUpdate
    public void setUpdateBefore() {
        this.updateTime = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity that = (AbstractEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
