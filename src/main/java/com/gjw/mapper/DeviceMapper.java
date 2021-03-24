package com.gjw.mapper;

import com.gjw.bean.Device;

import java.util.List;

/**
 * @Author gjw
 * @Date 2021/3/20 20:48
 **/
public interface DeviceMapper {

    List<Device> selectBySchoolId(Integer appId);

    boolean insert(Device device);

    List<Device> selectAll();

    Device selectByDeviceId(String deviceId);
}
