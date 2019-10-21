package com.sm.pfprod.web.rest.biz.exam;

import com.alibaba.fastjson.JSON;
import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.enums.YesOrNoNum;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.BasInspectCa;
import com.sm.pfprod.model.entity.BasInspectItem;
import com.sm.pfprod.model.entity.BasItemResult;
import com.sm.pfprod.model.enums.OperationTypeEnum;
import com.sm.pfprod.model.vo.biz.PfTreeSelectVo;
import com.sm.pfprod.service.biz.exam.PfExamService;
import com.sm.pfprod.web.portal.BaseController;
import com.sm.pfprod.web.security.CurrentUserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: PfExamRestController
 * @Description: 检验相关
 * @Author yangtongbin
 * @Date 2018/10/7
 */
@Controller
@RequestMapping(value = "/pf/r/exam")
public class PfExamRestController extends BaseController {

    @Resource
    private PfExamService pfExamService;


    /**
     * 题库分类树
     *
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0050','ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping(value = "/question/classify/tree", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject listQuestionClassifyTree() {
        return ResultObject.createSuccess("listQuestionClassifyTree", ResultObject.DATA_TYPE_LIST,
                pfExamService.listQuestionClassifyTree());
    }

    /**
     * 问诊题库分类树treeSelect
     *
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_EXM0030','ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/question/classify/tree/select", method = RequestMethod.POST)
    @ResponseBody
    public PfTreeSelectVo listQuestionClassifyTreeSelect() {
        String str = pfExamService.listQuestionClassifyTreeSelect();
        List<PfTreeSelectVo> list = JSON.parseArray(str, PfTreeSelectVo.class);
        PfTreeSelectVo pfTreeSelectVo = new PfTreeSelectVo();
        pfTreeSelectVo.setId("0");
        pfTreeSelectVo.setName("全部");
        pfTreeSelectVo.setOpen(true);
        pfTreeSelectVo.setChildren(list);
        return pfTreeSelectVo;
    }

    /**
     * 新增题库分类信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0050','ROLE_SUPER')")
    @RequestMapping(value = "/question/classify/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject addQuestionClassify(@RequestBody BasInspectCa dto) {
        /* 参数校验 */
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("addQuestionClassify", ResultObject.DATA_TYPE_OBJECT,
                pfExamService.addQuestionClassify(dto));
    }

    /**
     * 编辑题库分类信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0050', 'ROLE_SUPER')")
    @RequestMapping(value = "/question/classify/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject editQuestionClassify(@RequestBody BasInspectCa dto) {
        /* 参数校验 */
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfExamService.editQuestionClassify(dto) ? ResultObject.createSuccess("editQuestionClassify", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("editQuestionClassify", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);

    }

    /**
     * 删除题库分类信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0050','ROLE_SUPER')")
    @RequestMapping(value = "/question/classify/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject delQuestionClassify(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfExamService.delQuestionClassify(dto) ? ResultObject.createSuccess("delQuestionClassify", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("delQuestionClassify", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 新增信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0050','ROLE_SUPER')")
    @RequestMapping(value = "/question/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject addQuestion(@RequestBody BasInspectItem dto) {
        /* 参数校验 */
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("addQuestion", ResultObject.DATA_TYPE_OBJECT,
                pfExamService.addQuestion(dto));
    }

    /**
     * 编辑信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0050', 'ROLE_SUPER')")
    @RequestMapping(value = "/question/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject editQuestion(@RequestBody BasInspectItem dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdInspect() != null, "idInspect");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfExamService.editQuestion(dto) ? ResultObject.createSuccess("editQuestion", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("editQuestion", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);

    }

    /**
     * 删除信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0050','ROLE_SUPER')")
    @RequestMapping(value = "/question/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject delQuestion(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setStatus(YesOrNoNum.YES.getCode());
        return pfExamService.delQuestion(dto) ? ResultObject.createSuccess("delQuestion", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("delQuestion", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 停用、启用
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0050','ROLE_SUPER')")
    @RequestMapping(value = "/question/updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject updateQuestionStatus(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setOperationType(OperationTypeEnum.UPDATE_STATUS.getCode());
        return pfExamService.delQuestion(dto) ? ResultObject.createSuccess("updateQuestionStatus", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("updateQuestionStatus", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 删除答案信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0050','ROLE_SUPER')")
    @RequestMapping(value = "/question/answer/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject delAnswer(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfExamService.delAnswer(dto) ? ResultObject.createSuccess("delAnswer", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("delAnswer", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 保存答案信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0050','ROLE_SUPER')")
    @RequestMapping(value = "/question/answer/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject saveAnswer(@RequestBody BasItemResult dto) {
        /* 参数校验 */
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("saveAnswer", ResultObject.DATA_TYPE_OBJECT,
                pfExamService.saveAnswer(dto));
    }

}
