package com.sm.pfprod.integration.system.dic;

import com.sm.open.core.facade.model.param.pf.system.dic.PfDicParam;
import com.sm.open.core.facade.model.param.pf.system.dic.SysDictionaryParam;
import com.sm.open.core.facade.model.result.pf.system.dic.SysDictionaryResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.open.core.facade.pf.system.dic.PfDicFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class DicClient {

    @Resource
    private PfDicFacade pfDicFacade;


    public PfPageResult<SysDictionaryResult> listDicGroups(PfDicParam param) {
        return pfDicFacade.listDicGroups(param);
    }

    public PfPageResult<SysDictionaryResult> listEnums(PfDicParam param) {
        return pfDicFacade.listEnums(param);
    }


    public CommonResult<Boolean> addDic(SysDictionaryParam param) {
        return pfDicFacade.addDic(param);
    }


    public CommonResult<Boolean> editDic(SysDictionaryParam param) {
        return pfDicFacade.editDic(param);
    }

    public CommonResult<Boolean> delDic(List<Long> list) {
        return pfDicFacade.delDic(list);
    }


    public CommonResult<Boolean> addEnum(SysDictionaryParam param) {
        return pfDicFacade.addEnum(param);
    }


    public CommonResult<Boolean> editEnum(SysDictionaryParam param) {
        return pfDicFacade.editEnum(param);
    }

    public CommonResult<List<SysDictionaryResult>> listAllEnums() {
        return pfDicFacade.listAllEnums();
    }

}
