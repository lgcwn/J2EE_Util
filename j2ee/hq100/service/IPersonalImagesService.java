package com.up72.hq.service;

import com.up72.framework.util.page.Page;
import com.up72.framework.util.page.PageBounds;
import com.up72.hq.model.PersonalImages;

import java.util.Map;

/**
 * Created by lt on 2016/7/7.
 */
public interface IPersonalImagesService {

    void save(PersonalImages personalImages);

    void update(PersonalImages personalImages);

    void delete(Long id);

    PersonalImages getById(Long id);

    Page<PersonalImages> getPage(Map params, PageBounds rowBounds);

    PersonalImages findLastInfo(Long memberId);

    PersonalImages findInfoByOrder(Map params);

    /**
     * 根据用户id和图片排序查询上一条数据  （如果本条数据order为2 那么查询出来的为2+）
     *
     * @param params
     * @return
     */
    PersonalImages findBeforeInfoByOrderAndId(Map params);

    /**
     * 根据用户id和图片排序查询下一条数据  （如果本条数据order为2 那么查询出来的为2-）
     *
     * @param params
     * @return
     */
    PersonalImages findAfterInfoByOrderAndId(Map params);

}
