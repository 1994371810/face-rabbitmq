package com.gjw.enums;

import lombok.Getter;

/**
 * @Author gjw
 * @Date 2021/3/22 15:37
 **/
@Getter
public enum MQTaskEnum {

    AddFace(1,"添加人脸");


    private MQTaskEnum(int code, String text){
        this.code = code;
        this.text  = text;
    }
    private Integer code;
    private String text;
}
