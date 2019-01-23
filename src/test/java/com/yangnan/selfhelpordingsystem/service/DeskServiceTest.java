package com.yangnan.selfhelpordingsystem.service;

import com.yangnan.selfhelpordingsystem.dto.DeskDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeskServiceTest {

    @Autowired
    private DeskService deskService;

    @Test
    public void selectTest(){
        List<DeskDTO> deskDTOList = deskService.queryDeskInfo(null);
        System.out.println(deskDTOList);
    }

    @Test
    public void insertTest(){
        DeskDTO deskDTO = new DeskDTO();
        deskDTO.setDeskNum("002");
        deskDTO.setDescribe("窗边的风景永远最美");
        int n = deskService.addDeskInfo(deskDTO);
        System.out.println(n);
    }

    @Test
    public void updateTest(){
        int n = deskService.updateDeskInfo("003",null,3);
        System.out.println(n);
    }

    @Test
    public void deleteTest(){
        int n = deskService.deleteById(2);
        System.out.println(n);
    }
}
