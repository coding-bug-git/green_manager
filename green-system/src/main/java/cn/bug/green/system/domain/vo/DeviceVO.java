package cn.bug.green.system.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * @author coding-bug
 * Date 2022-01-04 13:51
 */
@Data
public class DeviceVO {
    private String deviceId;
    private List<String> channelIds;
}
