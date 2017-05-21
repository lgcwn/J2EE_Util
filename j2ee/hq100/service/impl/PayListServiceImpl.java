/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.hq.model.*;
import com.up72.hq.dao.*;
import com.up72.hq.service.*;

import com.up72.framework.util.page.PageBounds;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.up72.hq.service.IPayListService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 支付清单DAO实现
 *
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class PayListServiceImpl implements IPayListService {

    @Autowired
    private PayListMapper payListMapper;

    public void save(PayList payList) {
        payListMapper.insert(payList);
    }

    public void update(PayList payList) {
        payListMapper.update(payList);
    }

    public void delete(java.lang.Long id) {
        payListMapper.delete(id);
    }

    @Transactional(readOnly = true)
    public PayList getById(java.lang.Long id) {
        return payListMapper.findById(id);
    }

    @Transactional(readOnly = true)
    public Page<PayList> getPage(Map params, PageBounds rowBounds) {
        PageList list = payListMapper.findPage(params, rowBounds);
        return new Page<PayList>(list, list.getPagination());
    }

    public PayList findBySnCode(String sn) {
        return payListMapper.findBySnCode(sn);
    }


}
