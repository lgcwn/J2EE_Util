/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.model.IssueSet;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IIssueSetService {
	
	void save(IssueSet issueSet);

    void update(IssueSet issueSet);
	
	void delete(java.lang.Long id);
	
    IssueSet getById(java.lang.Long id);

    Page<IssueSet> getPage(Map params, PageBounds rowBounds);
	

}
