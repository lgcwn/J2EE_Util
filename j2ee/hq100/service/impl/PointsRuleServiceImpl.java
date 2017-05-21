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

import com.up72.hq.service.IPointsRuleService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 积分规则DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class PointsRuleServiceImpl implements  IPointsRuleService{
	
	@Autowired
	private PointsRuleMapper pointsRuleMapper;
	
	public void save(PointsRule pointsRule){
		pointsRuleMapper.insert(pointsRule);
	}

    public void update(PointsRule pointsRule){
		pointsRuleMapper.update(pointsRule);
	}
	
    public void delete(java.lang.Long id){
		pointsRuleMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public PointsRule getById(java.lang.Long id){
		return pointsRuleMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<PointsRule> getPage(Map params, PageBounds rowBounds){
        PageList list = pointsRuleMapper.findPage(params, rowBounds);
		return new Page<PointsRule>(list,list.getPagination());
	}
	

}
