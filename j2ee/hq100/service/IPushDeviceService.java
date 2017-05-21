package com.up72.hq.service;


import com.up72.hq.model.PushDevice;

import java.util.List;
import java.util.Map;

public interface IPushDeviceService {

    void save(PushDevice pushDevice);

    void update(PushDevice pushDevice);

    PushDevice getByTypeAndDevice(Map<String, Object> params);

    List<PushDevice> getAll();

    void  deleteBySalesMemberId(Long salesMemberId);
}
