package com.gjw.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (FaceDeviceLog)实体类
 *
 * @author guojunwang
 * @since 2021-03-20 20:07:50
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FaceDeviceLog implements Serializable {
    private static final long serialVersionUID = 478838266687738928L;

    private Integer id;
    /**
     * face_manager_id
     */
    private Integer studentId;
    /**
     * 设备表id
     */
    private Integer deviceId;
    /**
     * 1任务上传成功，2任务上传失败，3已下发到人脸机，4人脸添加成功，5人脸添加失败
     */
    private Integer status;
    /**
     * 人脸处理结果
     */
    private String message;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Integer appId;

    /**
     * 发送时间
     * */
    private Date sendTime;

    /**
     * 重试次数
     * */
    private Integer count;
}
