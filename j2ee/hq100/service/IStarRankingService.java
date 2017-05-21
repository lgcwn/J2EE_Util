/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.resp.StarRankingResp;
import com.up72.hq.model.StarRanking;

import com.up72.framework.util.page.PageBounds;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * (明星排行)接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IStarRankingService {
	
	void save(StarRanking starRanking);

    void update(StarRanking starRanking);
	
	void delete(java.lang.Long id);

    StarRankingResp getById(java.lang.Long id);

    Page<StarRankingResp> getPage(Map params, PageBounds rowBounds);
    Page<StarRankingResp> getPageHt(Map params, PageBounds rowBounds);

    Long countNumberByStatus(Integer status);

    Page<StarRankingResp> findStarRankPage(Map params, PageBounds rowBounds);

    Page<StarRankingResp> getStarPageByPopularity(Map params, PageBounds rowBounds);


}
