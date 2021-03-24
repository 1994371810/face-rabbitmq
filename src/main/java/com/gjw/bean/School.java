package com.gjw.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author gjw
 * @Date 2021/3/20 20:43
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class School implements Serializable {

    private Integer id;
    private String name;
}
