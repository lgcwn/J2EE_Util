package com.up72.hq.service.impl;

import com.up72.framework.util.page.Page;
import com.up72.framework.util.page.PageBounds;
import com.up72.framework.util.page.PageList;
import com.up72.hq.dao.PersonalImagesMapper;
import com.up72.hq.model.ParamValue;
import com.up72.hq.model.PersonalImages;
import com.up72.hq.service.IPersonalImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by lt on 2016/7/7.
 * zhenglin
 */
@Service
@Transactional
public class PersonalImagesServiceImpl implements IPersonalImagesService {

    @Autowired
    private PersonalImagesMapper personalImagesMapper;


    /**
     * 保存个人图片信息
     *
     * @param personalImages
     */
    public void save(PersonalImages personalImages) {
        personalImagesMapper.insert(personalImages);
    }

    /**
     * 修改个人图片信息
     *
     * @param personalImages
     */
    public void update(PersonalImages personalImages) {
        personalImagesMapper.update(personalImages);
    }

    /**
     * 根据id删除个人图片信息
     *
     * @param id
     */
    public void delete(Long id) {
        personalImagesMapper.delete(id);
    }

    /**
     * 根据id查询个人图片信息
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public PersonalImages getById(Long id) {
        return personalImagesMapper.findById(id);
    }

    /**
     * 带条件分页查询
     *
     * @param params
     * @param rowBounds
     * @return
     */
    @Transactional(readOnly = true)
    public Page<PersonalImages> getPage(Map params, PageBounds rowBounds) {
        PageList<PersonalImages> list = personalImagesMapper.findPage(params, rowBounds);
        return new Page<PersonalImages>(list, list.getPagination());

    }

    @Transactional(readOnly = true)
    public PersonalImages findLastInfo(Long memberId) {
        return personalImagesMapper.findLastInfo(memberId);
    }

    @Transactional(readOnly = true)
    public PersonalImages findInfoByOrder(Map params) {
        return personalImagesMapper.findInfoByOrder(params);
    }

    @Transactional(readOnly = true)
    public PersonalImages findBeforeInfoByOrderAndId(Map params) {
        return personalImagesMapper.findBeforeInfoByOrderAndId(params);
    }

    @Transactional(readOnly = true)
    public PersonalImages findAfterInfoByOrderAndId(Map params) {
        return personalImagesMapper.findAfterInfoByOrderAndId(params);
    }
}
