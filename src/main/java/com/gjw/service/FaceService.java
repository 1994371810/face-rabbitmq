package com.gjw.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.gjw.bean.Device;
import com.gjw.bean.FaceManager;
import com.gjw.bean.FaceTaskLog;
import com.gjw.bean.dto.FaceManagerVo;
import com.gjw.enums.MQTaskEnum;
import com.gjw.mapper.FaceManagerMapper;
import com.gjw.mapper.FaceTaskLogMapper;
import com.gjw.util.ListUtil;
import com.gjw.util.ReflectUtils;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author gjw
 * @Date 2021/3/20 22:27
 **/
@Service
public class FaceService {

    @Autowired
    private FaceManagerMapper faceManagerMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private FaceTaskLogMapper faceTaskLogMapper;

    @Autowired
    private DeviceService deviceService;


    /**添加人脸信息*/
    @Transactional(rollbackFor = Exception.class)
    public boolean add(List<FaceManager> faceManager,Integer priority){
        //保存数据库
        boolean isInsert = faceManagerMapper.batchInsert(faceManager);

        if (isInsert) {
            //发送rabbitMQ
            addTask(faceManager.get(0).getAppId(),priority);
        }
        return true;
    }


    public void addTask(Integer appId,Integer priority){
        List<FaceManager> faceManagers = faceManagerMapper.getFaceManagerByAppId(appId,0);
        List<Device> schoolDevice = deviceService.getSchoolDevice(appId);

        //如果该学校没有人脸数据 或 该学校没有人脸设备 直接返回
        if(CollUtil.isEmpty(faceManagers) || CollUtil.isEmpty( schoolDevice)){
            return;
        }
        //添加任务
        addFaceTask(appId,faceManagers,priority);
    }


    /**
    * 添加任务，主要用于消息重新入队
    * */
    public void addTask(List<FaceManager> allFace,Integer priority){
        Map<Integer,List<FaceManager>> faceManagerMap = new HashMap();
        ReflectUtils.toMapList(allFace,"appId", FaceManagerVo.class,faceManagerMap);

        if( CollUtil.isNotEmpty(faceManagerMap)){
            faceManagerMap.forEach( (appId,faceList) ->{
                List<Device> schoolDevice = deviceService.getSchoolDevice(appId);

                //如果该学校没有人脸数据 或 该学校没有人脸设备 直接返回
                if(CollUtil.isEmpty(faceList) || CollUtil.isEmpty( schoolDevice)){
                    return;
                }
                //添加任务
                addFaceTask(appId,faceList,priority);
            });
        }
    }



    private void addFaceTask(Integer appId,List<FaceManager> faceManager,Integer priority){
        final int maxSize = 500;

        //平均分
        List<List<FaceManager>> lists = ListUtil.averageAssign(faceManager, maxSize);

        lists.forEach( faceList -> {

            String exchange = DeviceService.getSchoolExchangeName(appId);
            //保存数据库
            FaceTaskLog log = new FaceTaskLog(null,MQTaskEnum.AddFace.getCode(), JSONUtil.toJsonStr(faceList),0,0);
            faceTaskLogMapper.save(log);

            CorrelationData data = new CorrelationData(log.getId().toString());
            ReturnedMessage message = new ReturnedMessage(null, MQTaskEnum.AddFace.getCode(),JSONUtil.toJsonStr(faceList),exchange,"");
            data.setReturned(message);

            List<FaceManager> dataList = new ArrayList(faceList);
            //任务入队 设置优先级
            rabbitTemplate.convertAndSend(exchange,"",dataList, msg ->{
                msg.getMessageProperties().setPriority(priority);
                return msg;
            },data);
        });
    }

    public FaceManager getByStudentId(Long studentId) {

        return faceManagerMapper.getByStudentId(studentId);
    }
}

