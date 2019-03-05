package com.yangnan.selfhelpordingsystem.service;

import com.yangnan.selfhelpordingsystem.dto.CookDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CookServiceTest {

    @Autowired
    private CookService cookService;

    @Test
    public void addTest() {
        CookDTO cookDTO = new CookDTO();
        cookDTO.setCookName("张三");
        cookDTO.setTelephone("15991183775");
        cookDTO.setCookStatus(0);
        int n = cookService.addCookerInfo(cookDTO);
        System.out.println(n);
    }

    @Test
    public void deleteTest() {
        int n = cookService.deleteCookerInfo(7);
        System.out.println(n);
    }

    @Test
    public void updateTest() {
        CookDTO cookDTO = new CookDTO();
        cookDTO.setCookName("李浩然");
        cookDTO.setId(3);
        cookService.updateCookInfo(cookDTO);
        System.out.println(cookDTO);
    }

    @Test
    public void queryTest() {
        List<CookDTO> cookEntityList = cookService.queryCookInfo("海瑞", null, 0);
        System.out.println(cookEntityList);
    }


}
