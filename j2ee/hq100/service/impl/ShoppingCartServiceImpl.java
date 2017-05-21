/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.text.DecimalFormat;
import java.util.*;

import com.up72.framework.util.ObjectUtils;
import com.up72.framework.util.holder.ApplicationContextHolder;
import com.up72.hq.constant.Cnst;
import com.up72.hq.constant.ShopConstans;
import com.up72.hq.dto.resp.GoodsResp;
import com.up72.hq.dto.resp.ShoppingCartResp;
import com.up72.hq.model.*;
import com.up72.hq.dao.*;
import com.up72.hq.redis.RedisCacheClient;
import com.up72.hq.service.*;

import com.up72.framework.util.page.PageBounds;

import com.up72.hq.utils.CookieUtil;
import com.up72.hq.utils.HqUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.up72.hq.service.IShoppingCartService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 购物车DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class ShoppingCartServiceImpl implements  IShoppingCartService{
	
	@Autowired
	private ShoppingCartMapper shoppingCartMapper;

	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private FavouriteMapper favouriteMapper;
	
	public void save(ShoppingCart shoppingCart){
		shoppingCartMapper.insert(shoppingCart);
	}

    public void update(ShoppingCart shoppingCart){
		shoppingCartMapper.update(shoppingCart);
	}
	
    public void delete(java.lang.Long id){
		shoppingCartMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public ShoppingCart getById(java.lang.Long id){
		return shoppingCartMapper.findById(id);
	}

	@Transactional(readOnly=true)
	public ShoppingCart getByShopping(ShoppingCart shoppingCart){
		return shoppingCartMapper.findByShopping(shoppingCart);
	}


	@Transactional(readOnly=true)
    public Page<ShoppingCart> getPage(Map params, PageBounds rowBounds){
        PageList list = shoppingCartMapper.findPage(params, rowBounds);
		return new Page<ShoppingCart>(list,list.getPagination());
	}

	public String addShoppingCart(HttpServletRequest request, HttpServletResponse response, Long goodsId,Integer count) {

		String flag="success";

		Goods goods=goodsMapper.findById(goodsId);

		if(goods .getStock() >= count.intValue()){
			Member member= HqUtil.getLoginMember(request);
			//cookie key 值
			String key= CookieUtil.cookieRandom(request, response);

			if(ObjectUtils.isEmpty(member)){//没有登录
				RedisCacheClient redisCacheClient=(RedisCacheClient) ApplicationContextHolder.getBean("redisCacheClientCore");

				LinkedHashMap<Long, ShoppingCart> m= redisCacheClient.get(key);

				m=m==null?new LinkedHashMap<Long, ShoppingCart>():m;

				boolean isEmpty=true;//检查是否有相同商品
				for(Long goodsKey:m.keySet()){
					if(goodsKey.equals(goodsId)){
						ShoppingCart shoppingCart=	m.get(goodsKey);
						shoppingCart.setCount(shoppingCart.getCount() + count.intValue() );
						m.put(goodsId,shoppingCart);
						isEmpty=false;
						break;//有相同商品 结束检查
					}

				}
				if(isEmpty){

					//商品属性
					GoodsResp goodses = goodsMapper.findRespById(goodsId);
					ShoppingCart shoppingCart=new ShoppingCart();
					shoppingCart.setCount(count.intValue());
					shoppingCart.setAddTime(Cnst.getCurTime());
					shoppingCart.setGoodsId(goodsId);
					shoppingCart.setGoodsImg(goodses.getMianImgStr());
					shoppingCart.setGoodsName(goodses.getFullName());
					shoppingCart.setStatus(1);
					m.put(goodsId,shoppingCart);

				}
				redisCacheClient.set(key,m, ShopConstans.Cart.SECOND);
			}else{
				addShoppingCart(goodsId,member.getId(),count.intValue());//新增数据库
			}

		}else{
			flag="stock";
		}

		return flag;
	}
	public String addShoppingCart(Long goodsId, Long memberId,Integer count) {
		String flag="success";

		Goods goods=goodsMapper.findById(goodsId);

		if(goods .getStock() >= count.intValue()){
			//检查数据库是否有相同商品
			Map map=new HashMap<>();
			map.put("goodsId",goodsId);
			map.put("memberId",memberId);
			ShoppingCart shoppingCart=shoppingCartMapper.findByGoodsId(map);

			if(shoppingCart != null){ // 有相同商品 数量 +1

				shoppingCart.setCount(shoppingCart.getCount() + count.intValue());

				shoppingCartMapper.update(shoppingCart);  //更新数据库

			}else{//没有相同商品  新增购物车
				//商品属性
				GoodsResp goodsResp = goodsMapper.findRespById(goodsId);
				ShoppingCart dbshoppingCart=new ShoppingCart();
				dbshoppingCart.setCount(count.intValue());
				dbshoppingCart.setAddTime(new Date().getTime());
				dbshoppingCart.setGoodsId(goodsId);
				dbshoppingCart.setGoodsImg(goodsResp.getMianImgStr());
				dbshoppingCart.setGoodsName(goodsResp.getFullName());
				dbshoppingCart.setStatus(1);
				dbshoppingCart.setMemberId(memberId);
				shoppingCartMapper.insert(dbshoppingCart);
			}
		}else{
			flag="stock";
		}
		return flag;

	}

	@Transactional
	public Integer countShoppingCart(HttpServletRequest request, HttpServletResponse response) {
		Member member=HqUtil.getLoginMember(request);
		//cookie key 值
		String key= CookieUtil.cookieRandom(request,response);

		if(member == null) {//没有登录

			RedisCacheClient redisCacheClient=(RedisCacheClient) ApplicationContextHolder.getBean("redisCacheClientCore");

			LinkedHashMap<Long, ShoppingCart> m= redisCacheClient.get(key);

			if(m == null){
				return 0;
			}else{
				return m.size();
			}

		}else{

			Integer i=shoppingCartMapper.countShoppingCartByUserId(member.getId());
			if(i == null){
				return 0;
			}else{
				return i;
			}

		}
	}

	@Transactional
	public Map<String, Object> showShoppingCart(HttpServletRequest request, HttpServletResponse response) {
		Member member=HqUtil.getLoginMember(request);
		//cookie key 值
		String key= CookieUtil.cookieRandom(request,response);

		Map<String,Object> map=new HashMap<String,Object>();
		Double totalAmount=0.0;
		List<ShoppingCartResp> shoppingCarts=new ArrayList<ShoppingCartResp>();

		DecimalFormat df   = new DecimalFormat("######0.00");
		if(member == null) {//没有登录

			RedisCacheClient redisCacheClient=(RedisCacheClient) ApplicationContextHolder.getBean("redisCacheClientCore");

			LinkedHashMap<Long, ShoppingCart> m= redisCacheClient.get(key);

			if(m == null){
				map.put("totalAmount",0.00);
				map.put("totalCount",0);
			}else{

				for(Long goodsKey:m.keySet()){

					ShoppingCart shoppingCartRedis=m.get(goodsKey);

					ShoppingCartResp shoppingCart=new ShoppingCartResp();

					shoppingCart.setCount(shoppingCartRedis.getCount());
					shoppingCart.setGoodsName(shoppingCartRedis.getGoodsName());
					shoppingCart.setGoodsImg(shoppingCartRedis.getGoodsImg());
					shoppingCart.setGoodsId(shoppingCartRedis.getGoodsId());

					GoodsResp goods=goodsMapper.findRespById(shoppingCart.getGoodsId());

					shoppingCart.setPrice(df.format(goods.getPrice()));
					shoppingCart.setMarketPrice(df.format(goods.getMarketPrice()));

					shoppingCart.setSpecNames(goods.getSpecNames());

					Double goodsPrice=goods.getPrice();

					goodsPrice=goodsPrice * shoppingCart.getCount();

					totalAmount = totalAmount + goodsPrice;

					shoppingCart.setTotalAmount(df.format(goodsPrice));

					shoppingCarts.add(shoppingCart);

				}
				map.put("totalCount",m.size());
			}
		}else{

			List<ShoppingCartResp>  shoppingCartResps= shoppingCartMapper.findByUserId(member.getId());

			for(ShoppingCartResp shoppingCart: shoppingCartResps){
				GoodsResp goods=goodsMapper.findRespById(shoppingCart.getGoodsId());
				if(ObjectUtils.isNotEmpty(goods)){
					shoppingCart.setSpecNames(goods.getSpecNames());
					shoppingCart.setPrice(shoppingCart.getPrice()==null?"0.0":shoppingCart.getPrice());
					shoppingCart.setMarketPrice(df.format(goods.getMarketPrice()));
					Double goodsPrice=Double.parseDouble(shoppingCart.getPrice());
					goodsPrice=goodsPrice * shoppingCart.getCount();
					totalAmount = totalAmount + goodsPrice;
					shoppingCart.setTotalAmount(df.format(goodsPrice));
					shoppingCarts.add(shoppingCart);
				}
			}
			map.put("totalCount",shoppingCartResps.size());
		}
		map.put("shoppingCarts",shoppingCarts);
		map.put("totalAmount",df.format(totalAmount));

		request.setAttribute("topShoppingCart",map);
		return map;
	}
	public Boolean deleteShoppingCart(String shoppingCartIds,HttpServletRequest request,HttpServletResponse response) {
		Member member=HqUtil.getLoginMember(request);
		//cookie key 值
		String key= CookieUtil.cookieRandom(request,response);

		if(member == null) {//没有登录

			RedisCacheClient redisCacheClient = (RedisCacheClient) ApplicationContextHolder.getBean("redisCacheClientCore");

			LinkedHashMap<Long, ShoppingCart> m = redisCacheClient.get(key);

			String ids[]=shoppingCartIds.split(",");

			for(String shoppingCartId:ids) {
				if (shoppingCartId != null && !"".equals(shoppingCartId)) {
					//id解密
					Long id = Long.parseLong(shoppingCartId);
					m.remove(id);
					redisCacheClient.set(key,m, ShopConstans.Cart.SECOND);
				}
			}

		}else{
			String ids[]=shoppingCartIds.split(",");

			for(String shoppingCartId:ids){
				if(shoppingCartId != null && !"".equals(shoppingCartId)){
					//id解密
					Long id=Long.parseLong(shoppingCartId);

					ShoppingCart shoppingCart=shoppingCartMapper.findById(id);

					shoppingCart.setStatus(3);

					shoppingCartMapper.update(shoppingCart);
				}
			}
		}
		return true;
	}


	@Override
	public String moveShoppingCartToMyLike(String shoppingCartIds,HttpServletRequest request) {

		String flag="success";
		Member member=HqUtil.getLoginMember(request);
		if(member != null){
			String ids[]=shoppingCartIds.split(",");

			for(String shoppingCartId:ids){
				if(shoppingCartId != null && !"".equals(shoppingCartId)){
					//id解密
					Long id=Long.parseLong(shoppingCartId);

					ShoppingCart shoppingCart=shoppingCartMapper.findById(id);

						/*shoppingCart.setStatus(4);

						shoppingCartMapper.update(shoppingCart);*/


					Favourite favouriteE=new Favourite();
					favouriteE.setMemberId(member.getId());
					favouriteE.setSourceId(shoppingCart.getGoodsId());
					favouriteE.setType(1);

					List<Favourite> l=favouriteMapper.findFavouriteList(favouriteE);
					if(!(l != null && l.size() >= 1)){
						Favourite dbFavourite=new Favourite();
						dbFavourite.setSourceId(shoppingCart.getGoodsId());
						dbFavourite.setType(1);
						dbFavourite.setMemberId(member.getId());
						dbFavourite.setMemberId(member.getId());
						favouriteMapper.insert(dbFavourite);
					}
				}
			}

		}else{
			flag="user";
		}
		return flag;
	}


	@Transactional
	public String shoppingCartChangeCount(String shoppingCartIdIdC, Integer type,HttpServletRequest request,HttpServletResponse response) {
		Member member=HqUtil.getLoginMember(request);
		//cookie key 值
		String key= CookieUtil.cookieRandom(request,response);

		String f="success";

		if(member == null) {//没有登录

			RedisCacheClient redisCacheClient=(RedisCacheClient) ApplicationContextHolder.getBean("redisCacheClientCore");

			LinkedHashMap<Long, ShoppingCart> m= redisCacheClient.get(key);

			ShoppingCart shoppingCart=m.get(Long.parseLong(shoppingCartIdIdC));

			if(type == 1){
				Goods goodsResp=goodsMapper.findById(shoppingCart.getGoodsId());
				if(goodsResp.getStock() > shoppingCart.getCount()){

					shoppingCart.setCount(shoppingCart.getCount() + 1);

					m.put(Long.parseLong(shoppingCartIdIdC),shoppingCart);

				}else{
					f="stock";
				}
			}else{
				if(shoppingCart.getCount() - 1 == 0){
					m.remove(Long.parseLong(shoppingCartIdIdC));
					f="del";
				}else{
					shoppingCart.setCount(shoppingCart.getCount() - 1);
					m.put(Long.parseLong(shoppingCartIdIdC),shoppingCart);
				}
			}
			redisCacheClient.set(key,m, ShopConstans.Cart.SECOND);
		}else{
			Long shoppingCartId=Long.parseLong(shoppingCartIdIdC);

			ShoppingCart shoppingCart=  shoppingCartMapper.findById(shoppingCartId);

			if( type == 1){//增
				Goods goods=goodsMapper.findById(shoppingCart.getGoodsId());
				if(goods.getStock() > shoppingCart.getCount()){
					shoppingCart.setCount(shoppingCart.getCount() + 1);
				}else{
					f="stock";
				}
			}else{
				if(shoppingCart.getCount() - 1 == 0){
					shoppingCart.setStatus(3);
					f="del";
				}else{
					shoppingCart.setCount(shoppingCart.getCount() - 1);
				}
			}
			shoppingCartMapper.update(shoppingCart);
		}
		return f;
	}

	@Override
	public String addShoppingCartList(HttpServletRequest request, HttpServletResponse response, String goodsIds, Integer count) {

		String mess="";
		int successCount = 0;
		int errorCount = 0;
		if(StringUtils.isNotEmpty(goodsIds)){
			String []ids = goodsIds.split(",");
			for(int i = 0;i<ids.length;i++){
				Goods goods=goodsMapper.findById(Long.parseLong(ids[i]));
				if(ObjectUtils.isNotEmpty(goods)){
					if(goods .getStock() >= count.intValue()){
						Member member=HqUtil.getLoginMember(request);
						//cookie key 值
						String key= CookieUtil.cookieRandom(request,response);
						if(member == null){//没有登录
							RedisCacheClient redisCacheClient=(RedisCacheClient) ApplicationContextHolder.getBean("redisCacheClientCore");
							LinkedHashMap<Long, ShoppingCart> m= redisCacheClient.get(key);
							m=m==null?new LinkedHashMap<Long, ShoppingCart>():m;
							boolean isEmpty=true;//检查是否有相同商品
							for(Long goodsKey:m.keySet()){
								if(goodsKey.equals(goods.getId())){
									ShoppingCart shoppingCart=	m.get(goodsKey);
									shoppingCart.setCount(shoppingCart.getCount() + count.intValue() );
									m.put(goods.getId(),shoppingCart);
									isEmpty=false;
									break;//有相同商品 结束检查
								}
							}
							if(isEmpty){
								//商品属性
								GoodsResp goodsResp = goodsMapper.findRespById(goods.getId());
								ShoppingCart shoppingCart=new ShoppingCart();
								shoppingCart.setCount(count.intValue());
								shoppingCart.setAddTime(new Date().getTime());
								shoppingCart.setGoodsId(goods.getId());
								shoppingCart.setGoodsImg(goodsResp.getMianImgStr());
								shoppingCart.setGoodsName(goodsResp.getFullName());
								shoppingCart.setStatus(1);
								m.put(goods.getId(),shoppingCart);
							}
							redisCacheClient.set(key,m, ShopConstans.Cart.SECOND);
						}else{
							addShoppingCart(goods.getId(),member.getId(),count.intValue());//新增数据库
						}
						successCount += 1;
					}else{
						errorCount += 1;
					}
				}else{
					errorCount += 1;
				}
			}
		}else{
			mess="商品信息有误,请重试!";
		}
		if(errorCount > 0){
			if(successCount > 0){
				//添加提示
				mess = "成功添加"+successCount+"个商品,失败"+errorCount+"个";
			}else{
				mess = "添加失败!";
			}
		}else{
			mess = "成功添加"+successCount+"个商品";
		}
		return mess;
	}
	

}
