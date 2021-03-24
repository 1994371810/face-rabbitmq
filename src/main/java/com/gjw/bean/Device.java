package com.gjw.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author gjw
 * @Date 2021/3/20 20:47
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Device implements Serializable {

    private Integer id;

    private String deviceId;

    private String name;

    private Integer appId;
}
