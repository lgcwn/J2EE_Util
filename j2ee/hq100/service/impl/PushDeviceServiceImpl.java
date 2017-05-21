package com.up72.hq.service.impl;


import com.up72.hq.dao.PushDeviceMapper;
import com.up72.hq.model.PushDevice;
import com.up72.hq.service.IPushDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PushDeviceServiceImpl implements IPushDeviceService {

    @Autowired
    private PushDeviceMapper pushDeviceMapper;

    @Override
    public void save(PushDevice pushDevice) {
        pushDeviceMapper.save(pushDevice);
    }

    @Override
    public void update(PushDevice pushDevice) {
        pushDeviceMapper.update(pushDevice);
    }

    @Override
    public PushDevice getByTypeAndDevice(Map<String,Object> params) {
      return   pushDeviceMapper.getByTypeAndDevice(params);
    }

    @Override
    public List<PushDevice> getAll() {
        return  pushDeviceMapper.getAll();
    }

    @Override
    public void deleteBySalesMemberId(Long salesMemberId) {
        pushDeviceMapper.deleteBySalesMemberId(salesMemberId);
    }
}
