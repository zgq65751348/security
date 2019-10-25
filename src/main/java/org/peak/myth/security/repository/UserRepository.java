package org.peak.myth.security.repository;

import org.peak.myth.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * <p>
 *
 * </p>
 * windows 7  旗舰版
 *
 * @author 墨茗琦妙
 * @since 2019/10/23 0023 下午 5:08
 */

public interface UserRepository  extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
}
