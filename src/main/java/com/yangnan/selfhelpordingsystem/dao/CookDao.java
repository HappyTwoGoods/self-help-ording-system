package com.yangnan.selfhelpordingsystem.dao;

import com.yangnan.selfhelpordingsystem.constant.CookStatus;
import com.yangnan.selfhelpordingsystem.entity.CookEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CookDao {

    /**
     * 添加厨师信息
     *
     * @param cookInfo
     * @return
     */
    int insertCooker(@Param("cookInfo") CookEntity cookInfo);

    /**
     * 删除厨师信息
     *
     * @param cookId
     * @return
     */
    int deleteCooker(@Param("cookId") Integer cookId);

    /**
     * 根据cookId动态修改厨师信息
     *
     * @param cookInfo
     * @return
     */
    int updateCookInfo(@Param("cookInfo") CookEntity cookInfo);

    /**
     * 动态查询厨师信息
     *
     * @param cookName
     * @param telephone
     * @param cookStatus
     * @return
     */
    List<CookEntity> queryCookInfo(@Param("cookName") String cookName,
                                   @Param("telephone") String telephone,
                                   @Param("cookStatus") Integer cookStatus);

    /**
     * 根据用户名和密码查厨师
     * @param name
     * @param password
     * @return
     */
    CookEntity selectCook(@Param("username") String name,
                   @Param("password") String password);

    /**
     * 根据厨师id查厨师
     * @param id
     * @return
     */
    CookEntity selectCookById(@Param("id") int id);
    /**
     * 根据厨师id修改厨师的状态
     * @param id
     * @param status
     * @return
     */
    int updateStatusById(@Param("id") int id,
                         @Param("status") int status);
}
