/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2015
 */

package com.up72.hq.service;

import com.up72.framework.util.page.Page;
import com.up72.framework.util.page.PageBounds;
import com.up72.hq.model.Information;

import java.util.Map;


/**
 * 信息表接口
 * 
 * @author LIUGUICHENG
 * @version 1.0
 * @since 1.0
 */
public interface IInformationService {
	
	void save(Information mnInformation);

    void update(Information mnInformation);
	
	void delete(Long id);
	
    Information getById(Long id);

    Information getdByType(Integer type);

    Page<Information> getPage(Map params, PageBounds rowBounds);

    void batchRealDel(String ids);
	

}
