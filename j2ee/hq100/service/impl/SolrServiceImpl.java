package com.up72.hq.service.impl;
import com.up72.framework.util.page.Order;
import com.up72.framework.util.page.Page;
import com.up72.framework.util.page.PageBounds;
import com.up72.framework.util.page.Pagination;
import com.up72.hq.conf.SystemConfig;
import com.up72.hq.service.*;
import com.up72.search.SolrSearch;
import com.up72.solr.SearchClient;
import com.up72.solr.SolrHelper;
import com.up72.solr.SolrObject;
import com.up72.solr.dto.resp.SolrPageResp;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by admin on 16-5-21.
 */
@Service
public class SolrServiceImpl implements ISolrService {
    // solr核心地址
    private String coreUrl = SystemConfig.instants().getValue("solr") + "/hq100-core";

    @Override
    public SolrSearch save(SolrSearch search) {
        SearchClient.add(SolrObject.getSolrDoc(search),coreUrl);
        return search;
    }

    @Override
    public void update(SolrSearch search) {
        SearchClient.add(SolrObject.getSolrDoc(search), coreUrl);
    }

    @Override
    public void delete(String id) {
        SearchClient.delById(id + "", coreUrl);
    }

    @Override
    public SolrSearch getById(String id) {
        return (SolrSearch)SolrObject.toBean(SearchClient.getById(id + "", coreUrl),SolrSearch.class);
    }
    // 更新增量索引
    @Override
    public void updateDeltaIndex(){
        new Thread(new Runnable() {
            public void run() {
                SearchClient.updateDeltaIndex(coreUrl);
            }
        }).start();
    }

    // 删除solr索引
    @Override
    public void delSolrIndex(final List<String> idList){
        new Thread(new Runnable() {
            public void run() {
                SearchClient.deleteByIds(idList, coreUrl);
            }
        }).start();
    }

    @Override
    public List<SolrSearch> getList(Map params) {
        return null;
    }

    @Override
    public Page<SolrSearch> getPage(Map params, PageBounds rowBounds) {
        //查询数据
        SolrHelper solrHelper = new SolrHelper<SolrSearch>(SearchClient.getClient(coreUrl));
        Set<String> set = params.keySet();
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()){
            String name = iterator.next();
            Object val = params.get(name);
            if(val instanceof String){
                solrHelper.andEquals(name,SolrObject.escapeQueryChars((String)val));
            }else{
                solrHelper.andEquals(name,String.valueOf(val));
            }
        }

        List<Order> orders = rowBounds.getOrders();
        StringBuilder sb = new StringBuilder();
        if(null != orders){
            for (Order order:orders){
                if(SolrQuery.ORDER.desc.toString().equals(order.getDirection().name().toLowerCase())){
                    sb.append(order.getProperty()).append(" desc");
                }else{
                    sb.append(order.getProperty()).append(" asc");
                }
                sb.append(",");
            }
        }
        SolrPageResp<SolrSearch> page = solrHelper.getPage(rowBounds.getPageNumber(), rowBounds.getPageSize(), sb.length() > 0 ? sb.substring(0, sb.length() - 1) : coreUrl, SolrSearch.class);
        return new Page(page.getList(), new Pagination(rowBounds.getPageNumber(),rowBounds.getPageSize(),((Long)page.getTotalCount()).intValue()));
    }

}
