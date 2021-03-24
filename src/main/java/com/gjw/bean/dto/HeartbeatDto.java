package com.gjw.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author gjw
 * @Date 2021/3/23 13:56
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeartbeatDto implements Serializable {

    private String deviceId;

    private List<String> successList;

    private List<String> errorList;
}
