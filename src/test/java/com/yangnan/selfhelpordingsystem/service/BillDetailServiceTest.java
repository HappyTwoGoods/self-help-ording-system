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
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BillDetailServiceTest {
    @Resource
    BillDetailService billDetailService;

    @Test
    public void addBillDetailServiceTest() {
        for(int i=0;i<10;i++) {
            BillDetailDTO billDetailDTO = new BillDetailDTO();
            Random random = new Random();
            billDetailDTO.setBillId(random.nextInt(3)+1);
            billDetailDTO.setGoodsId(random.nextInt(5)+1);
            billDetailDTO.setStatus(1);
            billDetailDTO.setPrice(BigDecimal.valueOf(40));
            billDetailDTO.setNum(random.nextInt(3)+1);
            billDetailService.addBillDetail(billDetailDTO);
        }
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
        goodsIds.add(4);
        List<BillDetailDTO> billDetailDTOS = billDetailService.selectOrderByGoodsIds(goodsIds,null);
        System.out.println(billDetailDTOS);
        Assert.assertNotNull(billDetailDTOS);
        Assert.assertEquals(billDetailDTOS.size(), 2);
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
