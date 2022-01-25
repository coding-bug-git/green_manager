package cn.bug.green.system.domain.vo;

import lombok.Data;

import java.util.List;


/**
 * @author coding-bug
 * @description
 * @createDate 2022-01-04 13:34:26
 */
@Data
public class FactoryModelDetailVO {

    private Integer id;
    private String cnName;
    private String enName;
    private Integer order;
    private Integer modelId;

    private List<AreaVO> areas;


}
