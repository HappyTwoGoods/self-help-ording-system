package com.yangnan.selfhelpordingsystem.service;

import com.yangnan.selfhelpordingsystem.dto.DeskDTO;

import java.util.List;

public interface DeskService {

    /**
     * 添加餐桌信息
     *
     * @param deskDTO
     * @return
     */
    int addDeskInfo(DeskDTO deskDTO);

    /**
     * 动态查询餐桌信息
     *
     * @param num
     * @return
     */
    List<DeskDTO> queryDeskInfo(String num,Integer id);

    /**
     * 动态修改餐桌信息
     *
     * @param num
     * @param deskDescribe
     * @return
     */
    int updateDeskInfo(String num, String deskDescribe,Integer deskId);

    /**
     * 根据id删除餐桌信息
     *
     * @param deskId
     * @return
     */
    int deleteById(Integer deskId);

    /**
     * 查看所有餐桌信息
     *
     * @return
     */
    List<DeskDTO> selectAll();

}
