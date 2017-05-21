/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.model.VoteProps;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 投票道具接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IVotePropsService {
	
	void save(VoteProps voteProps);

    void update(VoteProps voteProps);
	
	void delete(java.lang.Long id);
	
    VoteProps getById(java.lang.Long id);

    Page<VoteProps> getPage(Map params, PageBounds rowBounds);

    List<VoteProps> getListByType(Integer type);
	

}
