package com.gjw.service;

import cn.hutool.core.collection.CollUtil;
import com.gjw.bean.Device;
import com.gjw.bean.School;
import com.gjw.mapper.DeviceMapper;
import com.gjw.mapper.SchoolMapper;
import com.gjw.util.ReflectUtils;
import org.springframework.amqp.core.*;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.*;

/**
 * @Author gjw
 * @Date 2021/3/20 20:55
 **/
@Service
public class DeviceService {

    //学校交换机前缀
    private static final String faceExchangeKey = "SchoolDeviceExcahnge-";
    //学校死信队列前缀
    private static final String deadQueyeKey = "SchoolDeadQueue-";

    public static final String deadExchangeKey = "deadExchange";
    //学校设备任务队列前缀
    private static final String faceQueueKey = "faceQueue-";
    //学校设备Redis缓存key
    private static final String schoolDeviceRedisKey = "schoolDevice::";

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private SchoolMapper schoolMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**添加学校设备 */
    @Transactional(rollbackFor = Exception.class)
    public void addDevice(Device device){
        boolean isInserted = deviceMapper.insert(device);
        if(isInserted){
            createDeviceQueue(device);
            //将设备添加到缓存中
            HashOperations hashOp = redisTemplate.opsForHash();
            hashOp.put(getSchoolDeviceRedisKey(device.getAppId()),device.getId().toString(),device);
        }
    }



    /**

     查询所有学校和设备，创建学校队列，交换机和绑定规则


     * */
    @PostConstruct
    public void createSchoolExchange(){
        List<School> schools = schoolMapper.selectList();

        if(schools!=null && !schools.isEmpty()){
            schools.stream().forEach( school ->{
                String schoolExchange = getSchoolExchangeName(school.getId());
                //为每一个学校创建一个交换机
                Exchange exchange = ExchangeBuilder.fanoutExchange(schoolExchange).build();
                rabbitAdmin.declareExchange(exchange);
                //为学校创建死信队列
                createSchoolDeadQueue(school,schoolExchange);
            });
        }

        //创建队列并绑定
        Map<Integer, List<Device>> deviceMap = initSchoolDevice();
        deviceMap.forEach( (appId,deviceList) ->{
            saveDeviceCache(appId,deviceList);
            deviceList.forEach( device ->{
                createDeviceQueue(device);
            });
        });
    }


    /**
     * 通过appId获取学校的所有设备
     * */
    public List<Device> getSchoolDevice(Integer appId){

        HashOperations hashOp = redisTemplate.opsForHash();
        String key = schoolDeviceRedisKey + appId;
        List<Device> deviceList = hashOp.values(key);

        if( CollUtil.isNotEmpty(deviceList) ){return  deviceList;}

        //查询数据库，设置缓存
        deviceList = deviceMapper.selectBySchoolId(appId);
        saveDeviceCache(appId,deviceList);
        return deviceList;
    }


    /**
     * 将所有设备放入缓存
     * */
    public Map<Integer,List<Device>> initSchoolDevice(){

        List<Device> devices = deviceMapper.selectAll();

        if(devices==null || devices.isEmpty()){return Collections.emptyMap();}

        //key 为 appId value为设备id
        Map<Integer, List<Device>> schoolDevice = new HashMap();
        ReflectUtils.toMapList(devices, "appId", Device.class,schoolDevice);

        schoolDevice.forEach( (appId,deviceList) ->{
            saveDeviceCache(appId,deviceList);
        });

        return schoolDevice;
    }



    /**添加缓存*/
    private void saveDeviceCache(Integer appId,List<Device> deviceList){

        if(CollUtil.isNotEmpty(deviceList) && appId!=null){
            Map<String,Device> map = new HashMap(deviceList.size());

            CollUtil.toMap(deviceList,map ,o->o.getDeviceId());

            HashOperations hashOp = redisTemplate.opsForHash();
            hashOp.putAll(getSchoolDeviceRedisKey(appId),map);
        }
    }


    /**
     * 创建设备队列
     * */
    public void createDeviceQueue(Device device){
        //创建队列
        final String queueName = getDeviceQueueName(device);

        Map<String,Object> args = new HashMap<>();
        //设置最大优先级
        args.put("x-max-priority", 100);
        Queue queue = new Queue(queueName,true,false,false,args);

        System.out.println("添加队列-->"+rabbitAdmin.declareQueue(queue));

        //创建交换机
        FanoutExchange exchange = ExchangeBuilder.fanoutExchange( getSchoolExchangeName( device.getAppId() ) ).build();
        //绑定规则
        Binding binding = BindingBuilder.bind(queue).to(exchange);
        rabbitAdmin.declareBinding(binding);
    }


    /**
     * 获取缓存key
     * */
    public static String getSchoolDeviceRedisKey(Serializable appId){
        return schoolDeviceRedisKey+appId.toString();
    }

    /**
     * 获取交换机key
     * */
    public static String getSchoolExchangeName(Serializable appId){
        return faceExchangeKey+appId;
    }

    /**
     * 获取设备队列key
     * */
    public static String getDeviceQueueName(Device device){
        return faceQueueKey+device.getAppId()+"-"+device.getId();
    }


    /**
     * 通过设备id查询设备
     * */
    public Device getByDeviceId(String deviceId) {
        return deviceMapper.selectByDeviceId(deviceId);
    }

    public static String getSchoolDeadQueyeKey(Integer schoolId){
        return deadQueyeKey+schoolId;
    }


    public void createSchoolDeadQueue(School school,String exchangeName){
        //为每一个学校创建死信队列
        Map<String,Object> args = new HashMap();
        //设置过期时间
        args.put("x-message-ttl",1000*60*5);
        //绑定死信交换机
        args.put("x-dead-letter-exchange",exchangeName);

        Queue deadLetterQueue = new Queue(getSchoolDeadQueyeKey(school.getId()),true,false,false,args);
        rabbitAdmin.declareQueue(deadLetterQueue);

        DirectExchange deadExchange = ExchangeBuilder.directExchange("deadExchange").build();

        Binding binding = BindingBuilder.bind(deadLetterQueue).to(deadExchange).with(school.getId().toString());

        rabbitAdmin.declareBinding(binding);
    }



}
