package com.gjw.bean.dto;

import com.gjw.bean.FaceManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author gjw
 * @Date 2021/3/23 16:06
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class FaceManagerVo extends FaceManager {

    private Integer count;

}
