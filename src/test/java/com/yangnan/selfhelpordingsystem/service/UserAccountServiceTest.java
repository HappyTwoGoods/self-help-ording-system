package com.yangnan.selfhelpordingsystem.service;

import com.yangnan.selfhelpordingsystem.dto.UserAccountDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserAccountServiceTest {
    @Resource
    private UserAccountService userAccountService;

    @Test
    public void insetUserAccountTest() {
        UserAccountDTO userAccountDTO = new UserAccountDTO();
        userAccountDTO.setName("xiaoming");
        userAccountDTO.setPassword("xiaoming");
        int i = userAccountService.insertUserAccount(userAccountDTO);
        Assert.assertEquals(1, i);
    }

    @Test
    public void updateNameOrPassword() {
        UserAccountDTO userAccountDTO = new UserAccountDTO();
        userAccountDTO.setId(1);
//        userAccountDTO.setName("daming");
        userAccountDTO.setPassword("daming");
        int i = userAccountService.updateUserNameOrPassword(userAccountDTO);
        Assert.assertEquals(1, i);
    }

    @Test
    public void addPrice() {
        UserAccountDTO userAccountDTO=new UserAccountDTO();
        int i = userAccountService.addPrice(1,"xiaoming",BigDecimal.valueOf(3.12));
        Assert.assertEquals(1, i);
    }

    @Test
    public void reducePrice() {
        int i = userAccountService.reducePrice(1,"xiaoming",BigDecimal.valueOf(4.12));
        Assert.assertEquals(1, i);
    }

}
