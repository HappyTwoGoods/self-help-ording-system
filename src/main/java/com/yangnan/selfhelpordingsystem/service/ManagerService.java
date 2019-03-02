package com.yangnan.selfhelpordingsystem.service;

public interface ManagerService {
    /**
     * 查询管理员是否存在
     *
     * @param name
     * @param password
     * @return
     */
    int selectManager(String name, String password);
}
