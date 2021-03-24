package com.gjw.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author gjw
 * @Date 2021/3/22 16:02
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FaceTaskLog implements Serializable {


    private Integer id;

    /**
     * 类型 MQTaskType
     * */
    private Integer type;

    /**
     * 消息体json数据
     * */
    private String body;

    /**
     * 状态
     * */
    private Integer status;

    /**
     * 任务发送次数
     * */
    private Integer count;
}
