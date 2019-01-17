package com.yangnan.selfhelpordingsystem.dao;

import com.yangnan.selfhelpordingsystem.entity.ManagerEntity;
import org.apache.ibatis.annotations.Param;

public interface ManagerDao {
    int insertManager(@Param("manager") ManagerEntity managerEntity);
}
