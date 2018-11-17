package com.sm.pfprod.web.rest.biz.inquisition;

import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.enums.YesOrNoNum;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.dto.common.PfCatalogueTreeDto;
import com.sm.pfprod.model.entity.BasInques;
import com.sm.pfprod.model.entity.BasInquesAnswer;
import com.sm.pfprod.model.entity.BasInquesCa;
import com.sm.pfprod.model.enums.OperationTypeEnum;
import com.sm.pfprod.service.biz.inquisition.PfInquisitionService;
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

/**
 * @ClassName: PfInquisitionController
 * @Description: 问诊相关
 * @Author yangtongbin
 * @Date 2018/10/1
 */
@Controller
@RequestMapping(value = "/pf/r/inquisition")
public class PfInquisitionRestController extends BaseController {

    @Resource
    private PfInquisitionService pfInquisitionService;


    /**
     * 问诊题库分类树
     *
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_SUPER')")
    @RequestMapping(value = "/question/classify/tree", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject listQuestionClassifyTree() {
        return ResultObject.createSuccess("listQuestionClassifyTree", ResultObject.DATA_TYPE_LIST,
                pfInquisitionService.listQuestionClassifyTree());
    }

    /**
     * 新增问诊题库分类信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_SUPER')")
    @RequestMapping(value = "/question/classify/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject addQuestionClassify(@RequestBody BasInquesCa dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("addQuestionClassify", ResultObject.DATA_TYPE_OBJECT,
                pfInquisitionService.addQuestionClassify(dto));
    }

    /**
     * 编辑问诊题库分类信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010', 'ROLE_SUPER')")
    @RequestMapping(value = "/question/classify/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject editQuestionClassify(@RequestBody BasInquesCa dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdInquesCa() != null, "idInquesCa");
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfInquisitionService.editQuestionClassify(dto) ? ResultObject.createSuccess("editQuestionClassify", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("editQuestionClassify", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);

    }

    /**
     * 删除问诊题库分类信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_SUPER')")
    @RequestMapping(value = "/question/classify/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject delQuestionClassify(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfInquisitionService.delQuestionClassify(dto) ? ResultObject.createSuccess("delQuestionClassify", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("delQuestionClassify", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 新增问诊信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_SUPER')")
    @RequestMapping(value = "/question/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject addQuestion(@RequestBody BasInques dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getDesInques()), "desInques");
        Assert.isTrue(dto.getIdInquesCa() != null, "idInquesCa");
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("addQuestion", ResultObject.DATA_TYPE_OBJECT,
                pfInquisitionService.addQuestion(dto));
    }

    /**
     * 编辑问诊信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010', 'ROLE_SUPER')")
    @RequestMapping(value = "/question/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject editQuestion(@RequestBody BasInques dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdInques() != null, "idInques");
        Assert.isTrue(StringUtils.isNotBlank(dto.getDesInques()), "desInques");
        Assert.isTrue(dto.getIdInquesCa() != null, "idInquesCa");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfInquisitionService.editQuestion(dto) ? ResultObject.createSuccess("editQuestion", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("editQuestion", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);

    }

    /**
     * 删除问诊信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_SUPER')")
    @RequestMapping(value = "/question/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject delQuestion(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setStatus(YesOrNoNum.YES.getCode());
        return pfInquisitionService.delQuestion(dto) ? ResultObject.createSuccess("delQuestion", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("delQuestion", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 停用、启用
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_SUPER')")
    @RequestMapping(value = "/question/updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject updateQuestionStatus(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setOperationType(OperationTypeEnum.UPDATE_STATUS.getCode());
        return pfInquisitionService.delQuestion(dto) ? ResultObject.createSuccess("updateQuestionStatus", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("updateQuestionStatus", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 删除答案信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_SUPER')")
    @RequestMapping(value = "/question/answer/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject delAnswer(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfInquisitionService.delAnswer(dto) ? ResultObject.createSuccess("delAnswer", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("delAnswer", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 保存答案信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_SUPER')")
    @RequestMapping(value = "/question/answer/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject saveAnswer(@RequestBody BasInquesAnswer dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getDesAnswer()), "desAnswer");
        Assert.isTrue(dto.getIdInques() != null, "idInques");
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("saveAnswer", ResultObject.DATA_TYPE_OBJECT,
                pfInquisitionService.saveAnswer(dto));
    }

}
