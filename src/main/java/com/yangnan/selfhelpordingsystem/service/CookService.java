package com.yangnan.selfhelpordingsystem.service;

import com.yangnan.selfhelpordingsystem.dto.CookDTO;

import java.util.List;

public interface CookService {

    /**
     * 添加厨师信息
     *
     * @param cookDTO
     * @return
     */
    int addCookerInfo(CookDTO cookDTO);

    /**
     * 删除id厨师信息
     *
     * @param cookId
     * @return
     */
    int deleteCookerInfo(Integer cookId);

    /**
     * 根据id修改厨师信息
     *
     * @param cookDTO
     * @return
     */
    int updateCookInfo(CookDTO cookDTO);

    /**
     * 动态查询厨师信息
     *
     * @param cookName
     * @param telephone
     * @param cookStatus
     * @return
     */
    List<CookDTO> queryCookInfo(String cookName, String telephone, Integer cookStatus);

    /**
     * 根据用户名和密码查询厨师
     *
     * @param username
     * @param password
     * @return
     */
    CookDTO selectCook(String username, String password);

    /**
     * 根据id查厨师
     * @param id
     * @return
     */
    CookDTO selectCookById(int id);

    /**
     * 根据厨师的id修改
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatusById(int id, int status);

}
