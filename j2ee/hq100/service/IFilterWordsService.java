/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.model.FilterWords;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 过滤词接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IFilterWordsService {
	
	void save(FilterWords filterWords);

    void update(FilterWords filterWords);
	
	void delete(java.lang.Long id);
	
    FilterWords getById(java.lang.Long id);

    FilterWords getByWords(java.lang.String word);

    Page<FilterWords> getPage(Map params, PageBounds rowBounds);
	
    List<FilterWords> getAll();
    String filterWords(String content);
}
