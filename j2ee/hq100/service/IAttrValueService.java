/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.model.AttrValue;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 属性值接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IAttrValueService {

    AttrValue save(AttrValue AttrValue);

    void update(AttrValue AttrValue);

    void delete(Long id);

    AttrValue getById(Long id);

    Page<AttrValue> getPage(Map params, PageBounds rowBounds);

    /** 计算正在被使用的产品个数 */
    int cntUsing(Long attrValueId);

    /**
     * 根据ids查找并排序
     * @param ids
     * @return
     */
    public List<AttrValue> getAndOrderByIds(String ids);
	

}
