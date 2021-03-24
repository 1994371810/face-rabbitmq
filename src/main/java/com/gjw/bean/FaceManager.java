package com.gjw.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (FaceManager)实体类
 *
 * @author guojunwang
 * @since 2021-03-20 20:07:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FaceManager implements Serializable {
    private static final long serialVersionUID = 111934210478024123L;

    private Integer id;
    /**
     * 学生id
     */
    private Integer studentId;
    /**
     * 学校id
     */
    private Integer appId;
    /**
     * 图片访问地址
     */
    private String faceUrl;

    /**
     * 状态
     * */
    private Integer status;
}
