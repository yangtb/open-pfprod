package com.sm.pfprod.model.dto.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: PfDiseaseCatalogueTreeDto
 * @Description: 目录树入参
 * @Author yangtongbin
 * @Date 2018/9/29
 */
@Setter
@Getter
@ToString
public class PfCatalogueTreeDto implements Serializable {

    private static final long serialVersionUID = -6975428826879877283L;

    /**
     * 异步加载
     */
    private boolean async;

    /**
     * 操作id
     */
    private Long id;

    /**
     * 机构id
     */
    private Long idOrg;

    /**
     * 扩展id
     */
    private Long extId;

    /**
     * 包含疾病 1 包含 0不包含
     */
    private int includeDie;

    /**
     * 主目录 1 是 2否
     */
    private int mainCatalogue;

    /**
     * 主目录id
     */
    private String mainCatalogueId;

    /**
     * 搜索关键字
     */
    private String keyword;
}
