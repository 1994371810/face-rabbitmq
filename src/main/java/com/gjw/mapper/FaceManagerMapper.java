package com.gjw.mapper;

import com.gjw.bean.FaceManager;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (FaceManager)表数据库访问层
 *
 * @author guojunwang
 * @since 2021-03-20 20:07:51
 */
public interface FaceManagerMapper {


    boolean batchInsert( @Param("list") List<FaceManager> faceManager);

    List<FaceManager> getFaceManagerByAppId(@Param("appId") Integer appId,@Param("status")Integer status);

    boolean updateStatus(@Param("list")List<FaceManager> faceManagers ,@Param("status") Integer status);

    FaceManager getByStudentId(Long studentId);
}

