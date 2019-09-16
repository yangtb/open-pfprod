package com.sm.pfprod.model.dto.biz.tests;

import com.sm.pfprod.model.param.PageParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfTestExamTagDto extends PageParam implements Serializable {

    private static final long serialVersionUID = -1707262746906227797L;

    /**
     * 病例id
     */
    private Long idMedicalrec;

    /**
     * 病例组件编码
     */
    private String cdMedAsse;

    /**
     * 病例结果ID
     */
    private Long idTestexecResult;

    /**
     * 执行状态
     */
    private String sdTestexec;

    /**
     * 检查部位
     */
    private String sdBody;

    /**
     * 扩展字段 - 分类id
     */
    private Long extItemId;

    /**
     * 搜索关键字
     */
    private String keyword;

    private Long idMedCase;

}
