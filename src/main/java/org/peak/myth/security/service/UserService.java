package org.peak.myth.security.service;

import lombok.extern.slf4j.Slf4j;
import org.peak.myth.security.entity.User;
import org.peak.myth.security.enums.ResultEnum;
import org.peak.myth.security.exception.ServerException;
import org.peak.myth.security.repository.UserRepository;
import org.peak.myth.security.response.ResponseBody;
import org.peak.myth.security.response.ResponseUtil;
import org.peak.myth.security.utils.BCrypt;
import org.peak.myth.security.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;
import org.thymeleaf.util.StringUtils;
import com.alibaba.fastjson.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 * windows 7  旗舰版
 *
 * @author 墨茗琦妙
 * @since 2019/10/23 0023 下午 5:15
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User register(User user){
        //根据用户名查找该用户名是否已近注册
       List<User> users = userRepository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("username").as(String.class),user.getUsername());
            }
        });
        if(!ListUtils.isEmpty(users)){
            throw new ServerException(ResultEnum.USER_IS_EXISTED);
        }
        String salt = BCrypt.gensalt();
        String hashed = BCrypt.hashpw(user.getPassword(), salt);
        user.setPassword(hashed);
        return userRepository.save(user);
    }

    public ResponseBody login(User user)  {
        User sqlUser = userRepository.findOne(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.equal(root.get("username").as(String.class),user.getUsername())) ;
                predicates.add(criteriaBuilder.equal(root.get("role").as(Integer.class),user.getRole()));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }).orElse(new User());
        if(StringUtils.isEmpty(sqlUser.getUsername())){
            throw new ServerException(ResultEnum.ERROR_LOGIN);
        }
        String password = user.getPassword();
        log.info("用户输入密码为：{}",password);
        String hashPassword = sqlUser.getPassword();
        log.info("数据库密码为：{}",hashPassword);
        String salt = hashPassword.substring(0, 29);
        log.info("截取加密密码为：{}",salt);
        String hashed = BCrypt.hashpw(password, salt);
        log.info("用户输入加密密码为：{}",hashed);
        Map<String, Object> resMap = new HashMap<>();
        if (hashed.equals(hashPassword)) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", sqlUser.getId());
            String token = null;
            try {
                token = JWTUtil.createJWT(JSON.toJSONString(map));
            } catch (Exception e) {
                log.error("【JWT加密】JWT加密过程中出现错误,subject:{}", JSON.toJSONString(map));
                e.printStackTrace();
            }
            resMap.put("token", token);
            resMap.put("id", sqlUser.getId());
            return ResponseUtil.returnSuccess(resMap);
        }
        return ResponseUtil.returnFail(ResultEnum.AUTH_ERROR);

    }
}
