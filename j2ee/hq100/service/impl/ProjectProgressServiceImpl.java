/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.hq.dto.resp.ProjectProgressResp;
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

import com.up72.hq.service.IProjectProgressService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 众筹项目进展DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class ProjectProgressServiceImpl implements  IProjectProgressService{
	
	@Autowired
	private ProjectProgressMapper projectProgressMapper;
	
	public void save(ProjectProgress projectProgress){
		projectProgressMapper.insert(projectProgress);
	}

    public void update(ProjectProgress projectProgress){
		projectProgressMapper.update(projectProgress);
	}
	
    public void delete(java.lang.Long id){
		projectProgressMapper.delete(id);
	}

	@Override
	public void deleteByCrowdId(Long id) {
		projectProgressMapper.deleteByCrowdId(id);
	}

	@Override
	public ProjectProgress getId(Long id) {
		return projectProgressMapper.findId(id);
	}

	@Transactional(readOnly=true)
    public ProjectProgressResp getById(java.lang.Long id){
		return projectProgressMapper.findById(id);
	}

	@Override
	public  List<ProjectProgressResp> getByCrowdId(Long crowdId) {
		return projectProgressMapper.findByCrowdId(crowdId);
	}

	@Transactional(readOnly=true)
    public Page<ProjectProgressResp> getPage(Map params, PageBounds rowBounds){
        PageList list = projectProgressMapper.findPage(params, rowBounds);
		return new Page<ProjectProgressResp>(list,list.getPagination());
	}
	

}
