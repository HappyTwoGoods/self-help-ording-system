package com.yangnan.selfhelpordingsystem.service;

import com.yangnan.selfhelpordingsystem.dto.UserAccountDTO;
import org.apache.catalina.User;

import java.math.BigDecimal;

public interface UserAccountService {
    /**
     * 增加余额
     * @param id
     * @param password
     * @param price
     * @return
     */
    int addPrice(int id,String password,BigDecimal price);

    /**
     * 减少余额
     * @param id
     * @param password
     * @param price
     * @return
     */
    int reducePrice(int id,String password,BigDecimal price);

    /**
     * 查看用户
     *
     * @param name
     * @param password
     * @return
     */
    UserAccountDTO selectUserAccount(String name, String password);

    /**
     * 新增用户
     *
     * @param userAccountDTO
     * @return
     */
    int insertUserAccount(UserAccountDTO userAccountDTO);

    /**
     * 更改用户名或者密码
     *
     * @param userAccountDTO
     * @return
     */
    int updateUserNameOrPassword(UserAccountDTO userAccountDTO);

    /**
     * 结账
     *
     * @param price
     * @param userId
     * @param userPassword
     * @return
     */
    int updatePrice(BigDecimal price, int userId, String userPassword);

    /**
     * 根据id查找用户
     *
     * @param userId
     * @return
     */
    UserAccountDTO queryBuId(int userId);
}
