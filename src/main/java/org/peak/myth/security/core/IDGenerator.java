package org.peak.myth.security.core;

import org.hibernate.id.IdentityGenerator;

/**
 * <p>
 *
 * </p>
 * windows 7  旗舰版
 *
 * @author 墨茗琦妙
 * @since 2019/10/23 0023 下午 4:30
 */
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import java.io.Serializable;

public class IDGenerator extends IdentityGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws MappingException {
        Object primaryKey = PrimaryKeyGenerationAlgorithm.nextId();
        if (null != primaryKey) {
            return (Serializable) primaryKey;
        }
        return super.generate(session, object);
    }
}
