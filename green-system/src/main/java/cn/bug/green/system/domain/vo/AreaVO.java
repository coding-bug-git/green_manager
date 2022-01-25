package cn.bug.green.system.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * @author coding-bug
 * @description
 * @createDate 2022-01-04 13:55
 */
@Data
public class AreaVO {
    private Integer id;
    private String areaName;
    private String modelId;
    private Integer order;
    private List<DeviceVO> cameras;
}
