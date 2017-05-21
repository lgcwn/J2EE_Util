/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import com.up72.framework.util.ObjectUtils;
import com.up72.framework.util.page.Page;
import com.up72.framework.util.page.PageBounds;
import com.up72.framework.util.page.PageList;
import com.up72.hq.conf.SystemConfig;
import com.up72.hq.constant.Cnst;
import com.up72.hq.dao.*;
import com.up72.hq.dto.resp.*;
import com.up72.hq.model.*;
import com.up72.hq.redis.RedisCacheClient;
import com.up72.hq.service.IAttrValueService;
import com.up72.hq.service.ICatService;
import com.up72.hq.service.IKeywordService;
import com.up72.hq.utils.CastUtil;
import com.up72.hq.utils.Pinyin4jUtil;
import com.up72.solr.SearchClient;
import com.up72.solr.SolrObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 搜索关键字DAO实现
 * 
 * @author up72
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class KeywordServiceImpl implements IKeywordService {
	@Autowired
	private KeywordMapper keywordMapper;
	@Autowired
	private NewsMapper newsMapper;
	@Autowired
	private QuizMapper quizMapper;
	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private ChooseMapper chooseMapper;
	@Autowired
	private VideoMapper videoMapper;
	@Autowired
	private CrowdMapper crowdMapper;
	@Autowired
	private RoleSelectMapper roleSelectMapper;

	// solr核心地址
	private String coreUrl = SystemConfig.instants().getValue("solr") + "/hq100-keyword";

	// 更新增量索引
	@Override
	public void updateDeltaIndex(){
		new Thread(new Runnable() {
			public void run() {
				SearchClient.updateDeltaIndex(coreUrl);
			}
		}).start();
	}


	public void addIndex(final KeywordSolr keywordSolr)
	{
		new Thread(new Runnable() {
			public void run() {
				SearchClient.add(SolrObject.getSolrDoc(keywordSolr), KeywordServiceImpl.this.coreUrl);
			}
		}).start();
	}

	// 删除索引
	@Override
	public void delListIndex(List<String> list){
		new Thread(new Runnable() {
			public void run() {
				SearchClient.deleteByIds(list,coreUrl);
			}
		}).start();
	}

	// 删除索引
	@Override
	public void delIndexByKeyword(String keyword,Integer type){
		List<String> ids=getByListIds(keyword,type);
		if (ObjectUtils.isNotEmpty(ids)){
			new Thread(new Runnable() {
				public void run() {
					SearchClient.deleteByIds(ids, coreUrl);
				}
			}).start();
		}
	}

	@Override
	public List<Keyword> findList(String keyword){
		SolrQuery solrQuery = getQuery(keyword);
		QueryResponse queryResponse = null;
		List<Keyword> keywordList = new ArrayList<Keyword>();
		try {
			queryResponse = SearchClient.getClient(coreUrl).query(solrQuery);
			//获取搜索结果
			SolrDocumentList solrDocumentList = queryResponse.getResults();
			Keyword word = null;
			SolrDocument solrDocument = null;
			Map<String,Object> valueMap = null;
			for (int i = 0; i < solrDocumentList.size(); i++) {
				solrDocument = solrDocumentList.get(i);
				valueMap = solrDocument.getFieldValueMap();
				word = new Keyword();
				word.setAbbre(CastUtil.getString(valueMap,"abbre"));
				word.setKeyword(CastUtil.getString(valueMap,"keyword"));
				word.setPinyin(CastUtil.getString(valueMap,"pinyin"));
				word.setSearchTimes(CastUtil.getLong(valueMap,"searchTimes"));
				word.setType(CastUtil.getInteger(valueMap,"type"));
				keywordList.add(word);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return keywordList;
	}

	private SolrQuery getQuery(String keyword){
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setRequestHandler("/select");
		solrQuery.setQuery("suggest:" + keyword + "* OR suggest:*," + keyword + "*");//suggest:zhongqing* OR suggest:*,zhongqing*
		solrQuery.setStart(0);
		solrQuery.setRows(Integer.MAX_VALUE);
		solrQuery.addSort("searchTimes", SolrQuery.ORDER.desc);
		return solrQuery;
	}

	public void save(Keyword keyword){
		keywordMapper.insert(keyword);
	}

    public void update(Keyword keyword){
		keywordMapper.update(keyword);
	}

    public void delete(Long id){
		keywordMapper.delete(id);
	}

    @Transactional(readOnly=true)
    public Keyword getById(Long id){
		return keywordMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<Keyword> getPage(Map params, PageBounds rowBounds){
        PageList list = keywordMapper.findPage(params, rowBounds);
		return new Page<Keyword>(list,list.getPagination());
	}

    @Transactional(readOnly=true)
    public Keyword getByKeyword(String keyword){
		return keywordMapper.findByKeyword(keyword);
	}
    @Transactional(readOnly=true)
    public List<String> getByListIds(String keyword,Integer type){
    	List<String> idsList=new ArrayList<>();
    	Map<String,Object> params=new HashedMap();
		params.put("keyword",keyword);
		params.put("type",type);
		PageList<Keyword> list = keywordMapper.findPage(params, new PageBounds(1,Integer.MAX_VALUE));
		for (int i = 0; i <list.size() ; i++) {
			Keyword word=list.get(i);
			idsList.add(word.getId().toString());
		}
		return idsList;
	}

	@Override
	public void addToCache(String keyword){
		Long times = Cnst.keywordTimes.get(keyword);
		if(times == null){
			Cnst.keywordTimes.put(keyword,1L);
		}else{
			Cnst.keywordTimes.put(keyword,times + 1);
		}
	}

	@Autowired
	private ICatService catService;

	@Autowired
	private IAttrValueService attrValueService;

	@Override
	public void initKeywords(){
		Integer type=null;
		PageBounds pageBounds = new PageBounds(1,Integer.MAX_VALUE);
		//资讯，旅游
		PageList<NewsResp> newsList = newsMapper.findPage(null,pageBounds);
		for (News news : newsList) {
			if(news.getNewOrTravel()==0){
				type=Cnst.SolrSearch.ONE;
			}else{
				type=Cnst.SolrSearch.SIX;
			}
			initKeyword(news.getTitle().replaceAll("'",""),type);
		}
		//评选(之最和时事)
		PageList<ChooseResp> chooseRespPageList = chooseMapper.findPage(null,pageBounds);
		for (ChooseResp chooseResp : chooseRespPageList) {
			if(chooseResp.getType()==Cnst.Choose.Type.MOST){
				type=Cnst.SolrSearch.TWO_TWO;
			}else{
				type=Cnst.SolrSearch.TWO_THREE;
			}
			initKeyword(chooseResp.getTitle().replaceAll("'",""),type);
		}

		//角色选拔
		PageList<RoleSelectResp> roleSelectRespPageList = roleSelectMapper.findPage(null,pageBounds);
		for (RoleSelectResp roleSelectResp : roleSelectRespPageList) {
			initKeyword(roleSelectResp.getName().replaceAll("'",""),Cnst.SolrSearch.TWO_ONE);
		}

		//视频
		PageList<VideoResp> videoRespPageList = videoMapper.findPage(null,pageBounds);
		for (VideoResp videoResp : videoRespPageList) {
			initKeyword(videoResp.getName().replaceAll("'",""),Cnst.SolrSearch.FOUR);
		}
		//赛场
		PageList<QuizResp> quizRespPageList = quizMapper.findPage(null,pageBounds);
		for (QuizResp quizResp : quizRespPageList) {
			initKeyword(quizResp.getTitle().replaceAll("'",""),Cnst.SolrSearch.THREE);
		}
		//公益
		PageList<CrowdResp> crowdRespPageList = crowdMapper.findPage(null,pageBounds);
		for (CrowdResp crowdResp : crowdRespPageList) {
			initKeyword(crowdResp.getName().replaceAll("'",""),Cnst.SolrSearch.FIVE);
		}

		//商品
		PageList<GoodsResp> goodsRespPageList = goodsMapper.findPage(null,pageBounds);
		for (GoodsResp goodsResp : goodsRespPageList) {
			if(goodsResp.getType()==Cnst.HqGoods.XN_GOODS){
				type=Cnst.SolrSearch.SEVEN_XN;
			}else if(goodsResp.getType()==Cnst.HqGoods.JF_GOODS){
				type=Cnst.SolrSearch.SEVEN_JF;
			}else if(goodsResp.getType()==Cnst.HqGoods.MS_GOODS){
				type=Cnst.SolrSearch.SEVEN_MS;
			}
			if (StringUtils.isNotBlank(goodsResp.getFullName())){
				initKeyword(goodsResp.getFullName().replaceAll("'",""),type);
			}
		}
	}

	@Override
	public void addKeyword(String keywordName,Integer type){
		initKeyword(keywordName.replaceAll("'",""),type);
		//updateDeltaIndex();
	}

	private void initKeyword(String keywordName,Integer type) {
		Keyword keyword =getByKeyword(keywordName);
		String pinyin=Pinyin4jUtil.converterToSpell(keywordName);
		String abbre=Pinyin4jUtil.converterToFirstSpell(keywordName);
		if(keyword == null){
            keyword=new Keyword();
			keyword.setSearchTimes(1L);
            keyword.setPinyin(pinyin.length()>10000?pinyin.substring(0,10000):pinyin);
            keyword.setAbbre(abbre.length()>10000?abbre.substring(0,10000):abbre);
            keyword.setKeyword(keywordName);
			keyword.setType(type);
            save(keyword);
        }

		if (ObjectUtils.isNotEmpty(keyword)) {
			KeywordSolr keywordSolr = new KeywordSolr();
			keywordSolr.setId(keyword.getId());
			keywordSolr.setType(keyword.getType());
			keywordSolr.setPinyin(pinyin.length()>10000?pinyin.substring(0,10000):pinyin);
			keywordSolr.setAbbre(abbre.length()>10000?abbre.substring(0,10000):abbre);
			keywordSolr.setSearchTimes(keyword.getSearchTimes());
			keywordSolr.setKeyword(keyword.getKeyword());
			addIndex(keywordSolr);
		}
	}

}
