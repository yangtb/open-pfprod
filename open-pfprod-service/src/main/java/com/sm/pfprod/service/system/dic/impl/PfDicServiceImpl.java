package com.sm.pfprod.service.system.dic.impl;

import com.sm.pfprod.dal.system.dic.PfDicDao;
import com.sm.pfprod.model.dto.system.dic.PfDicDto;
import com.sm.pfprod.model.entity.SysDictionary;
import com.sm.pfprod.model.enums.SysEnum;
import com.sm.pfprod.service.system.dic.PfDicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("pfDicService")
public class PfDicServiceImpl implements PfDicService {

    @Resource
    private PfDicDao pfDicDao;

    @Override
    public List<SysDictionary> listDicGroups(PfDicDto dto) {
        return pfDicDao.listDicGroups(dto);
    }

    @Override
    public List<SysDictionary> listEnums(PfDicDto dto) {
        return pfDicDao.listEnums(dto);
    }

    @Override
    public Long countEnum(PfDicDto dto) {
        return pfDicDao.countEnum(dto);
    }

    @Override
    public List<SysDictionary> listAllEnums() {
        return pfDicDao.listAllEnums();
    }

    @Override
    public boolean isExistDic(String dictCode) {
        return pfDicDao.isExistDic(dictCode) >= 1 ? true : false;
    }

    @Override
    public boolean addDic(SysDictionary dto) {
        dto.setGroupName(SysEnum.DICTIONARY_GROUP.getDesc());
        dto.setGroupCode(SysEnum.DICTIONARY_GROUP.getCode());
        return pfDicDao.addDic(dto) == 1 ? true : false;
    }

    @Override
    public boolean editDic(SysDictionary dto) {
        return pfDicDao.editDic(dto) == 1 ? true : false;
    }

    @Override
    public boolean delDic(List<Long> list) {
        return pfDicDao.delDic(list) >= 1 ? true : false;
    }

    @Override
    public boolean addEnum(SysDictionary dto) {
        return pfDicDao.addDic(dto) == 1 ? true : false;
    }

    @Override
    public boolean isExistEnum(String dictCode, String groupCode) {
        return pfDicDao.isExistEnum(dictCode, groupCode) >= 1 ? true : false;
    }

}
