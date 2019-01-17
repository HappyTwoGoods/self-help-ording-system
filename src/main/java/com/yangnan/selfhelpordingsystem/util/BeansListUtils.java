package com.yangnan.selfhelpordingsystem.util;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BeansListUtils {

    /**
     * 将sourceList 拷贝为T类型的List
     *
     * @param sourceList
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> List<T> copyListProperties(List<?> sourceList, Class<T> tClass) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.emptyList();
        }
        List<T> targets = new ArrayList<>();
        sourceList.forEach(source -> {
            try {
                T target = tClass.newInstance();
                BeanUtils.copyProperties(source, target);
                targets.add(target);
            } catch (Exception e) {
                throw new FatalBeanException("Copy bean [" + source.toString() + "] failed.", e);
            }
        });
        return targets;
    }

    /**
     * 拷贝分页查询结果
     *
     * @param sourceList
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> PageInfo<T> copyListPageInfo(List<?> sourceList, Class<T> tClass) {
        if (CollectionUtils.isEmpty(sourceList) || !(sourceList instanceof Page)) {
            return null;
        }
        PageInfo<T> pageInfo = new PageInfo<>();
        BeanUtils.copyProperties(new PageInfo<>(sourceList), pageInfo);
        pageInfo.setList(copyListProperties(sourceList, tClass));
        return pageInfo;
    }
}
