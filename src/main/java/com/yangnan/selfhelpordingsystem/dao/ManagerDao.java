package com.yangnan.selfhelpordingsystem.dao;

import com.yangnan.selfhelpordingsystem.entity.ManagerEntity;
import org.apache.ibatis.annotations.Param;

public interface ManagerDao {
    /**
     * 新增管理员
     * @param managerEntity
     * @return
     */
    int insertManager(@Param("manager") ManagerEntity managerEntity);

    /**
     * 查询管理员是否存在
     * @param name
     * @param password
     * @return
     */
    int selectManager(@Param("name") String name,
                      @Param("password") String password);
}
