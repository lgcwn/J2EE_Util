/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.model.PayList;

import com.up72.framework.util.page.PageBounds;

import java.util.List;
import java.util.Map;

import com.up72.framework.util.page.Page;


/**
 * 支付清单接口
 *
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IPayListService {

    void save(PayList payList);

    void update(PayList payList);

    void delete(java.lang.Long id);

    PayList getById(java.lang.Long id);

    Page<PayList> getPage(Map params, PageBounds rowBounds);

    PayList findBySnCode(String sn);  //根据单号查询此表

}
