package com.yangnan.selfhelpordingsystem.dao;

import com.yangnan.selfhelpordingsystem.entity.DeskEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeskDao {

    /**
     * 添加餐桌信息
     *
     * @param deskInfo
     * @return
     */
    int insertDesk(@Param("desk") DeskEntity deskInfo);

    /**
     * 动态查询餐桌信息
     *
     * @param num
     * @return
     */
    List<DeskEntity> queryDeskInfo(@Param("num") String num,
                                   @Param("id") Integer id);

    /**
     * 动态修改餐桌信息
     *
     * @param num
     * @param deskDescribe
     * @return
     */
    int updateDeskInfo(@Param("num") String num,
                       @Param("deskDescribe") String deskDescribe,
                       @Param("deskId") Integer deskId);

    /**
     * 根据id删除餐桌信息
     *
     * @param deskId
     * @return
     */
    int deleteById(@Param("deskId") Integer deskId);
}
