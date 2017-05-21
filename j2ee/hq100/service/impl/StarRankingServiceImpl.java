/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.hq.dto.resp.StarRankingResp;
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

import com.up72.hq.service.IStarRankingService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * (明星排行)DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class StarRankingServiceImpl implements  IStarRankingService{
	
	@Autowired
	private StarRankingMapper starRankingMapper;
	
	public void save(StarRanking starRanking){
		starRankingMapper.insert(starRanking);
	}

    public void update(StarRanking starRanking){
		starRankingMapper.update(starRanking);
	}
	
    public void delete(java.lang.Long id){
		starRankingMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public StarRankingResp getById(java.lang.Long id){
		return starRankingMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<StarRankingResp> getPage(Map params, PageBounds rowBounds){
        PageList list = starRankingMapper.findPage(params, rowBounds);
		return new Page<StarRankingResp>(list,list.getPagination());
	}

	@Override
	public Page<StarRankingResp> getPageHt(Map params, PageBounds rowBounds) {
		PageList list = starRankingMapper.findPageHt(params, rowBounds);
		return new Page<StarRankingResp>(list,list.getPagination());
	}

	@Override
	public Long countNumberByStatus(Integer status) {
		Map<String,Object> params=new HashMap<>();
		params.put("status",status);
		return starRankingMapper.countNumber(params);
	}

	@Override
	public Page<StarRankingResp> findStarRankPage(Map params, PageBounds rowBounds) {
		PageList list = starRankingMapper.findStarRankPage(params, rowBounds);
		return new Page<StarRankingResp>(list,list.getPagination());
	}
	@Transactional(readOnly = true)
	public Page<StarRankingResp> getStarPageByPopularity(Map params, PageBounds rowBounds) {
		PageList list = starRankingMapper.findStarPageByPopularity(params, rowBounds);
		return new Page<StarRankingResp>(list,list.getPagination());
	}


}
