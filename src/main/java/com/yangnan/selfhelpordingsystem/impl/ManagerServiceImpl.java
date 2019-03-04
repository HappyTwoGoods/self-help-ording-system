package com.yangnan.selfhelpordingsystem.impl;

import com.yangnan.selfhelpordingsystem.dao.ManagerDao;
import com.yangnan.selfhelpordingsystem.service.ManagerService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ManagerServiceImpl implements ManagerService {
    @Resource
    ManagerDao managerDao;

    @Override
    public int selectManager(String name, String password) {
        if(Strings.isEmpty(name)||Strings.isEmpty(password)){
            return 0;
        }
        return managerDao.selectManager(name, password);
    }
}
