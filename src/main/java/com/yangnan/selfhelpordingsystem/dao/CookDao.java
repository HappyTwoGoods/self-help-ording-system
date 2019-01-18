package com.yangnan.selfhelpordingsystem.dao;

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
}
