package cn.bug.green.framework.web.service;


import cn.bug.green.bi.domain.Area;
import cn.bug.green.bi.domain.Channel;
import cn.bug.green.bi.domain.Device;
import cn.bug.green.bi.domain.Factory;
import cn.bug.green.bi.mapper.green.AreaMapper;
import cn.bug.green.bi.mapper.green.ChannelMapper;
import cn.bug.green.bi.mapper.green.DeviceMapper;
import cn.bug.green.bi.mapper.green.FactoryMapper;
import cn.bug.green.system.domain.vo.AreaVO;
import cn.bug.green.system.domain.vo.DeviceVO;
import cn.bug.green.system.domain.vo.FactoryModelDetailVO;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestService {

    private final FactoryMapper factoryMapper;
    private final AreaMapper areaMapper;
    private final DeviceMapper deviceMapper;
    private final ChannelMapper channelMapper;

    @Autowired
    public TestService(FactoryMapper factoryMapper, AreaMapper areaMapper, DeviceMapper deviceMapper, ChannelMapper channelMapper) {
        this.factoryMapper = factoryMapper;
        this.areaMapper = areaMapper;
        this.deviceMapper = deviceMapper;
        this.channelMapper = channelMapper;
    }


    public List<FactoryModelDetailVO> listFactoryInfo() {
        List<Factory> factoryList = factoryMapper.selectList(null);
        List<FactoryModelDetailVO> list = new ArrayList<>();

        factoryList.forEach(factory -> {
            FactoryModelDetailVO factoryModelDetailVO = new FactoryModelDetailVO();

            List<Area> areaList = areaMapper.selectList(Wrappers.<Area>lambdaQuery().eq(Area::getFacId, factory.getId()));
            List<AreaVO> areaVOList = new ArrayList<>();

            areaList.forEach(area -> {
                AreaVO areaVO = new AreaVO();
                BeanUtils.copyProperties(area, areaVO);
                areaVOList.add(areaVO);
                List<DeviceVO> deviceVOList = new ArrayList<>();
                List<Device> deviceList = deviceMapper.selectList(Wrappers.<Device>lambdaQuery().eq(Device::getAreaId, area.getId()));
                deviceList.forEach(device -> {
                    DeviceVO deviceVO = new DeviceVO();
                    BeanUtils.copyProperties(device, deviceVO);
                    deviceVOList.add(deviceVO);
                    List<Channel> channelList = channelMapper.selectList(Wrappers.<Channel>lambdaQuery().eq(Channel::getDeviceId, device.getDeviceId()));
                    List<String> channelIds = channelList.stream().map(Channel::getChannelId).collect(Collectors.toList());
                    deviceVO.setChannelIds(channelIds);
                });
                areaVO.setCameras(deviceVOList);

            });

            BeanUtils.copyProperties(factory, factoryModelDetailVO);
            factoryModelDetailVO.setAreas(areaVOList);
            list.add(factoryModelDetailVO);
        });
        return list;
    }
}
