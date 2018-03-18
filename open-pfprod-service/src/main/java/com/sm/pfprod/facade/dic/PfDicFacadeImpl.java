package com.sm.pfprod.facade.dic;

import com.sm.open.care.core.annotation.Loggable;
import com.sm.open.care.core.enums.Level;
import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.pfprod.model.dto.system.dic.PfDicDto;
import com.sm.pfprod.model.entity.SysDictionary;
import com.sm.pfprod.model.param.PageParam;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.result.ResultFactory;
import com.sm.pfprod.service.system.dic.PfDicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component("pfDicFacade")
public class PfDicFacadeImpl implements PfDicFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(PfDicFacadeImpl.class);

    @Resource
    private PfDicService pfDicService;

    @Loggable(bizDec = "获取字典分组列表", level = Level.info)
    @Override
    public PageResult<SysDictionary> listDicGroups(PfDicDto dto) {
        try {
            LOGGER.debug("获取字典分组列表", dto.toString());
            PageParam.initPageDto(dto);
            return ResultFactory.initPageResultWithSuccess(0L, pfDicService.listDicGroups(dto));
        } catch (Exception e) {
            return ResultFactory.initPageResultWithError(PfDicConstant.SELECT_DIC_ERROR,
                    PfDicConstant.SELECT_DIC_ERROR_MSG);
        }
    }

    @Loggable(bizDec = "获取字典枚举", level = Level.info)
    @Override
    public PageResult<SysDictionary> listEnums(PfDicDto dto) {
        try {
            PageParam.initPageDto(dto);
            return ResultFactory.initPageResultWithSuccess(pfDicService.countEnum(dto), pfDicService.listEnums(dto));
        } catch (Exception e) {
            return ResultFactory.initPageResultWithError(PfDicConstant.SELECT_PAGE_DIC_ENUMS_ERROR,
                    PfDicConstant.SELECT_PAGE_DIC_ENUMS_ERROR_MSG);
        }
    }

    @Loggable(bizDec = "新增字典", level = Level.info)
    @Override
    public boolean addDic(SysDictionary dto) {
        if (pfDicService.isExistDic(dto.getDictCode())) {
            throw new BizRuntimeException(PfDicConstant.ADD_DIC_ISEXIST, PfDicConstant.ADD_DIC_ISEXIST_MSG);
        }
        return pfDicService.addDic(dto);
    }

    @Loggable(bizDec = "修改字典", level = Level.info)
    @Override
    public boolean editDic(SysDictionary dto) {
        return pfDicService.editDic(dto);
    }

    @Loggable(bizDec = "删除字典枚举", level = Level.info)
    @Override
    public boolean delDic(List<Long> list) {
        return pfDicService.delDic(list);
    }

    @Loggable(bizDec = "新增枚举", level = Level.info)
    @Override
    public boolean addEnum(SysDictionary dto) {
        if (pfDicService.isExistEnum(dto.getDictCode(), dto.getGroupCode())) {
            throw new BizRuntimeException(PfDicConstant.ADD_ENUM_ISEXIST, PfDicConstant.ADD_ENUM_ISEXIST_MSG);
        }
        return pfDicService.addEnum(dto);
    }

    @Loggable(bizDec = "修改枚举", level = Level.info)
    @Override
    public boolean editEnum(SysDictionary dto) {
        return pfDicService.editDic(dto);
    }

}
