/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.resp.ProjectProgressResp;
import com.up72.hq.model.ProjectProgress;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 众筹项目进展接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IProjectProgressService {
	
	void save(ProjectProgress projectProgress);

    void update(ProjectProgress projectProgress);
	
	void delete(java.lang.Long id);
    void deleteByCrowdId(java.lang.Long id);

    ProjectProgress getId(java.lang.Long id);
    ProjectProgressResp getById(java.lang.Long id);

    List<ProjectProgressResp> getByCrowdId(java.lang.Long crowdId);

    Page<ProjectProgressResp> getPage(Map params, PageBounds rowBounds);
	

}
