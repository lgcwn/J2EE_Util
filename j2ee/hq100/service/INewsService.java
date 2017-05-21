/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.resp.NewsResp;
import com.up72.hq.model.News;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;

import javax.servlet.http.HttpServletRequest;


/**
 * 资讯接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface INewsService {
	
	void save(News news);

    void update(News news);

    void update(News news ,long[] ids);

	void delete(java.lang.Long id);
	
    News getById(java.lang.Long id);
    NewsResp getByIdResp(java.lang.Long id);

    Page<NewsResp> getPage(Map params, PageBounds rowBounds);
    Page<NewsResp> getPageTour(Map params, PageBounds rowBounds);

    void setNewsLimit4(HttpServletRequest request);

}
