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

import com.up72.hq.service.ILabelService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class LabelServiceImpl implements  ILabelService{
	
	@Autowired
	private LabelMapper labelMapper;
	
	public void save(Label label){
		labelMapper.insert(label);
	}

    public void update(Label label){
		labelMapper.update(label);
	}
	
    public void delete(java.lang.Long id){
		labelMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public Label getById(java.lang.Long id){
		return labelMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<Label> getPage(Map params, PageBounds rowBounds){
        PageList list = labelMapper.findPage(params, rowBounds);
		return new Page<Label>(list,list.getPagination());
	}
	

}
