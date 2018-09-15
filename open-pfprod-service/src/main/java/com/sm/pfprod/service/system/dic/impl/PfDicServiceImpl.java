package com.sm.pfprod.service.system.dic.impl;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.system.dic.PfDicParam;
import com.sm.open.core.facade.model.param.pf.system.dic.SysDictionaryParam;
import com.sm.open.core.facade.model.result.pf.system.dic.SysDictionaryResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.pfprod.integration.system.dic.DicClient;
import com.sm.pfprod.model.dto.system.dic.PfDicDto;
import com.sm.pfprod.model.entity.SysDictionary;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.system.dic.PfDicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("pfDicService")
public class PfDicServiceImpl implements PfDicService {

    @Resource
    private DicClient dicClient;


    @Override
    public PageResult<SysDictionary> listDicGroups(PfDicDto dto) {
        PfPageResult<SysDictionaryResult> result = dicClient.listDicGroups(BeanUtil.convert(dto, PfDicParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public PageResult<SysDictionary> listEnums(PfDicDto dto) {
        PfPageResult<SysDictionaryResult> result = dicClient.listEnums(BeanUtil.convert(dto, PfDicParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean addDic(SysDictionary dto) {
        CommonResult<Boolean> result = dicClient.addDic(BeanUtil.convert(dto, SysDictionaryParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        return false;
    }

    @Override
    public boolean editDic(SysDictionary dto) {
        CommonResult<Boolean> result = dicClient.editDic(BeanUtil.convert(dto, SysDictionaryParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        return false;
    }

    @Override
    public boolean delDic(List<Long> list) {
        CommonResult<Boolean> result = dicClient.delDic(list);
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        return false;
    }

    @Override
    public boolean addEnum(SysDictionary dto) {
        CommonResult<Boolean> result = dicClient.addEnum(BeanUtil.convert(dto, SysDictionaryParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        return false;
    }

    @Override
    public boolean editEnum(SysDictionary dto) {
        CommonResult<Boolean> result = dicClient.editEnum(BeanUtil.convert(dto, SysDictionaryParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        return false;
    }

    @Override
    public List<SysDictionary> listAllEnums() {
        CommonResult<List<SysDictionaryResult>> result = dicClient.listAllEnums();
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), SysDictionary.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

}
