package com.yangnan.selfhelpordingsystem.dao;

import com.yangnan.selfhelpordingsystem.entity.DeskEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeskDao {

    /**
     * 添加餐桌信息
     *
     * @param deskEntity
     * @return
     */
    int insertDesk(@Param("deskInfo")DeskEntity deskEntity);

    /**
     * 动态查询餐桌信息
     *
     * @param num
     * @return
     */
    List<DeskEntity> queryDeskInfo(@Param("num")Integer num);

    /**
     * 动态修改餐桌信息
     *
     * @param num
     * @param deskDescribe
     * @return
     */
    int updateDeskInfo(@Param("num")Integer num,
                       @Param("deskDescribe")String deskDescribe);

    /**
     * 根据id删除餐桌信息
     *
     * @param deskId
     * @return
     */
    int deleteById(@Param("deskId")Integer deskId);
}
