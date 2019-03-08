package com.yangnan.selfhelpordingsystem.impl;

import com.yangnan.selfhelpordingsystem.constant.CookStatus;
import com.yangnan.selfhelpordingsystem.dao.CookDao;
import com.yangnan.selfhelpordingsystem.dto.CookDTO;
import com.yangnan.selfhelpordingsystem.entity.CookEntity;
import com.yangnan.selfhelpordingsystem.service.CookService;
import com.yangnan.selfhelpordingsystem.util.BeansListUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Service
public class CookServiceImpl implements CookService {

    @Autowired
    private CookDao cookDao;

    @Override
    public int addCookerInfo(CookDTO cookDTO) {
        if (cookDTO == null || StringUtils.isEmpty(cookDTO.getCookName()) ||
                StringUtils.isEmpty(cookDTO.getTelephone()) ||
                StringUtils.isEmpty(cookDTO.getNickname()) ||
                StringUtils.isEmpty(cookDTO.getCookPassword())) {
            return 0;
        }
        CookEntity cookEntity = new CookEntity();
        BeanUtils.copyProperties(cookDTO, cookEntity);
        cookEntity.setCookStatus(CookStatus.REST);
        return cookDao.insertCooker(cookEntity);
    }

    @Override
    public int deleteCookerInfo(Integer cookId) {
        if (cookId <= 0) {
            return 0;
        }
        return cookDao.deleteCooker(cookId);
    }

    @Override
    public int updateCookInfo(CookDTO cookDTO) {
        if (cookDTO == null) {
            return 0;
        }
        CookEntity cookEntity = new CookEntity();
        BeanUtils.copyProperties(cookDTO, cookEntity);
        return cookDao.updateCookInfo(cookEntity);
    }

    @Override
    public List<CookDTO> queryCookInfo(String cookName, String telephone, Integer cookStatus) {
        List<CookEntity> cookEntityList = cookDao.queryCookInfo(cookName, telephone, cookStatus);
        if (CollectionUtils.isEmpty(cookEntityList)) {
            return Collections.emptyList();
        }
        return BeansListUtils.copyListProperties(cookEntityList, CookDTO.class);
    }

    @Override
    public CookDTO selectCook(String username, String password) {
        if (Strings.isEmpty(username) || Strings.isEmpty(password)) {
            return null;
        }
        CookEntity cookEntity = cookDao.selectCook(username, password);
        if (cookEntity == null) {
            return null;
        }
        CookDTO cookDTO = new CookDTO();
        BeanUtils.copyProperties(cookEntity, cookDTO);
        return cookDTO;
    }

    @Override
    public CookDTO selectCookById(int id) {
        if (id < 1) {
            return null;
        }
        CookEntity cookEntity = cookDao.selectCookById(id);
        if (cookEntity == null) {
            return null;
        }
        CookDTO cookDTO = new CookDTO();
        BeanUtils.copyProperties(cookEntity, cookDTO);
        return cookDTO;
    }

    @Override
    public int updateStatusById(int id, int status) {
        if (id < 1 || status != CookStatus.REST && status != CookStatus.WORK) {
            return 0;
        }
        return cookDao.updateStatusById(id, status);
    }
}
