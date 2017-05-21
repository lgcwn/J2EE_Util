/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.framework.util.page.Page;
import com.up72.framework.util.page.PageBounds;
import com.up72.hq.dto.resp.ScoreRespListResp;
import com.up72.hq.model.ScoreList;

import java.util.Map;


/**
 * 积分明细接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IScoreListService {
	
	void save(ScoreList scoreList);

    void update(ScoreList scoreList);
	
	void delete(java.lang.Long id);

	void delete(long[] ids);

    ScoreList getById(java.lang.Long id);

    Page<ScoreList> getPage(Map params, PageBounds rowBounds);

    Page<ScoreRespListResp> getRespPage(Map params, PageBounds rowBounds);
	

}
