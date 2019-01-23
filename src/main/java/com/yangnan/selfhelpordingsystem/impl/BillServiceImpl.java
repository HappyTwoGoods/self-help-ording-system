package com.yangnan.selfhelpordingsystem.impl;

import com.yangnan.selfhelpordingsystem.constant.BillPayType;
import com.yangnan.selfhelpordingsystem.constant.BillStatus;
import com.yangnan.selfhelpordingsystem.dao.BillDao;
import com.yangnan.selfhelpordingsystem.dto.BillDTO;
import com.yangnan.selfhelpordingsystem.entity.BillEntity;
import com.yangnan.selfhelpordingsystem.service.Billservice;
import com.yangnan.selfhelpordingsystem.util.BeansListUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Service
public class BillServiceImpl implements Billservice {
    @Resource
    BillDao billDao;

    @Override
    public int insertBill(BillDTO billDTO) {
        if (billDTO == null ||
                billDTO.getPayType() == null || billDTO.getPayType() != BillPayType.UNDER_LINE && billDTO.getPayType() != BillPayType.ON_LINE ||
                billDTO.getPrice() == null || billDTO.getPrice().compareTo(BigDecimal.ZERO) < 0 ||
                billDTO.getUserId() == null || billDTO.getUserId() < 1 ||
                Strings.isEmpty(billDTO.getGoodsInfo())) {
            return 0;
        }
        BillEntity billEntity = new BillEntity();
        BeanUtils.copyProperties(billDTO, billEntity);
        return billDao.insertBill(billEntity);
    }

    @Override
    public int updateBillStatus(int id, int status) {
        if (id < 1 || status < BillStatus.CANCEL || status < BillStatus.USED) {
            return 0;
        }
        return billDao.updateBillStatus(id, status);
    }

    @Override
    public int updateGoodsInfoAndPrice(int id, String goodsInfo,BigDecimal price) {
        if(id<1|| Strings.isEmpty(goodsInfo)){
            return 0;
        }
        return billDao.updateGoodsInfoAndPrice(id,goodsInfo,price);
    }

    @Override
    public BillDTO selectBillById(int id) {
        if (id < 0) {
            return new BillDTO();
        }
        BillEntity billEntity = billDao.selectBillById(id);
        BillDTO billDTO = new BillDTO();
        BeanUtils.copyProperties(billEntity, billDTO);
        return billDTO;
    }

    @Override
    public List<BillDTO> selectBillByUserId(int userId) {
        if (userId < 0) {
            return new ArrayList<>();
        }
        List<BillEntity> billEntities = billDao.selectBillByUserId(userId);
        return BeansListUtils.copyListProperties(billEntities, BillDTO.class);
    }
}
