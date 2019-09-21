package com.sm.pfprod.web.rest.biz.check;

import com.alibaba.fastjson.JSON;
import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.enums.YesOrNoNum;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.BasBody;
import com.sm.pfprod.model.entity.BasBodyCa;
import com.sm.pfprod.model.entity.BasBodyResult;
import com.sm.pfprod.model.enums.OperationTypeEnum;
import com.sm.pfprod.model.vo.biz.PfTreeSelectVo;
import com.sm.pfprod.service.biz.check.PfCheckService;
import com.sm.pfprod.web.portal.BaseController;
import com.sm.pfprod.web.security.CurrentUserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: PfCheckRestController
 * @Description: 检查相关
 * @Author yangtongbin
 * @Date 2018/10/7
 */
@Controller
@RequestMapping(value = "/pf/r/check")
public class PfCheckRestController extends BaseController {

    @Resource
    private PfCheckService pfCheckService;


    /**
     * 题库分类树
     *
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0030','ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping(value = "/question/classify/tree", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject listQuestionClassifyTree() {
        return ResultObject.createSuccess("listQuestionClassifyTree", ResultObject.DATA_TYPE_LIST,
                pfCheckService.listQuestionClassifyTree());
    }

    /**
     * 问诊题库分类树treeSelect
     *
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_EXM0030','ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/question/classify/tree/select", method = RequestMethod.POST)
    @ResponseBody
    public List<PfTreeSelectVo> listQuestionClassifyTreeSelect() {
        String str = pfCheckService.listQuestionClassifyTreeSelect();
        List<PfTreeSelectVo> list = JSON.parseArray(str, PfTreeSelectVo.class);
        return list;
    }

    /**
     * 新增题库分类信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0030','ROLE_SUPER')")
    @RequestMapping(value = "/question/classify/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject addQuestionClassify(@RequestBody BasBodyCa dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("addQuestionClassify", ResultObject.DATA_TYPE_OBJECT,
                pfCheckService.addQuestionClassify(dto));
    }

    /**
     * 编辑题库分类信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0030', 'ROLE_SUPER')")
    @RequestMapping(value = "/question/classify/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject editQuestionClassify(@RequestBody BasBodyCa dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdBodyCa() != null, "idBodyCa");
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfCheckService.editQuestionClassify(dto) ? ResultObject.createSuccess("editQuestionClassify", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("editQuestionClassify", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);

    }

    /**
     * 删除题库分类信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0030','ROLE_SUPER')")
    @RequestMapping(value = "/question/classify/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject delQuestionClassify(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfCheckService.delQuestionClassify(dto) ? ResultObject.createSuccess("delQuestionClassify", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("delQuestionClassify", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 新增信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0030','ROLE_SUPER')")
    @RequestMapping(value = "/question/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject addQuestion(@RequestBody BasBody dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdBodyCa() != null, "idBodyCa");
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("addQuestion", ResultObject.DATA_TYPE_OBJECT,
                pfCheckService.addQuestion(dto));
    }

    /**
     * 编辑信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0030', 'ROLE_SUPER')")
    @RequestMapping(value = "/question/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject editQuestion(@RequestBody BasBody dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdBody() != null, "idBody");
        Assert.isTrue(dto.getIdBodyCa() != null, "idBodyCa");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfCheckService.editQuestion(dto) ? ResultObject.createSuccess("editQuestion", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("editQuestion", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);

    }

    /**
     * 删除信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0030','ROLE_SUPER')")
    @RequestMapping(value = "/question/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject delQuestion(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setStatus(YesOrNoNum.YES.getCode());
        return pfCheckService.delQuestion(dto) ? ResultObject.createSuccess("delQuestion", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("delQuestion", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 停用、启用
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0030','ROLE_SUPER')")
    @RequestMapping(value = "/question/updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject updateQuestionStatus(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setOperationType(OperationTypeEnum.UPDATE_STATUS.getCode());
        return pfCheckService.delQuestion(dto) ? ResultObject.createSuccess("updateQuestionStatus", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("updateQuestionStatus", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 删除答案信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0030','ROLE_SUPER')")
    @RequestMapping(value = "/question/answer/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject delAnswer(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfCheckService.delAnswer(dto) ? ResultObject.createSuccess("delAnswer", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("delAnswer", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 保存答案信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0030','ROLE_SUPER')")
    @RequestMapping(value = "/question/answer/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject saveAnswer(@RequestBody BasBodyResult dto) {
        /* 参数校验 */
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return  ResultObject.createSuccess("saveAnswer", ResultObject.DATA_TYPE_OBJECT,
                pfCheckService.saveAnswer(dto));
    }

}
