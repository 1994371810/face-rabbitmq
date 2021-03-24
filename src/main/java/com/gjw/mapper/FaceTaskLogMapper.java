package com.gjw.mapper;

import com.gjw.bean.FaceTaskLog;
import org.apache.ibatis.annotations.Param;

public interface FaceTaskLogMapper {

    boolean save(FaceTaskLog log);

    boolean updateStatus(@Param("id") String id, @Param("status")int status);
}
