package com.gjw.config;

import cn.hutool.json.JSONUtil;
import com.gjw.bean.FaceManager;
import com.gjw.enums.MQTaskEnum;
import com.gjw.mapper.FaceManagerMapper;
import com.gjw.mapper.FaceTaskLogMapper;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * rabbitMQ confirmCallback
 * @Author gjw
 * @Date 2021/3/20 20:20
 **/
@Component
public class RabbitMQConfirmCallback implements RabbitTemplate.ConfirmCallback {

    @Autowired
    private FaceTaskLogMapper faceTaskLogMapper;

    @Autowired
    private FaceManagerMapper faceManagerMapper;

    /**
     * 消息是否发送到 Exchange 的回调
     * */
    @Override
    public void confirm(CorrelationData correlationData, boolean isSend, String s) {
        System.out.println("\n");
        System.out.println( "correlationData--->"+correlationData);
        System.out.println( "returndata--->"+correlationData.getReturned());
        System.out.println( "boolean--->"+isSend);
        System.out.println( "s--->"+s);

        ReturnedMessage returned = correlationData.getReturned();

        if(isSend){
            handlerOk(correlationData);
        }else{
            handlerFail(correlationData);
        }
    }

    /**
     * 处理添加成功
    * */
    public void handlerOk(CorrelationData correlationData){
        ReturnedMessage returned = correlationData.getReturned();
        //如果是添加人脸任务的消息
        if(MQTaskEnum.AddFace.getCode().equals(returned.getReplyCode())){
            //添加成功了修改 face_manager和face_task_log 记录为 1
            faceTaskLogMapper.updateStatus(correlationData.getId(),1);
            String replyText = returned.getReplyText();
            List<FaceManager> faceManagers = JSONUtil.toList(replyText, FaceManager.class);
            faceManagerMapper.updateStatus(faceManagers,1);
        }
    }


    /**
     * 处理添加失败
     * */
    public void handlerFail(CorrelationData correlationData){

        ReturnedMessage returned = correlationData.getReturned();

        //如果是添加人脸任务的消息
        //添加失败了修改 face_manager 和 face_task_log 记录为 -1
        if(MQTaskEnum.AddFace.getCode().equals(returned.getReplyCode())){
            faceTaskLogMapper.updateStatus(correlationData.getId(),-1);
            String replyText = returned.getReplyText();
            List<FaceManager> faceManagers = JSONUtil.toList(replyText, FaceManager.class);
            faceManagerMapper.updateStatus(faceManagers,-1);
        }
    }
}
