package com.yangnan.selfhelpordingsystem.service;

import com.yangnan.selfhelpordingsystem.constant.BillStatus;
import com.yangnan.selfhelpordingsystem.dto.BillDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BillServiceTest {
    @Resource
    private Billservice billservice;

    @Test
    public void insertBillTest() {
        BillDTO billDTO = new BillDTO();
        billDTO.setPayType(0);
        billDTO.setPrice(BigDecimal.valueOf(15));
        billDTO.setUserId(2);
        int i = billservice.insertBill(billDTO);
        Assert.assertEquals(1, i);
    }

    @Test
    public void updateBillStatusTest() {
        int i = billservice.updateBillStatus(1, BillStatus.PAYED);
        Assert.assertEquals(1, i);
    }

    @Test
    public void selectBillByIdTest() {
        BillDTO billDTO = billservice.selectBillById(1);
        Assert.assertNotNull(billDTO);
    }

    @Test
    public void selectBillByUserIdTest() {
        List<BillDTO> billDTOS = billservice.selectBillByUserId(1);
        Assert.assertNotNull(billDTOS);
    }

    @Test
    public void selectBillByStatusTest() {
        List<BillDTO> billDTOS = billservice.selectBillByStatus(BillStatus.PAYED);
        Assert.assertNotNull(billDTOS);
    }
}
