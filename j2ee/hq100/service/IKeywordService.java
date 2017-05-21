/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.framework.util.page.Page;
import com.up72.framework.util.page.PageBounds;
import com.up72.hq.model.Keyword;
import com.up72.hq.model.KeywordSolr;

import java.util.List;
import java.util.Map;


/**
 * 搜索关键字接口
 * 
 * @author up72
 * @version 1.0
 * @since 1.0
 */
public interface IKeywordService {

	// 更新增量索引
	void updateDeltaIndex();

	void addIndex(KeywordSolr paramKeywordSolr);

	// 删除索引
	void delListIndex(List<String> list);

	// 删除索引
	void delIndexByKeyword(String keyword,Integer type);

	List<Keyword> findList(String keyword);

	void save(Keyword Keyword);

    void update(Keyword Keyword);
	
	void delete(Long id);
	
    Keyword getById(Long id);

    Page<Keyword> getPage(Map params, PageBounds rowBounds);
	
	Keyword getByKeyword(String keyword);

	List<String> getByListIds(String keyword,Integer type);

	void addToCache(String keyword);

	void initKeywords();

	void addKeyword(String keywordName,Integer type);
}
