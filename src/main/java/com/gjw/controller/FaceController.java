package com.gjw.controller;

import com.gjw.bean.Device;
import com.gjw.bean.FaceManager;
import com.gjw.bean.dto.HeartbeatDto;
import com.gjw.service.DeviceService;
import com.gjw.service.FaceService;
import com.gjw.service.HeartbeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author gjw
 * @Date 2021/3/20 20:08
 **/
@RestController
public class FaceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private FaceService faceService;

    @Autowired
    private HeartbeatService heartbeatService;

    @GetMapping("addDevice")
    public boolean addDevice(Device device){
        deviceService.addDevice(device);
        return true;
    }

    @PostMapping("/addFace")
    public boolean addFace(@RequestBody List<FaceManager> faceManager){
        return faceService.add(faceManager,1);
    }

    @PostMapping("/heartbeat")
    public String heartbeat(@RequestBody HeartbeatDto dto){
        return heartbeatService.heartbeat(dto);
    }


    /**
     * 学生请假
     * */
    @PostMapping("/leave")
    public boolean leave(Long studentId){
        return heartbeatService.leave(studentId);
    }



}

