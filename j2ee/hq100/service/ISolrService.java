package com.up72.hq.service;

import com.up72.framework.util.page.Page;
import com.up72.framework.util.page.PageBounds;
import com.up72.search.SolrSearch;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-05-23.
 */
public interface ISolrService {

    SolrSearch save(SolrSearch solrSearch);

    // 删除solr索引
    void delSolrIndex(List<String> idList);

    // 更新增量索引
    void updateDeltaIndex();

    void update(SolrSearch solrSearch);

    void delete(String id);

    SolrSearch getById(String id);

    List<SolrSearch> getList(Map params);

    Page<SolrSearch> getPage(Map params, PageBounds rowBounds);
}
