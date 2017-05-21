/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

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

import com.up72.hq.service.IMenuService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 平台菜单配置DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class MenuServiceImpl implements  IMenuService{
	
	@Autowired
	private MenuMapper menuMapper;
	
	public void save(Menu menu){
		menuMapper.insert(menu);
	}

    public void update(Menu menu){
		menuMapper.update(menu);
	}
	
    public void delete(java.lang.Long id){
		menuMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public Menu getById(java.lang.Long id){
		return menuMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<Menu> getPage(Map params, PageBounds rowBounds){
        PageList list = menuMapper.findPage(params, rowBounds);
		return new Page<Menu>(list,list.getPagination());
	}
	

}
