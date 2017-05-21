/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.framework.util.ObjectUtils;
import com.up72.hq.dto.resp.GoodsResp;
import com.up72.hq.dto.resp.ProdSpecResp;
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

import com.up72.hq.service.IProdSpecService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

import javax.servlet.http.HttpServletRequest;

/**
 * 产品规格中间表DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class ProdSpecServiceImpl implements  IProdSpecService{

	@Autowired
	private ProdSpecMapper prodSpecMapper;
	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private ProdMapper prodMapper;

	public ProdSpec save(ProdSpec model){
		prodSpecMapper.insert(model);
		return model;
	}

	public void update(ProdSpec dhsProdSpec){
		prodSpecMapper.update(dhsProdSpec);
	}

	public void delete(Long id){
		prodSpecMapper.delete(id);
	}

	@Transactional(readOnly=true)
	public ProdSpec getById(Long id){
		return prodSpecMapper.findById(id);
	}

	@Transactional(readOnly=true)
	public Page<ProdSpec> getPage(Map params, PageBounds rowBounds){
		PageList<ProdSpec> list = prodSpecMapper.findPage(params, rowBounds);
		return new Page<ProdSpec>(list,list.getPagination());
	}

	@Override
	@Transactional(readOnly=true)
	public String getSpecValueIdsByGoodsId(Map params) {
		String specValueIds=prodSpecMapper.findSpecValueIdsByGoodsId(params);
		if(ObjectUtils.isEmpty(specValueIds)){
			return "";
		}
		return  specValueIds;
	}

	@Override
	public List<ProdSpec> getProdSpecByGoodsId(Long goodsId) {
		return prodSpecMapper.findListByGoodsId(goodsId);
	}

	@Override
	public ProdSpecResp getByParams(Map map) {
		return prodSpecMapper.findByParams(map);
	}

	@Override
	public List<ProdSpecResp> getProdSpecByProdId(Long prodId) {
		return prodSpecMapper.findListByProdId(prodId);
	}

	@Override
	public Boolean getIsUseSpec(HttpServletRequest request) {
		Boolean result = false;
		String[] specStrs = request.getParameter("specStr").split(",");
		String[] specValStrs = request.getParameter("specValStr").split(",");
		String prodId = request.getParameter("prodId");
		Prod prod =  prodMapper.findById(Long.parseLong(prodId));
		if(prod.getIsEnableSpec() == 1){
			ProdSpec prodSpecOne = null;
			for (int i = 0; i < specStrs.length; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("specId", Long.parseLong(specStrs[specStrs.length - 1 - i]));
				map.put("specValueId", Long.parseLong(specValStrs[specValStrs.length - 1 - i]));
				map.put("prodId", prodId);
				PageBounds pageBounds = new PageBounds();
				pageBounds.setPageNumber(1);
				pageBounds.setPageSize(Integer.MAX_VALUE);
				PageList<ProdSpec> page = prodSpecMapper.findPage(map, pageBounds);
				for(int a=0;a<page.size();a++){
					prodSpecOne = new ProdSpec();
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("goodsId",page.get(a).getGoodsId());
					PageBounds pagebs = new PageBounds();
					pagebs.setPageNumber(1);
					pagebs.setPageSize(Integer.MAX_VALUE);
					PageList<ProdSpec> specPageList = prodSpecMapper.findPage(params,pagebs);
					for(int z =0;z<specStrs.length;z++){
						if( prodSpecOne != null ){
							if(specPageList.get(z).getSpecId().equals(Long.parseLong(specStrs[z])) && specPageList.get(z).getSpecValueId().equals(Long.parseLong(specValStrs[z]))){
								prodSpecOne = specPageList.get(z);
							}else{
								prodSpecOne = null;
							}
						}
					}
					if(prodSpecOne != null){
						break;
					}
				}
				if(prodSpecOne != null){
					Goods goods = goodsMapper.findById(prodSpecOne.getGoodsId());
					if(goods.getIsDelete() == null || goods.getIsDelete() == 1){
						result = true;
						break;
					}
				}
			}
		}else{
			List<GoodsResp> goodses =  goodsMapper.findListByProdId(prod.getId());
			if(goodses.size()>0){
				GoodsResp resp = goodses.get(0);
				if(resp.getIsDelete() == null || "".equals(resp.getIsDelete())){
					result = true;
				}
			}else{
				result = true;
			}
		}
		return result;
	}

	@Transactional(readOnly=true)
	public List<ProdSpecResp> getColorListByProdId(Map map) {
		return prodSpecMapper.findColorListByProdId(map);
	}

}
