/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.up72.framework.util.ObjectUtils;
import com.up72.framework.util.page.Pagination;
import com.up72.hq.constant.Cnst;
import com.up72.hq.dto.resp.*;
import com.up72.hq.model.*;
import com.up72.hq.dao.*;
import com.up72.hq.service.*;

import com.up72.framework.util.page.PageBounds;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.up72.hq.service.IProdService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 产品DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class ProdServiceImpl implements  IProdService{

	@Autowired
	private ProdMapper prodMapper;
	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private ProdAttrMapper prodAttrMapper;
	@Autowired
	private ProdParamMapper prodParamMapper;
	@Autowired
	private ProdSpecMapper prodSpecMapper;
	@Autowired
	private ProdImgMapper prodImgMapper;
	//	@Autowired
//	private ClctMapper dhsClctMapper;
	@Autowired
	private IOrderGoodsCommentService orderGoodsCommentService;
	@Autowired
	private IProdImgService prodImgService;
	@Autowired
	private ISpecValueService specValueService;
	@Autowired
	private IGoodsService goodsService;
	@Autowired
	private ICatService catService;
	@Autowired
	private IAttrValueService attrValueService;

	@Autowired
	private IKeywordService keywordService;

	public Prod save(Prod model){
		prodMapper.insert(model);
		return model;
	}

	public void update(Prod dhsProd){
		prodMapper.update(dhsProd);
	}

	public void updateIsMarketable(Prod dhsProd){
		Map params = new HashMap();
		params.put("prodId",dhsProd.getId());
		params.put("isMarketable",dhsProd.getIsMarketable());
		params.put("editTime",new Date());
//		goodsMapper.updateIsMarketable(params);
		prodMapper.update(dhsProd);
	}

	public void delete(Long id) {
		// 依次删除产品相关的图片、参数、属性、规格、商品、产品
		prodImgMapper.deleteByProdId(id);
		prodParamMapper.deleteByProdId(id);
		prodAttrMapper.deleteByProdId(id);
		prodSpecMapper.deleteByProdId(id);
		goodsMapper.deleteByProdId(id);
		prodMapper.delete(id);
	}

	@Transactional(readOnly=true)
	public Prod getById(Long id){
		return prodMapper.findById(id);
	}

	@Transactional(readOnly=true)
	public Page<Prod> getPage(Map params, PageBounds rowBounds){
		PageList<Prod> list = prodMapper.findPage(params, rowBounds);
		return new Page<Prod>(list,list.getPagination());
	}
	@Transactional(readOnly=true)
	public Page<ProdResp> getRespPage(Map params, PageBounds rowBounds){
		PageList<ProdResp> list = prodMapper.findRespPage(params, rowBounds);
		return new Page<ProdResp>(list,list.getPagination());
	}

	@Transactional(readOnly=true)
	public Page<Prod2Resp> getResp2Page(Map params, PageBounds rowBounds){
		int pageNumber = rowBounds.getPageNumber();
		int pageSize = rowBounds.getPageSize();
		int totalCount = prodMapper.cntResp2(params);
		params.put("start",(pageNumber - 1) * pageSize);
		params.put("pageSize",pageSize);
		List<Prod2Resp> prod2RespList = prodMapper.findResp2List(params);
		Pagination pagination = new Pagination(rowBounds.getPageNumber(),pageSize,totalCount);
		PageList<Prod2Resp> list = new PageList<Prod2Resp>(prod2RespList,pagination);
		return new Page<Prod2Resp>(list,list.getPagination());
	}

	public void saveRespAttrParamImg(ProdResp prodResp){
		Long prodId=prodResp.getId();
		// 3.
		StringBuffer attrIds = new StringBuffer("");
		StringBuffer attrFull = new StringBuffer("");
		StringBuffer attrNameFull = new StringBuffer("");
		List<ProdAttrResp> prodAttrList = prodResp.getProdAttrList();
		AttrValue dhsAttrValue = null;
		for (ProdAttr prodAttr : prodAttrList) {
			prodAttr.setProdId(prodId);
			prodAttrMapper.insert(prodAttr);
			attrFull.append(" " + prodAttr.getAttrId() + "_" + prodAttr.getAttrValueId());
			attrIds.append(" " + prodAttr.getAttrId());
			dhsAttrValue = attrValueService.getById(prodAttr.getAttrValueId());
			if(dhsAttrValue != null){
				attrNameFull.append(" " + dhsAttrValue.getAttrValue());
			}
		}
		prodResp.setAttrIds(attrIds.toString());
		prodResp.setAttrFull(attrFull.toString());
		prodResp.setAttrNameFull(attrNameFull.toString());

		// 4.
		List<ProdParamResp> prodParamList = prodResp.getProdParamList();
		for (ProdParam prodParam : prodParamList) {
			prodParam.setProdId(prodId);
			prodParamMapper.insert(prodParam);
		}

		// 5.
		List<ProdImg> prodImgList = prodResp.getProdImgList();
		for (ProdImg prodImg : prodImgList) {
			prodImg.setProdId(prodId);
			prodImgMapper.insert(prodImg);
		}
		updateFull(prodResp);
	}

	@Override
	public ProdResp saveResp(ProdResp prodResp) {
		/*
		1.保存产品
		2.保存商品列表，如果开启了规格，同时保存规格列表
		3.保存产品属性列表
		4.保存产品参数列表
		5.保存产品图片列表
		 */
		// 1.
		String timeStr = Cnst.getCurTime().toString();
		String number=timeStr.substring(2, timeStr.length() - 3);
		prodResp.setItemNumber(number);
		prodMapper.insert(prodResp);
		prodResp.setSortId(prodResp.getId());
		prodMapper.update(prodResp);
		Long prodId = prodResp.getId();
		Date editTime = new Date();

		//TODO 新增产品时添加商品
		List<GoodsResp> goodsList = prodResp.getGoodsList();
		for (GoodsResp goods : goodsList) {
			goods.setProdId(prodId);
			goods.setEditTime(editTime.getTime());
			goods.setIsMarketable(0);
			goods.setIsDelete(0);
			goods.setSn(timeStr.substring(2, timeStr.length()));
			goods.setType(Cnst.HqGoods.MS_GOODS);
			goods.setAddTime(Cnst.getCurTime());
			goodsMapper.insert(goods);

			if(StringUtils.isNotEmpty(goods.getFullName())){
				keywordService.addKeyword(goods.getFullName().replaceAll("'",""),Cnst.SolrSearch.SEVEN_MS);
			}
			List<ProdSpec> prodSpecList = goods.getProdSpecList();
			if(prodSpecList == null || prodSpecList.size() == 0) {
				continue;
			}
			for (ProdSpec prodSpec : prodSpecList) {
				prodSpec.setProdId(prodId);
				prodSpec.setGoodsId(goods.getId());
				prodSpecMapper.insert(prodSpec);;
			}

		}
		prodMapper.update(prodResp);
		return prodResp;
	}

	@Transactional(readOnly=true)
	public ProdResp getRespById(Long id) {
		return prodMapper.findRespById(id);
	}

	@Override
	public ProdResp updateResp(ProdResp prodResp) {
		/*
		1.修改产品
		2.如果是单一规格的商品则直接修改商品；如果是多规格的商品，则能根据规格查询到的商品执行修改操作，不能查询到的执行插入操作，多余的执行删除操作
		3.删除原来的产品属性列表，保存新的产品属性列表
		4.删除原来的产品参数列表，保存新的保存产品参数列表
		5.删除原来的产品图片列表，保存新的保存产品图片列表
		 */

		// 1.
		String timeStr = Cnst.getCurTime().toString();
		String number=timeStr.substring(2, timeStr.length() - 3);
		prodMapper.update(prodResp);
		Cat dhsCat = catService.getById(prodResp.getCatId());
		boolean isEnableSpec = prodResp.getIsEnableSpec() == 1;
		Long prodId = prodResp.getId();
		Date editTime = new Date();
		String catIds = goodsService.getCatIdsStr(dhsCat);
		// 2.
		List<GoodsResp> goodsList = prodResp.getGoodsList();
		if(!isEnableSpec) { // 单一规格的商品直接修改
			GoodsResp goods = goodsList.get(0);
			goods.setProdId(prodId);
			goods.setEditTime(editTime.getTime());
			goods.setCollectNum(goodsMapper.findById(goods.getId()).getCollectNum());
			Goods goods1 = goodsMapper.findById(goods.getId());
			goods.setCollectNum(goods1.getCollectNum());
			if(goods1.getIsDelete() != null){
				goods.setIsDelete(goods1.getIsDelete());
			}else{
				goods.setIsDelete(0);
			}
			goods.setSolrName(prodResp.getName() + " " + goods.getFullName() + catIds);
			goods.setSort(goods1.getSort());
			goods.setEditTime(Cnst.getCurTime());
			goods.setAddTime(goods1.getAddTime());
			goods.setType(goods1.getType());
			goods.setIsTop(goods1.getIsTop());
			goods.setIsMarketable(goods1.getIsMarketable());
			goods.setMarkeTime(Cnst.getCurTime());
			goods.setIsDelete(0);
			goods.setSn(goods1.getSn() == null ? number:goods1.getSn());
			goods.setIsGive(goods1.getIsGive());
			goods.setSn(goods1.getSn());
			goods.setType(Cnst.HqGoods.MS_GOODS);
			goodsMapper.update(goods);
			keywordService.addKeyword(goods.getFullName().replaceAll("'",""),Cnst.SolrSearch.SEVEN_MS);
		} else {
			//List<GoodsResp> dbGoodsList = prodMapper.findRespById(prodId).getGoodsList();
			List<Long> existsGoodsIdList = new ArrayList<Long>();
			//删除该产品所有规格值
			prodSpecMapper.deleteByProdId(prodId);
			for (GoodsResp goods : goodsList) {
				goods.setProdId(prodId);
				goods.setEditTime(editTime.getTime());
				Long goodsId = goods.getId();
//				Long goodsId = getGoodsId(dbGoodsList,goods.getProdSpecList());
				if(goodsId == null) { // 执行插入
					goodsMapper.insert(goods);
				} else { // 执行修改
					Goods goods1 = goodsMapper.findById(goodsId);
					existsGoodsIdList.add(goodsId);
					goods.setId(goodsId);
					goods.setCollectNum(goods1.getCollectNum());
					if(goods1.getIsDelete() != null){
						goods.setIsDelete(goods1.getIsDelete());
					}
					goods.setSort(goods1.getSort());
					goods.setEditTime(new Date().getTime());
					goods.setAddTime(goods1.getAddTime());
					goods.setType(Cnst.HqGoods.MS_GOODS);
					goods.setMarkeTime(Cnst.getCurTime());
					goods.setIsTop(goods1.getIsTop());
					goods.setIsMarketable(goods1.getIsMarketable());
					goods.setMonthSales(goods1.getMonthSales());
					goods.setSn(goods1.getSn() == null ? number:goods1.getSn());
					goods.setIsDelete(0);
					goods.setIsGive(goods1.getIsGive());
					goods.setSolrName(goods.getFullName() + " " + goods.getSpecNames() + catIds);
					goodsMapper.update(goods);
					keywordService.addKeyword(goods.getFullName().replaceAll("'",""),Cnst.SolrSearch.SEVEN_MS);
				}
				List<ProdSpec> prodSpecList = goods.getProdSpecList();
				for (ProdSpec prodSpec : prodSpecList) {
					prodSpec.setProdId(prodId);
					prodSpec.setGoodsId(goods.getId());
					prodSpecMapper.insert(prodSpec);;
				}
			}

//			// 删除数据库多余的商品
//			for (GoodsResp dbGoods : dbGoodsList) {
//				if(!existsGoodsIdList.contains(dbGoods.getId())){
//					Long goodsId = dbGoods.getId();
//					prodSpecMapper.deleteByGoodsId(goodsId); // 删除规格
//					goodsMapper.delete(goodsId);
//				}
//			}
		}

		// 3.
		//prodAttrMapper.deleteByProdId(prodId);
		StringBuffer attrIds = new StringBuffer("");
		StringBuffer attrFull = new StringBuffer("");
		StringBuffer attrNameFull = new StringBuffer("");
		List<ProdAttrResp> prodAttrList = prodResp.getProdAttrList();;
		AttrValue dhsAttrValue = null;
		for (ProdAttr prodAttr : prodAttrList) {
			prodAttr.setProdId(prodId);
			prodAttrMapper.insert(prodAttr);
			attrIds.append(" " + prodAttr.getAttrId());
			attrFull.append(" " + prodAttr.getAttrId() + "_" + prodAttr.getAttrValueId());
			dhsAttrValue = attrValueService.getById(prodAttr.getAttrValueId());
			if(dhsAttrValue != null){
				attrNameFull.append(" " + dhsAttrValue.getAttrValue());
			}
		}
		prodResp.setAttrIds(attrIds.toString());
		prodResp.setAttrFull(attrFull.toString());
		prodResp.setAttrNameFull(attrNameFull.toString());
		// 4.
		prodParamMapper.deleteByProdId(prodId);
		List<ProdParamResp> prodParamList = prodResp.getProdParamList();
		for (ProdParam prodParam : prodParamList) {
			prodParam.setProdId(prodId);
			prodParamMapper.insert(prodParam);
		}

		// 5.
		prodImgMapper.deleteByProdId(prodId);
		List<ProdImg> prodImgList = prodResp.getProdImgList();
		for (ProdImg prodImg : prodImgList) {
			prodImg.setProdId(prodId);
			prodImgMapper.insert(prodImg);
		}
		updateFull(prodResp);
		return prodResp;
	}

	/** 获取收藏数量 */
	private Integer getClctNum(Long goodsId, List<GoodsResp> dbGoodsList) {
		for (GoodsResp goods : dbGoodsList) {
			if(goods.getId().longValue() == goodsId.longValue()) {
				return goods.getCollectNum();
			}
		}
		return 0;
	}

	/** 对比规格值，如果数据库中的规格值与当前要添加的规格值完全一致，则认为是修改商品，返回商品ID */
	private Long getGoodsId(List<GoodsResp> dbGoodsList, List<ProdSpec> prodSpecList) {
		for (GoodsResp goods : dbGoodsList) {
			List<ProdSpec> dbProdSpecList = goods.getProdSpecList();
			boolean isAllDbInModel = false;
			for (ProdSpec dbProdSpec : dbProdSpecList) {
				boolean isThisDbInModel = false;
				for (ProdSpec prodSpec : prodSpecList) {
					if(dbProdSpec.getSpecValueId().longValue() == prodSpec.getSpecValueId().longValue()) {
						isThisDbInModel = true;
						break;
					}
				}
				isAllDbInModel = isThisDbInModel;
				if(!isThisDbInModel) {
					break;
				}
			}

			boolean isAllModelInDb = false;
			for(ProdSpec prodSpec : prodSpecList) {
				boolean isThisModelInDb = false;
				for (ProdSpec dbProdSpec : dbProdSpecList) {
					if(prodSpec.getSpecValueId().longValue() == dbProdSpec.getSpecValueId().longValue()) {
						isThisModelInDb = true;
						break;
					}
				}
				isAllModelInDb = isThisModelInDb;
				if(!isThisModelInDb) {
					break;
				}
			}

			if(isAllDbInModel && isAllModelInDb) {
				return goods.getId();
			}
		}
		return null;
	}

	@Override
	public Double getMinPriceById(Long id) {
		Double d = prodMapper.findMinPriceById(id);
		if( d == null){
			d = 0.0;
		}
		return d;
	}

	@Override
	public Double getMaxPriceById(Long id) {
		Double d = prodMapper.findMaxPriceById(id);
		if( d == null){
			d = 0.0;
		}
		return d;
	}

	@Override
	public Page<ProdResp> getListByCatIdPath(Map params, PageBounds rowBounds) {
		PageList<ProdResp> list = prodMapper.findListByCatIdPath(params, rowBounds);
		return new Page<ProdResp>(list,list.getPagination());
	}

	@Override
	public String isUseMarke(Long prodId) {
		String result = "";
		try {
			Prod prod = prodMapper.findById(prodId);
			Map map = new HashMap();
			map.put("prodId", prodId);
			map.put("isDelete", 0);
			map.put("type", 1);
			PageBounds p = new PageBounds();
			p.setPageSize(Integer.MAX_VALUE);
			p.setPageNumber(1);
			//上架条件
			if (prod.getIsMarketable() == null || prod.getIsMarketable() == 0) {
				map.put("isMarketable", 1);
				PageList<GoodsResp> pageList = goodsMapper.findPage(map, p);
				if (pageList.size() > 0) {
					result = "trueMess";
				} else {
					result = "falseMess";
				}
				//取消上架条件
			} else {
				map.put("isMarketable", 1);
				PageList<GoodsResp> pageList = goodsMapper.findPage(map, p);
				if (pageList.size() > 0) {
					result = "falseSucc";
				} else {
					result = "trueSucc";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
		}
		return result;
	}

	@Override
	public ProdResp getByGoodsId(Long goodsId) {
		return prodMapper.findByGoodsId(goodsId);
	}

	@Override
	public void updateFull(Prod prod) {
		//是否新品，品牌
		Integer compositeScore = 0;
		List<GoodsResp> goodsList = goodsMapper.findListByProdId(prod.getId());

		//1.评论总数 产品全规格评论数总和
		Long commentCount = Long.valueOf(orderGoodsCommentService.getCommentNumber("",prod.getId()));
		prod.setCommentCount(commentCount);//评论总数

		//2.图片集合 全规格列表（颜色规格）
		String imgJson = "";
		List<ProdImgResp> dhsProdImgRespList = new ArrayList<ProdImgResp>();
		if(prod.getIsEnableSpec() == 1){
			List<ProdImg> dhsProdImgList = prodImgService.getListByProdId(prod.getId());
			if(dhsProdImgList != null && dhsProdImgList.size() > 0){
				ProdImgResp dhsProdImgResp = null;
				for (ProdImg dhsProdImg : dhsProdImgList) {
					dhsProdImgResp = ProdImgResp.convert(dhsProdImg);
					//设置规格名
					SpecValue dhsSpecValue = specValueService.getById(dhsProdImgResp.getSpecValueId());
					if (ObjectUtils.isNotEmpty(dhsSpecValue)){
						dhsProdImgResp.setSpecValueName(dhsSpecValue.getSpecValue());
						//设置首张图片，列表使用
						if(StringUtils.isNotBlank(dhsProdImgResp.getImgs()) && dhsProdImgResp.getImgs().indexOf(",") > -1){
							dhsProdImgResp.setFirstImg(dhsProdImgResp.getImgs().split(",")[0]);
						}else{
							dhsProdImgResp.setFirstImg(dhsProdImgResp.getImgs());
						}
						dhsProdImgRespList.add(dhsProdImgResp);
					}
				}

			}
		}else{
			ProdImgResp dhsProdImgResp = new ProdImgResp();
			dhsProdImgResp.setFirstImg(goodsList.get(0).getFirstImg());
			dhsProdImgResp.setSpecValueName(goodsList.get(0).getFullName());
			dhsProdImgResp.setProdId(prod.getId());
			dhsProdImgRespList.add(dhsProdImgResp);
		}
		imgJson = JSONObject.toJSONString(dhsProdImgRespList);
		prod.setSpecColorFull(imgJson);//全规格图片

		//3.产品名称+全规格商品内容 总销量(产品全规格销量总和) 总规格名称、最低价
		Integer sales = 0;
		StringBuffer specNames = new StringBuffer();
		StringBuffer goodsNameFull = new StringBuffer(prod.getName());
		Double minPrice = Double.MAX_VALUE;
		if(goodsList != null && goodsList.size() > 0){
			if(prod.getIsEnableSpec() == 1){
				//产品solrName = 产品名称 + catIds + goods的solrName
				for (GoodsResp goods : goodsList) {
					Double price = goods.getPrice() == null ? 0d : goods.getPrice();
					sales += goods.getSales() == null ? 0 : goods.getSales();//计算总销量
					//找出最低价
					if(price != 0 && goods.getIsMarketable() != 0){
						//如果商品价格为0,或商品未上架，则不将其加入到solrName，且不将其作为最低价格
						//只有商品价格部位0，并且商品已上架，才会将与最低价比较，并且加入solrName
						minPrice = Math.min(minPrice,price);
						//增加规格名
						specNames.append("[" + goods.getSpecNames() + "]");
						goodsNameFull.append("[" + goods.getFullName() + "]");
					}
				}
			}else{
				//单规格的产品，其最低价格为商品价格
				minPrice = goodsList.get(0).getPrice() == null ? 0D : goodsList.get(0).getPrice();
				//单规格的产品，其solrName为商品solrName
				specNames.append(goodsList.get(0).getSpecNames() == null ? "" : goodsList.get(0).getSpecNames());
				goodsNameFull.append(goodsList.get(0).getFullName());
			}
		}
		if(Double.MAX_VALUE == minPrice){
			minPrice = 0D;
		}
		//全商品规格内容
		prod.setSpecFull(specNames.toString());
		//产品名称+全商品名称
		prod.setGoodsNameFull(goodsNameFull.toString());
		Cat dhsCat = catService.getById(prod.getCatId());
		//产品所有属性id

		prod.setCatIdFull(goodsService.getCatIdsStr(dhsCat));
		//产品所有属性名称
		prod.setCatNameFull(goodsService.getCatNamesStr(dhsCat));

		//总销量(产品全规格销量总和)
		prod.setSales(sales);
		//全规格中最低价格
		prod.setMinPrice(minPrice);
		//综合评分
		prod.setCompositeScore(compositeScore);
		//是否新品
		prod.setIsNewProd(0);//暂时都为否
		//产品的更新时间
		prodMapper.update(prod);
		prodMapper.updateStock(prod.getId());
	}

	@Override
	public void updateCommentCount() {
		prodMapper.updateCommentCount();
	}

	/**
	 * 更新产品库存
	 * @param prodId
	 */
	@Override
	public void updateStock(Long prodId){
		prodMapper.updateStock(prodId);
	}
	

}
