package com.yangnan.selfhelpordingsystem.impl;

import com.yangnan.selfhelpordingsystem.constant.BillDetailStatus;
import com.yangnan.selfhelpordingsystem.dao.BillDetailDao;
import com.yangnan.selfhelpordingsystem.dto.BillDetailDTO;
import com.yangnan.selfhelpordingsystem.entity.BillDetailEntity;
import com.yangnan.selfhelpordingsystem.service.BillDetailService;
import com.yangnan.selfhelpordingsystem.util.BeansListUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.smartcardio.CommandAPDU;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BillDetailServiceImpl implements BillDetailService {
    @Resource
    BillDetailDao billDetailDao;

    @Override
    public int addBillDetail(BillDetailDTO detail) {
        if (detail == null ||
                detail.getBillId() == null || detail.getBillId() < 1 ||
                detail.getGoodsId() == null || detail.getGoodsId() < 1 ||
                detail.getNum() == null || detail.getNum() < 1 ||
                detail.getPrice() == null || detail.getPrice().compareTo(BigDecimal.ZERO) < 0 ||
                detail.getStatus() == null || !detail.getStatus().equals(BillDetailStatus.CONCONFIRM)) {
            return 0;
        }
        BillDetailEntity billDetailEntity = new BillDetailEntity();
        BeanUtils.copyProperties(detail, billDetailEntity);
        return billDetailDao.addBillDetail(billDetailEntity);
    }

    @Override
    public int updateDetailStatusById(int id, int status) {
        if (id < 1 || status < BillDetailStatus.CANCEL || status > BillDetailStatus.PRODUCED) {
            return 0;
        }
        return billDetailDao.updateDetailStatusById(id, status);
    }


    @Override
    public List<BillDetailDTO> selectOrderByGoodsIds(List<Integer> goodsIds,Integer status) {
        if (CollectionUtils.isEmpty(goodsIds)) {
            return new ArrayList<>();
        }
        List<BillDetailEntity> billDetailEntities = billDetailDao.selectOrderByGoodsIds(goodsIds,status);
        if(CollectionUtils.isEmpty(billDetailEntities)){
            return new ArrayList<>();
        }
        return BeansListUtils.copyListProperties(billDetailEntities, BillDetailDTO.class);
    }

    @Override
    public List<BillDetailDTO> selectDetailByBillId(int billId) {
        if (billId < 1) {
            return new ArrayList<>();
        }
        List<BillDetailEntity> billDetailEntities = billDetailDao.selectDetailByBillId(billId);
        if(CollectionUtils.isEmpty(billDetailEntities)){
            return new ArrayList<>();
        }
        return BeansListUtils.copyListProperties(billDetailEntities, BillDetailDTO.class);
    }

    @Override
    public List<BillDetailDTO> selectDetailByStatus(int status,int billId) {
        if (status < BillDetailStatus.CANCEL || status > BillDetailStatus.PRODUCED) {
            return new ArrayList<>();
        }
        List<BillDetailEntity> billDetailEntities = billDetailDao.selectDetailByStatus(status,billId);
        if(CollectionUtils.isEmpty(billDetailEntities)){
            return new ArrayList<>();
        }
        return BeansListUtils.copyListProperties(billDetailEntities, BillDetailDTO.class);
    }

    @Override
    public BillDetailDTO selectDetailById(int id) {
        if(id<1){
            return null;
        }
        BillDetailEntity billDetailEntity = billDetailDao.selectDetailById(id);
        if(billDetailEntity==null){
            return null;
        }
        BillDetailDTO billDetailDTO=new BillDetailDTO();
        BeanUtils.copyProperties(billDetailEntity,billDetailDTO);
        return billDetailDTO;
    }

    @Override
    public List<BillDetailDTO> queryByBillId(int billId) {
        if (billId <= 0){
            return null;
        }
        List<BillDetailEntity> billDetailEntityList = billDetailDao.queryByBillId(billId);
        if(CollectionUtils.isEmpty(billDetailEntityList)){
            return new ArrayList<>();
        }
        return BeansListUtils.copyListProperties(billDetailEntityList, BillDetailDTO.class);
    }
}
