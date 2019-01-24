package com.yangnan.selfhelpordingsystem.controller;

import com.yangnan.selfhelpordingsystem.service.Billservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillInfoController {

    @Autowired
    private Billservice billservice;


}
