package com.yangnan.selfhelpordingsystem.service;

import com.yangnan.selfhelpordingsystem.constant.BillDetailStatus;
import com.yangnan.selfhelpordingsystem.dto.BillDetailDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BillDetailServiceTest {
    @Resource
    BillDetailService billDetailService;

    @Test
    public void addBillDetailServiceTest() {
        BillDetailDTO billDetailDTO = new BillDetailDTO();
        billDetailDTO.setBillId(1);
        billDetailDTO.setGoodsId(1);
        billDetailDTO.setStatus(1);
        billDetailDTO.setPrice(BigDecimal.valueOf(40));
        billDetailDTO.setNum(3);
        int i = billDetailService.addBillDetail(billDetailDTO);
        Assert.assertEquals(1, i);
    }

    @Test
    public void updateStatusServiceTest() {
        int i = billDetailService.updateDetailStatusById(1, BillDetailStatus.PRODUCING);
        Assert.assertEquals(1, i);
    }

    @Test
    public void selectByGoodsIdsTest() {
        ArrayList<Integer> goodsIds = new ArrayList<>();
        goodsIds.add(1);
        goodsIds.add(2);
        List<BillDetailDTO> billDetailDTOS = billDetailService.selectDetailByGoodsIds(goodsIds);
        Assert.assertNotNull(billDetailDTOS);
        Assert.assertEquals(billDetailDTOS.size(), 1);
    }

    @Test
    public void selectByBillId() {
        List<BillDetailDTO> billDetailDTOS = billDetailService.selectDetailByBillId(1);
        Assert.assertNotNull(billDetailDTOS);
    }

    @Test
    public void selectByStatus() {
        List<BillDetailDTO> billDetailDTOS = billDetailService.selectDetailByStatus(2);
        Assert.assertNotNull(billDetailDTOS);
    }
}
