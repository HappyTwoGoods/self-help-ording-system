package com.yangnan.selfhelpordingsystem.impl;

import com.yangnan.selfhelpordingsystem.dao.DeskDao;
import com.yangnan.selfhelpordingsystem.dto.DeskDTO;
import com.yangnan.selfhelpordingsystem.entity.DeskEntity;
import com.yangnan.selfhelpordingsystem.service.DeskService;
import com.yangnan.selfhelpordingsystem.util.BeansListUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
public class DeskServiceImpl implements DeskService {

    @Autowired
    private DeskDao deskDao;

    @Override
    public int addDeskInfo(DeskDTO deskDTO) {
        if (deskDTO == null) {
            return 0;
        }
        DeskEntity deskEntity = new DeskEntity();
        BeanUtils.copyProperties(deskDTO, deskEntity);
        deskDao.insertDesk(deskEntity);
        return deskEntity.getId();
    }

    @Override
    public List<DeskDTO> queryDeskInfo(String num, Integer id) {
        List<DeskEntity> deskEntityList = deskDao.queryDeskInfo(num, id);
        if (CollectionUtils.isEmpty(deskEntityList)) {
            return Collections.emptyList();
        }
        return BeansListUtils.copyListProperties(deskEntityList, DeskDTO.class);
    }

    @Override
    public int updateDeskInfo(String num, String deskDescribe, Integer deskId) {
        if (num == null && deskDescribe == null) {
            return 0;
        }
        return deskDao.updateDeskInfo(num, deskDescribe, deskId);
    }

    @Override
    public int deleteById(Integer deskId) {
        if (deskId == null) {
            return 0;
        }
        return deskDao.deleteById(deskId);
    }

    @Override
    public List<DeskDTO> selectAll() {
        List<DeskEntity> deskEntityList = deskDao.selectAll();
        if (CollectionUtils.isEmpty(deskEntityList)) {
            return null;
        }
        return BeansListUtils.copyListProperties(deskEntityList,DeskDTO.class);
    }
}
