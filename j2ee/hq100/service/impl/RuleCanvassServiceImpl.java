/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.hq.dto.resp.RuleCanvassResp;
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

import com.up72.hq.service.IRuleCanvassService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 角色拉票DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class RuleCanvassServiceImpl implements  IRuleCanvassService{
	
	@Autowired
	private RuleCanvassMapper ruleCanvassMapper;
	
	public void save(RuleCanvass ruleCanvass){
		ruleCanvassMapper.insert(ruleCanvass);
	}

    public void update(RuleCanvass ruleCanvass){
		ruleCanvassMapper.update(ruleCanvass);
	}
	
    public void delete(java.lang.Long id){
		ruleCanvassMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public RuleCanvass getById(java.lang.Long id){
		return ruleCanvassMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<RuleCanvassResp> getPage(Map params, PageBounds rowBounds){
        PageList list = ruleCanvassMapper.findPage(params, rowBounds);
		return new Page<RuleCanvassResp>(list,list.getPagination());
	}

	@Transactional(readOnly=true)
	public Long countNumberByStatus(Integer status,Long roleSelectId) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("status",status);
		params.put("roleSelectId",roleSelectId);
		return ruleCanvassMapper.countNumber(params);
	}

	@Transactional(readOnly=true)
	public List<RuleCanvassResp> orderByCanvassCntList() {
		return ruleCanvassMapper.orderByCanvassCntList();
	}
}
