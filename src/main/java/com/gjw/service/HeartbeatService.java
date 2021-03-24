package com.gjw.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.json.JSONUtil;
import com.gjw.bean.Device;
import com.gjw.bean.FaceManager;
import com.gjw.bean.dto.FaceManagerVo;
import com.gjw.bean.dto.HeartbeatDto;
import com.gjw.mapper.FaceDeviceLogMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author gjw
 * @Date 2021/3/23 14:00
 **/
@Service
public class HeartbeatService {

    @Autowired
    private FaceDeviceLogMapper faceDeviceLogMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private FaceService faceService;


    /**
     * 最大重试次数
     * */
    private final int MAX_RETRY=5;

    @Transactional
    public String heartbeat(HeartbeatDto dto){

        Device device = deviceService.getByDeviceId(dto.getDeviceId());

        //修改成功状态
        if(!CollUtil.isEmpty(dto.getSuccessList())){
            faceDeviceLogMapper.updateStatus(dto.getSuccessList(),device.getId(),1);
        }

        if(!CollUtil.isEmpty(dto.getErrorList())){
            faceDeviceLogMapper.updateStatus(dto.getErrorList(),device.getId(),-1);
        }

        String deviceQueueName = DeviceService.getDeviceQueueName( device );

        List<FaceManager> faceManagers = (List<FaceManager>)rabbitTemplate.receiveAndConvert(deviceQueueName);

        if(faceManagers!=null){
            faceDeviceLogMapper.insert(faceManagers,device.getId(),device.getAppId());
        }
        return JSONUtil.toJsonStr(faceManagers);
    }



    /**
     * 定时任务重新发送识别失败的消息
     * */
    @Scheduled(cron = "0/5 * * * * ? *")
    public void resendTask(){
        List<FaceManagerVo> failFace = faceDeviceLogMapper.getFailFace(MAX_RETRY);

        faceDeviceLogMapper.updateSendTime(MAX_RETRY);
        //获取5次失败了的
        failFace.stream().filter( o ->o.getCount()==MAX_RETRY).forEach( o->{
            System.out.println("------->人脸识别失败发送模板消息"+o);
        });

        //获取失败次数的
        List<FaceManager> resendFace = failFace.stream().filter(o -> o.getCount() <MAX_RETRY).map( o ->{
            FaceManager faceManager = new FaceManager();
            BeanUtils.copyProperties(o,faceManager);
            return faceManager;
        }).collect(Collectors.toList());

        faceService.addTask(resendFace,0);
    }

    /**
     * 学生请假
     * */
    public boolean leave(Long studentId) {

        //1 先往执行修改名单为临时名单的任务
        FaceManager faceManager = faceService.getByStudentId(studentId);
        faceService.addTask(ListUtil.toList(faceManager),90);
        //2 使用延迟队列执行修改临时名单为永久名单的任务
        sendLeaveMsg(faceManager);
        return true;
    }

    public void sendLeaveMsg(FaceManager faceManager){
        rabbitTemplate.convertAndSend(DeviceService.deadExchangeKey,faceManager.getAppId().toString(),faceManager,message->{
            message.getMessageProperties().setPriority(100);
            return message;
        });
    }

}
