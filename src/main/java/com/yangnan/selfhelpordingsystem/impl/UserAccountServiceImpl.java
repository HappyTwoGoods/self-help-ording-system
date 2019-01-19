package com.yangnan.selfhelpordingsystem.impl;

import com.google.common.base.Strings;
import com.yangnan.selfhelpordingsystem.dao.UserAccountDao;
import com.yangnan.selfhelpordingsystem.dto.UserAccountDTO;
import com.yangnan.selfhelpordingsystem.entity.UserAccountEntity;
import com.yangnan.selfhelpordingsystem.service.UserAccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class UserAccountServiceImpl implements UserAccountService {
    @Resource
    private UserAccountDao userAccountDao;


    @Override
    public int addPrice(int id, String password, BigDecimal price) {
        if (id < 1 || Strings.isNullOrEmpty(password) || price == null || price.equals(BigDecimal.ZERO)) {
            return 0;
        }
        return userAccountDao.addPrice(id, password, price);
    }

    @Override
    public int reducePrice(int id, String password, BigDecimal price) {
        if (id < 1 || Strings.isNullOrEmpty(password) || price == null || price.equals(BigDecimal.ZERO)) {
            return 0;
        }
        return userAccountDao.reducePrice(id, password, price);
    }

    @Override
    public UserAccountDTO selectUserAccount(String name, String password) {
        if (Strings.isNullOrEmpty(name) || Strings.isNullOrEmpty(password)) {
            return new UserAccountDTO();
        }
        UserAccountEntity userAccountEntity = userAccountDao.selectUserAccount(name, password);
        UserAccountDTO userAccountDTO = new UserAccountDTO();
        BeanUtils.copyProperties(userAccountEntity, userAccountDTO);
        return userAccountDTO;
    }

    @Override
    public int insertUserAccount(UserAccountDTO userAccountDTO) {
        if (userAccountDTO == null || Strings.isNullOrEmpty(userAccountDTO.getName()) || Strings.isNullOrEmpty(userAccountDTO.getPassword())) {
            return 0;
        }
        UserAccountEntity userAccountEntity = new UserAccountEntity();
        BeanUtils.copyProperties(userAccountDTO, userAccountEntity);
        return userAccountDao.insertUserAccount(userAccountEntity);
    }

    @Override
    public int updateUserNameOrPassword(UserAccountDTO userAccountDTO) {
        if (userAccountDTO == null || userAccountDTO.getId() < 1 || (Strings.isNullOrEmpty(userAccountDTO.getName()) && Strings.isNullOrEmpty(userAccountDTO.getPassword()))) {
            return 0;
        }
        UserAccountEntity userAccountEntity = new UserAccountEntity();
        BeanUtils.copyProperties(userAccountDTO, userAccountEntity);
        return userAccountDao.updateUserNameOrPassword(userAccountEntity);
    }
}
