package com.yangnan.selfhelpordingsystem.dao;

import com.yangnan.selfhelpordingsystem.entity.UserAccountEntity;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface UserAccountDao {
    /**
     * 增加余额
     *
     * @param price
     * @param id
     * @return
     */
    int addPrice(@Param("id") int id,
                 @Param("password") String password,
                 @Param("price") BigDecimal price);

    /**
     * 减少余额
     *
     * @param price
     * @param id
     * @return
     */
    int reducePrice(@Param("id") int id,
                    @Param("password") String password,
                    @Param("price") BigDecimal price);

    /**
     * 查看用户
     *
     * @param name
     * @param password
     * @return
     */
    UserAccountEntity selectUserAccount(@Param("name") String name,
                                        @Param("password") String password);

    /**
     * 新增用户
     *
     * @param userAccountEntity
     * @return
     */
    int insertUserAccount(@Param("user") UserAccountEntity userAccountEntity);

    /**
     * 更改用户名或者密码
     *
     * @param userAccountEntity
     * @return
     */
    int updateUserNameOrPassword(@Param("user") UserAccountEntity userAccountEntity);

    /**
     * 查看余额
     *
     * @param id
     * @param password
     * @return
     */
    BigDecimal selectPrice(@Param("id") int id,
                           @Param("password") String password);
}
