package com.yangnan.selfhelpordingsystem.service;

import com.yangnan.selfhelpordingsystem.dto.DeskDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeskServiceTest {

    @Autowired
    private DeskService deskService;

    @Test
    public void selectTest() {
        List<DeskDTO> deskDTOList = deskService.queryDeskInfo(null, null);
        System.out.println(deskDTOList);
    }

    @Test
    public void insertTest() {
        for (int i = 0; i < 10; i++) {
            Random random = new Random();
            DeskDTO deskDTO = new DeskDTO();
            deskDTO.setDeskNum("002" + (random.nextInt(100) + 1));
            deskDTO.setDescribe("窗边的风景永远最美" + (random.nextInt(10) + 1));
            int n = deskService.addDeskInfo(deskDTO);
        }
    }

    @Test
    public void updateTest() {
        for(int i=0;i<40;i++){
        int n = deskService.updateDeskInfo((i+1)+"", null, i+1);
        }
    }

    @Test
    public void deleteTest() {
        int n = deskService.deleteById(2);
        System.out.println(n);
    }
}
