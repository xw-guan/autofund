package com.xwguan.autofund.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xwguan.autofund.dao.common.BaseDao;
import com.xwguan.autofund.dao.common.CUDBatchDao;
import com.xwguan.autofund.dto.common.Page;
import com.xwguan.autofund.entity.user.User;
import com.xwguan.autofund.enums.UserTypeEnum;

/**
 * TODO mapper未实现
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-10
 */
public interface UserDao extends BaseDao<User>, CUDBatchDao<User> {

    /**
     * loginId和password匹配则返回User, 不匹配则返回null, 用于用户登录
     * 
     * @param loginId 用户名或手机号或email
     * @param password 密码
     * @return 
     */
    User getByLoginIdAndPassword(@Param("loginId") String loginId, @Param("password") String password);

    /**
     * 根据用户类型进行查询
     * 
     * @param userType
     * @param page
     * @return
     */
    List<User> listByType(@Param("userType") UserTypeEnum userType, @Param("page") Page page);

    /**
     * 查询对应用户类型的用户数量
     * 
     * @param userType 用户类型
     * @return
     */
    int countUserOfType(@Param("userType") UserTypeEnum userType);

}
