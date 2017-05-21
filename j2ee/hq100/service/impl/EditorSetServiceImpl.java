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

import com.up72.hq.service.IEditorSetService;
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
public class EditorSetServiceImpl implements  IEditorSetService{
	
	@Autowired
	private EditorSetMapper editorSetMapper;
	
	public void save(EditorSet editorSet){
		editorSetMapper.insert(editorSet);
	}

    public void update(EditorSet editorSet){
		editorSetMapper.update(editorSet);
	}
	
    public void delete(java.lang.Long id){
		editorSetMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public EditorSet getById(java.lang.Long id){
		return editorSetMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<EditorSet> getPage(Map params, PageBounds rowBounds){
        PageList list = editorSetMapper.findPage(params, rowBounds);
		return new Page<EditorSet>(list,list.getPagination());
	}
	

}
