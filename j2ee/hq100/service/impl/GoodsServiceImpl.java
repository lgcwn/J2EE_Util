/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.framework.util.ObjectUtils;
import com.up72.framework.util.page.*;
import com.up72.hq.constant.Cnst;
import com.up72.hq.dto.resp.Goods2Resp;
import com.up72.hq.dto.resp.Goods3Resp;
import com.up72.hq.dto.resp.GoodsResp;
import com.up72.hq.dto.resp.ProdResp;
import com.up72.hq.model.*;
import com.up72.hq.dao.*;
import com.up72.hq.model.Order;
import com.up72.hq.service.*;

import com.up72.hq.utils.CastUtil;
import com.up72.hq.utils.HqUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.up72.hq.service.IGoodsService;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * 商品DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class GoodsServiceImpl implements  IGoodsService{

	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private ProdSpecMapper prodSpecMapper;
	@Autowired
	private SpecMapper specMapper;
	@Autowired
	private SpecValueMapper specValueMapper;
	@Autowired
	private ProdImgMapper prodImgMapper;
	@Autowired
	private CatMapper catMapper;
	@Autowired
	private ProdMapper prodMapper;
	@Autowired
	private IProdService prodService;
	@Autowired
	private FavouriteMapper favouriteMapper;

	public Goods save(Goods model){
		goodsMapper.insert(model);
		return model;
	}

	public void update(Goods goods){
		goodsMapper.update(goods);
	}

	public void delete(long id){
		goodsMapper.delete(id);
	}

	@Override
	public void delete(long[] ids) {
		for(long id:ids){
			Goods goods = goodsMapper.findById(id);
			goods.setIsDelete(Cnst.HqGoods.DEL_GOODS);
			update(goods);
		}
	}

	@Transactional(readOnly=true)
	public GoodsResp getById(Long id){
		return goodsMapper.findById(id);
	}

	@Transactional(readOnly=true)
	public Page<GoodsResp> getPage(Map params, PageBounds rowBounds){
		PageList<GoodsResp> list = goodsMapper.findPage(params, rowBounds);
		return new Page<GoodsResp>(list,list.getPagination());
	}

	@Transactional(readOnly=true)
	public List<Goods> getList(Map params){
		return goodsMapper.findList(params);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Goods3Resp> getResp3Page(Map params, PageBounds rowBounds) {
		PageList<Goods3Resp> list = goodsMapper.findResp3Page(params, rowBounds);
		return new Page<Goods3Resp>(list,list.getPagination());
	}

	@Override
	@Transactional(readOnly=true)
	public Goods2Resp getResp2ById(Long id) {
		return goodsMapper.findResp2ById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<GoodsResp> getListByProdId(Long prodId) {
		return goodsMapper.findListByProdId(prodId);
	}

	@Override
	public String getGoodsIdsByProdId(Long prodId) {
		return goodsMapper.findGoodsIdsByProdId(prodId);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Goods> getHomePage(Map params, PageBounds rowBounds) {
		PageList<Goods> list = goodsMapper.findHomePage(params,rowBounds);
		return new Page<Goods>(list,list.getPagination());
	}
	@Override
	@Transactional(readOnly=true)
	public Page<GoodsResp> getRespPage(Map params, PageBounds rowBounds) {
		PageList<GoodsResp> list = goodsMapper.findRespPage(params,rowBounds);
		return new Page<GoodsResp>(list,list.getPagination());
	}

	@Override
	@Transactional(readOnly=true)
	public Page<GoodsResp> getRecommendGoodsPage(Map params, PageBounds rowBounds) {
		PageList<GoodsResp> list = goodsMapper.findRecommendGoodsPage(params,rowBounds);
		return new Page<GoodsResp>(list,list.getPagination());
	}

	@Override
	@Transactional(readOnly=true)
	public Page<GoodsResp> getRecommendGoodsLists(HttpServletRequest request) {
		Map<String,Object> params = new HashMap<String, Object>();
		Member member = HqUtil.getLoginMember(request);
		Favourite dhsFavourite = new Favourite();
		dhsFavourite.setMemberId(member.getId());
		/*List<Favourite> dhsFavouriteList = favouriteMapper.findFavouriteList(dhsFavourite);
		if(dhsFavouriteList.isEmpty()){
			params.put("isDelete",0);
			params.put("isMarketable",1);
			params.put("type",1);
			PageBounds pageBounds = $getPageBounds(request,4);
			pageBounds.setOrders(com.up72.framework.util.page.Order.formString("SALES.DESC"));
			PageList<GoodsResp> goods =  goodsMapper.findRespPage(params,pageBounds);
			return new Page<GoodsResp>(goods,goods.getPagination());
		}else {
			PageBounds pageBounds = $getPageBounds(request,4);
			params.put("memberId",member.getId());
			params.put("isMarketable",1);
			params.put("isDelete",0);
			params.put("type",1);
			pageBounds.setOrders(com.up72.framework.util.page.Order.formString("SALES.DESC"));
			PageList<GoodsResp> goods =  goodsMapper.findRecommendGoodsPage(params,pageBounds);
			return new Page<GoodsResp>(goods,goods.getPagination());
		}*/
		return null;

	}

	@Override
	@Transactional(readOnly=true)
	public Page<GoodsResp> getRecommendGoodsListss(HttpServletRequest request) {
		Map<String,Object> params = new HashMap<String, Object>();
		Member member = HqUtil.getLoginMember(request);
		Favourite dhsFavourite = new Favourite();
		dhsFavourite.setMemberId(member.getId());
		/*List<Favourite> dhsFavouriteList = favouriteMapper.findFavouriteList(dhsFavourite);
		if(dhsFavouriteList.isEmpty()){
			params.put("isDelete",0);
			params.put("isMarketable",1);
			params.put("type",1);
			PageBounds pageBounds = $getPageBounds(request,12);
			pageBounds.setOrders(com.up72.framework.util.page.Order.formString("SALES.DESC"));
			PageList<GoodsResp> goods =  goodsMapper.findRespPage(params,pageBounds);
			return new Page<GoodsResp>(goods,goods.getPagination());
		}else {
			PageBounds pageBounds = $getPageBounds(request,12);
			params.put("memberId",member.getId());
			params.put("isMarketable",1);
			params.put("isDelete",0);
			params.put("type",1);
			pageBounds.setOrders(com.up72.framework.util.page.Order.formString("SALES.DESC"));
			PageList<GoodsResp> goods =  goodsMapper.findRecommendGoodsPage(params,pageBounds);
			return new Page<GoodsResp>(goods,goods.getPagination());
		}*/
		return  null;

	}

	private static PageBounds $getPageBounds(ServletRequest request, int... pageSize) {
		int size = 15;
		if (pageSize.length > 0 && pageSize[0] > 0) {
			size = pageSize[0];
		}
		int pageNumber = $getParam(request, "pageNumber", 1);
		if (pageNumber < 1) pageNumber = 1;
		PageBounds pageBounds = new PageBounds(pageNumber, size);
		return pageBounds;
	}
	private static final int $getParam(ServletRequest request, String name,
									   int defval) {
		String param = request.getParameter(name);
		int value = defval;
		if (param != null) {
			try {
				value = Integer.parseInt(param);
			} catch (NumberFormatException ignore) {
			}
		}
		return value;
	}

	@Override
	public List<Goods> getListByGoodsIds(Map params) {
		String goodsIds = (String)params.get("goodsIds"); // 确保没有注入
		goodsIds = goodsIds.replaceAll("'","'");
		params.put("goodsIds",goodsIds);
		return goodsMapper.getListByGoodsIds(params);
	}

	@Override
	public List<Goods> getTop10OfToday() {
		Map params = new HashMap();
		String date = Cnst.SDF_SHORT.format(new Date());
		params.put("createDateStart",Long.parseLong(date  + "000000000"));
		params.put("createDateEnd",  Long.parseLong(date  + "235959999"));
		return goodsMapper.findTop10OfToday(params);
	}

	@Override
	public Long getMinSort(Long catId) {
		return goodsMapper.findMinSort(catId)-1;
	}

	@Override
	public Goods getMoveSortAction(Long catId,Long goodsId,Integer type) {
		Goods sortGoods = null;
		Goods goods = goodsMapper.findById(goodsId);
		Map<String,Object> map = new HashMap<String,Object>();
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPageSize(Integer.MAX_VALUE);
		pageBounds.setPageNumber(1);
		//上移
		if(type == 1) {
			pageBounds.setOrders(com.up72.framework.util.page.Order.formString("SORT.DESC"));
			map.put("moveStr",goods.getSort());
		}
		//下移
		if(type == 0){
			pageBounds.setOrders(com.up72.framework.util.page.Order.formString("SORT.ASC"));
			map.put("downStr",goods.getSort());
		}
		map.put("catId",catId);
		PageList<GoodsResp> goodses = goodsMapper.findPage(map,pageBounds);
		if(goodses.size()>0){
			sortGoods = goodses.get(0);
		}
		return sortGoods;
	}

	@Override
	public Integer doEditWaitGoods(HttpServletRequest request, Goods model) {
		Integer status = 1; //1失败 0成功
		try{
			//本次编辑的商品，也就是待编辑商品
			Goods db = goodsMapper.findById(model.getId());
			//所属分类
			Cat cat = catMapper.findById(model.getCatId());
			//待选的规格ids 1,2 (颜色，尺码)
			String specStr = request.getParameter("specStr");
			//对应待选的规格，所选择的规格值ids 3,4 (白色，170)
			String specValStr = request.getParameter("specValStr");
			//所属产品
			Prod prod = prodMapper.findById(model.getProdId());
			//编辑商品
			db.setCatId(model.getCatId());
			db.setProdId(prod.getId());
			db.setFullName(model.getFullName());
			db.setType(1);//自营
			db.setEditTime(new Date().getTime());
			db.setMarketPrice(model.getMarketPrice());
			db.setPrice(model.getPrice());
			db.setSort(db.getId());
			db.setIsMarketable(model.getIsMarketable());
			if(model.getIsMarketable() == 1) {
				db.setMarkeTime(Cnst.getCurTime());
			}
			db.setIsDelete(0);
			if (db.getStock() == null){
				db.setStock(0);
			}
			db.setSales(0);
			db.setAddTime(new Date().getTime());
			if(StringUtils.isEmpty(db.getSn())) {
				String timeStr = Cnst.getCurTime().toString();
				db.setSn(timeStr.substring(2,timeStr.length()-3));
			}
			//如果是多规格
			if(prod.getIsEnableSpec() == 1){
				//商品规格处理
				if(StringUtils.isNotEmpty(specStr) && StringUtils.isNotEmpty(specValStr)){
					String []specStrs = specStr.split(",");
					String []specValStrs = specValStr.split(",");
					//修改老数据
					ProdSpec prodSpecOne = null;
					//循环所有使用的规格
					for(int i =0;i<specStrs.length;i++){
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("specId",Long.parseLong(specStrs[specStrs.length-1-i]));
						map.put("specValueId",Long.parseLong(specValStrs[specValStrs.length-1-i]));
						map.put("prodId",model.getProdId());
						PageBounds pageBounds = new PageBounds();
						pageBounds.setPageNumber(1);
						pageBounds.setPageSize(Integer.MAX_VALUE);
						//查找产品某规格某值（颜色-白色）的原有数据
						PageList<ProdSpec> page = prodSpecMapper.findPage(map,pageBounds);
						if(page.size()>1){
							//如果有多条数据（{颜色-白色，尺码-160}{颜色-白色，尺码-165}),颜色-白色可能会出现多次，但对应的goods不一样。
							for(int a=0;a<page.size();a++){
								//循环多条数据
								prodSpecOne = null;
								Map<String,Object> params = new HashMap<String,Object>();
								params.put("goodsId",page.get(a).getGoodsId());
								PageBounds pagebs = new PageBounds();
								pagebs.setPageNumber(1);
								pagebs.setPageSize(Integer.MAX_VALUE);
								//当前数据的goodsId查找所有商品对应规格信息， {颜色-白色，尺码-160}
								PageList<ProdSpec> specPageList = prodSpecMapper.findPage(params,pagebs);
								for(int z =0;z<specStrs.length;z++){
									//所有规格全部都匹配，才可作为对应goods
									boolean isSameSpecId = specPageList.get(z).getSpecId().equals(Long.parseLong(specStrs[z]));
									//相同的规格值 如 白色
									boolean isSameSpecValue = specPageList.get(z).getSpecValueId().equals(Long.parseLong(specValStrs[z]));
									if(isSameSpecId && isSameSpecValue){
										prodSpecOne = specPageList.get(z);
									}else{
										prodSpecOne = null;
										break;
									}
								}
								//找到了规格之前保存的值
								if(prodSpecOne != null){
									System.out.println("");
									break;
								}
							}
							if(prodSpecOne != null){
								//找到该规格对应商品（原商品，创建产品时自动创建的）,即可找到该商品所有规格，将其数据中的商品id改为新的即可
								doProdSpec(db,prodSpecOne);
								break;
							}
						}else{
							ProdSpec prodSpec = page.get(page.size()-1);
							doProdSpec(db,prodSpec);
							break;
						}
					}
				}
				//设置商品的规格明细
				db.setSpecNames(getSpecName(specStr,specValStr));
				//可供solr搜索的name
				String solrName = db.getFullName() + " " + db.getSpecNames() + getCatIdsStr(cat);
				db.setSolrName(solrName);
			}else{
				//单规格
				List<GoodsResp> goods = goodsMapper.findListByProdId(prod.getId());
				if (goods.size()>0){
					GoodsResp resp = goods.get(0);
					db.setFirstImg(resp.getFirstImg());
					db.setImages(resp.getImages());
					db.setSolrName(prod.getName() + " " + db.getFullName() + getCatIdsStr(cat));
					db.setTagIds(resp.getTagIds());
					goodsMapper.delete(resp.getId());
				}
			}
			goodsMapper.update(db);
			prodService.updateFull(prod);
			status = 0;
		}catch (Exception e){
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * 根据cat拼接供solr使用的catId字符串
	 * @param cat
	 * @return
	 */
	@Override
	public String getCatIdsStr(Cat cat) {
		StringBuffer sb = new StringBuffer();
		for (String id : cat.getIdPath().split(",")) {
			sb.append(" catId" + id + "_");
		}
		return sb.toString();
	}

	/**
	 * 根据cat拼接供solr使用的catNameFull字符串
	 * @param cat
	 * @return
	 */
	@Override
	public String getCatNamesStr(Cat cat) {
		StringBuffer sb = new StringBuffer();
		Cat dhsCat = null;
		Long catId = null;
		for (String id : cat.getIdPath().split(",")) {
			catId = CastUtil.castToLong(id);
			if(catId != null){
				dhsCat = catMapper.findById(Long.valueOf(id));
				sb.append(" " + dhsCat.getName());
			}
		}
		return sb.toString();
	}

	private void doProdSpec(Goods db,ProdSpec prodSpec){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("goodsId",prodSpec.getGoodsId());
		PageBounds pagebs = new PageBounds();
		pagebs.setPageNumber(1);
		pagebs.setPageSize(Integer.MAX_VALUE);
		PageList<ProdSpec> specPageList = prodSpecMapper.findPage(params,pagebs);
		for(ProdSpec spec:specPageList){
			goodsMapper.delete(spec.getGoodsId());
			spec.setGoodsId(db.getId());
			prodSpecMapper.update(spec);
		}
	}
	@Override
	public Boolean doEditGoods(HttpServletRequest request, Goods model) {
		Boolean status = false;
		try{
			String specStr = request.getParameter("specStr");
			String specValStr = request.getParameter("specValStr");
			String prodSpecIdStr = request.getParameter("prodSpecIdStr");
			String imgs = request.getParameter("imgs");
			//编辑商品
			Goods db = goodsMapper.findById(model.getId());
			db.setCatId(model.getCatId());
			db.setProdId(model.getProdId());
			db.setFullName(model.getFullName());
			db.setTagIds(model.getTagIds());
			db.setStock(model.getStock());
			db.setType(1);//自营
			db.setEditTime(new Date().getTime());
			db.setSort(model.getSort());
			db.setIsGive(model.getIsGive());
			db.setMarketPrice(model.getMarketPrice());
			db.setIsMarketable(model.getIsMarketable());
			if(StringUtils.isNotEmpty(imgs)){
				String[] imgArray = imgs.split(",");
				db.setFirstImg(imgArray[0]);
				if(imgArray.length>1){
					db.setImages(imgs.substring(imgs.indexOf(","),imgs.length()));
				}
			}
			goodsMapper.update(db);
			//商品图片
			if(StringUtils.isNotEmpty(imgs)){
				doImgStr(db.getProdId(),imgs,specStr,specValStr);
			}
			//商品规格
			if(StringUtils.isNotEmpty(prodSpecIdStr)){
				doGoodsSpec(db,specStr,specValStr,prodSpecIdStr);
			}
			status = true;
		}catch (Exception e){
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public List<GoodsResp> getListByCatId(Long catId) {
		return goodsMapper.findListByCatId(catId);
	}

	@Override
	public GoodsResp getRespById(Long id) {
		return goodsMapper.findRespById(id);
	}


	private void doImgStr(Long prodId , String imgs , String specStr , String specValStr){
		Map<String,Object> map = new HashMap<String,Object>();
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPageNumber(1);
		pageBounds.setPageSize(Integer.MAX_VALUE);
		String [] specArray = specStr.split(",");
		String [] specValArray = specValStr.split(",");
		for(int i = 0 ; i< specArray.length ; i ++){
			ProdImg img = new ProdImg();
			map.put("specId",specArray[i]);
			map.put("specValueId",specValArray[i]);
			map.put("prodId",prodId);
			PageList<ProdImg> prodImgPage = prodImgMapper.findPage(map,pageBounds);
			if(prodImgPage.size()>0){
				img = prodImgPage.get(0);
				img.setImgs(imgs);
				prodImgMapper.update(img);
			}else{
				img.setSpecId(Long.parseLong(specArray[i]));
				img.setSpecValueId(Long.parseLong(specValArray[i]));
				img.setImgs(imgs);
				img.setProdId(prodId);
				prodImgMapper.insert(img);
			}
		}
	}


	private void doGoodsSpec(Goods db,String specStr,String specValStr,String prodSpecIdStr){
		String [] specArray = specStr.split(",");
		String [] specValArray = specValStr.split(",");
		String [] prodSpecIdArray = prodSpecIdStr.split(",");

		if(prodSpecIdArray.length > 0){
			for(int i = 0 ; i < prodSpecIdArray.length ; i++){
				ProdSpec spec = prodSpecMapper.findById(Long.parseLong(prodSpecIdArray[i]));
				if(ObjectUtils.isNotEmpty(spec)){
					prodSpecMapper.delete(spec.getId());
				}
			}
		}
		for(int i = 0 ; i < specArray.length; i++){
			ProdSpec spec = new ProdSpec();
			spec.setSpecId(Long.parseLong(specArray[i]));
			spec.setSpecValueId(Long.parseLong(specValArray[i]));
			spec.setProdId(db.getProdId());
			spec.setGoodsId(db.getId());
			prodSpecMapper.insert(spec);
			spec.setSortId(spec.getId());
			prodSpecMapper.update(spec);
		}
	}

	@Override
	public String getSpecName(String specStr,String specValStr){
		String specName = "";
		String []specStrs = specStr.split(",");
		String []specValStrs = specValStr.split(",");

		for(int i =0; i<specStrs.length;i++){
			Spec spec = specMapper.findById(Long.parseLong(specStrs[i]));
			SpecValue value = specValueMapper.findById(Long.parseLong(specValStrs[i]));
			if(ObjectUtils.isNotEmpty(spec) && ObjectUtils.isNotEmpty(value)){
				specName+=spec.getName()+":"+value.getSpecValue()+";";
			}
		}
		return specName;
	}

	@Override
	public Map<String,Object> getGoodsListByProd(ProdSpec prodSpec,Region dhsRegion) {
		List<GoodsResp> goodsResps = new ArrayList<GoodsResp>();
		Map<String,Object> params = new HashMap<String,Object>();
		ProdSpec specs = prodSpecMapper.findById(prodSpec.getId());
		ProdResp prod = prodMapper.findRespById(prodSpec.getProdId());
		prod = setGoodsList(prod,dhsRegion);
		for(GoodsResp resp : prod.getGoodsRespList()){
			if(resp.getIsDelete() == 0 && resp.getIsMarketable() == 1){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("specId",specs.getSpecId());
				map.put("specValueId",specs.getSpecValueId());
				map.put("prodId",specs.getProdId());
				map.put("goodsId",resp.getId());
				ProdSpec spec = prodSpecMapper.findByParams(map);
				if(ObjectUtils.isNotEmpty(spec)){
					goodsResps.add(resp);
				}
			}
		}
		params.put("goodsResps",goodsResps);
		params.put("prod",prod);
		return params;
	}

	@Override
	public List<GoodsResp> getByProdId(Map map) {
		return goodsMapper.findByProdId(map);
	}


	@Transactional
	public ProdResp setGoodsList(ProdResp prodSpec,Region dhsRegion){
		Map<String,Object> map = new HashMap<String,Object>();
		if(ObjectUtils.isNotEmpty(dhsRegion)){
			map.put("countyId",dhsRegion.getId());
		}
		map.put("prodId",prodSpec.getId());
		List<GoodsResp> resps = goodsMapper.findByProdId(map);
		if(resps.size()>0){
			for(GoodsResp resp : resps){
				if(ObjectUtils.isNotEmpty(resp)){
					if(resp.getStock() != null && resp.getStock() > 0){
						prodSpec.setIsHaveStock(1);
					}
				}
			}
		}
		prodSpec.setGoodsRespList(resps);
		return prodSpec;
	}

	@Transactional
	public List<GoodsResp> getMemberFavouriteList(Map map,HttpServletRequest request,PageBounds pageBounds) {
		PageList<GoodsResp> list = null;
		Member member =HqUtil.getLoginMember(request);
		if(ObjectUtils.isNotEmpty(member)){
			map.put("memberId",member.getId());
			list = goodsMapper.findMemberFavouriteList(map,pageBounds);
			request.setAttribute("pagination",list.getPagination());
			request.setAttribute("pageNumber",list.getPagination().getPageNumber());
			request.setAttribute("pageCount",list.getPagination().getTotalPages());
			request.setAttribute("thisPageNumber",list.getPagination().getPageNumber());
			request.setAttribute("thisPageCount",list.getPagination().getTotalPages());
			request.setAttribute("thisListCount",list.getPagination().getTotalCount());
		}
		return list;
	}

	@Override
	public List<Goods> getListByGoodsName(String fullName) {
		List<Goods> goods = new ArrayList<Goods>();
		Set<Goods> goodsesSet = new HashSet<Goods>();
		if(StringUtils.isNotEmpty(fullName)){
			goodsesSet.addAll(goodsMapper.findListByGoodsName(fullName));
		}
		goods.addAll(goodsesSet);
		return goods;
	}

	@Override
	public List<GoodsResp> getGoodsErrorPage() {
		Goods goods = new GoodsResp();
		goods.setIsDelete(0);
		goods.setIsMarketable(1);
		goods.setType(1);
		return goodsMapper.findGoodsErrorPage(goods);
	}

}
