package com.gjw.mapper;

import com.gjw.bean.FaceManager;
import com.gjw.bean.dto.FaceManagerVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (FaceDeviceLog)表数据库访问层
 *
 * @author guojunwang
 * @since 2021-03-20 20:07:50
 */
public interface FaceDeviceLogMapper {


    boolean insert(@Param("list") List<FaceManager> list,@Param("deviceId")Integer deviceId,@Param("appId")Integer appId);

    void updateStatus(@Param("studentIds") List<String> studentId,@Param("deviceId")Integer deviceId,@Param("status") Integer status);

    /**
     * 获取失败的人脸数据
     * */
    List<FaceManagerVo> getFailFace(Integer count);

    boolean updateSendTime(int max_retry);
}

