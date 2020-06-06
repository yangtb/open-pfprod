package com.sm.pfprod.web.rest.biz.inquisition;

import com.alibaba.fastjson.JSON;
import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.enums.YesOrNoNum;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.model.dto.biz.inquisition.PfInquisitionQuestionDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.BasInques;
import com.sm.pfprod.model.entity.BasInquesAnswer;
import com.sm.pfprod.model.entity.BasInquesCa;
import com.sm.pfprod.model.enums.OperationTypeEnum;
import com.sm.pfprod.model.enums.SysDicGroupEnum;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.PfXmSelectVo;
import com.sm.pfprod.model.vo.biz.PfTreeSelectVo;
import com.sm.pfprod.model.vo.dic.PfDicCache;
import com.sm.pfprod.service.biz.inquisition.PfInquisitionService;
import com.sm.pfprod.web.portal.BaseController;
import com.sm.pfprod.web.security.CurrentUserUtils;
import com.sm.pfprod.web.util.EnumUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

    @Resource
    private EnumUtil enumUtil;

    /**
     * 问题标签treeSelect
     *
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_ORG_MG','ROLE_FAQ0010','ROLE_EXM0040','ROLE_SUPER')")
    @PostMapping(value = "/question/classify/label")
    @ResponseBody
    public List<PfTreeSelectVo> selectQuestionClassifyLabel() {
        List<PfTreeSelectVo> treeSelectVos = new ArrayList<>();
        List<PfDicCache> dicCaches = enumUtil.getEnumList(SysDicGroupEnum.INQUES_LABEL.getCode());
        for (PfDicCache pfDicCache : dicCaches) {
            if (pfDicCache.getDictCode().length() == 2) {
                PfTreeSelectVo pfTreeSelectVo = new PfTreeSelectVo();
                pfTreeSelectVo.setId(pfDicCache.getDictCode());
                pfTreeSelectVo.setName(pfDicCache.getDictName());
                pfTreeSelectVo.setOpen(true);
                pfTreeSelectVo.setChildren(this.getChildren(pfDicCache.getDictCode(), dicCaches));
                treeSelectVos.add(pfTreeSelectVo);
            }
        }
        return treeSelectVos;
    }

    private List<PfTreeSelectVo> getChildren(String parentCode, List<PfDicCache> dicCaches) {
        List<PfTreeSelectVo> children = new ArrayList<>();
        for (PfDicCache pfDicCache : dicCaches) {
            if (pfDicCache.getDictCode().length() == 4 && pfDicCache.getDictCode().startsWith(parentCode)) {
                PfTreeSelectVo pfTreeSelectVo = new PfTreeSelectVo();
                pfTreeSelectVo.setId(pfDicCache.getDictCode());
                pfTreeSelectVo.setName(pfDicCache.getDictName());
                pfTreeSelectVo.setOpen(true);
                children.add(pfTreeSelectVo);
            }
        }
        return children;
    }


    @PreAuthorize("hasAnyRole('ROLE_ORG_MG','ROLE_FAQ0010','ROLE_EXM0040','ROLE_SUPER')")
    @PostMapping(value = "/question/classify/label/xmSelect")
    @ResponseBody
    public List<PfXmSelectVo> selectQuestionClassifyLabelXm() {
        List<PfXmSelectVo> treeSelectVos = new ArrayList<>();
        List<PfDicCache> dicCaches = enumUtil.getEnumList(SysDicGroupEnum.INQUES_LABEL.getCode());
        for (PfDicCache pfDicCache : dicCaches) {
            if (pfDicCache.getDictCode().length() == 2) {
                PfXmSelectVo pfTreeSelectVo = new PfXmSelectVo();
                pfTreeSelectVo.setValue(pfDicCache.getDictCode());
                pfTreeSelectVo.setName(pfDicCache.getDictName());
                pfTreeSelectVo.setChildren(this.getChildrenXm(pfDicCache.getDictCode(), dicCaches));
                treeSelectVos.add(pfTreeSelectVo);
            }
        }
        return treeSelectVos;
    }

    private List<PfXmSelectVo> getChildrenXm(String parentCode, List<PfDicCache> dicCaches) {
        List<PfXmSelectVo> children = new ArrayList<>();
        for (PfDicCache pfDicCache : dicCaches) {
            if (pfDicCache.getDictCode().length() == 4 && pfDicCache.getDictCode().startsWith(parentCode)) {
                PfXmSelectVo pfTreeSelectVo = new PfXmSelectVo();
                pfTreeSelectVo.setValue(pfDicCache.getDictCode());
                pfTreeSelectVo.setName(pfDicCache.getDictName());
                children.add(pfTreeSelectVo);
            }
        }
        return children;
    }

    /**
     * 问诊题库分类树
     *
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_EXM0030','ROLE_EXM0300','ROLE_SUPER')")
    @RequestMapping(value = "/question/classify/tree", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject listQuestionClassifyTree() {
        return ResultObject.createSuccess("listQuestionClassifyTree", ResultObject.DATA_TYPE_LIST,
                pfInquisitionService.listQuestionClassifyTree());
    }

    /**
     * 问诊题库分类树treeSelect
     *
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_EXM0030','ROLE_EXM0300','ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/question/classify/tree/select", method = RequestMethod.POST)
    @ResponseBody
    public PfTreeSelectVo listQuestionClassifyTreeSelect() {
        String str = pfInquisitionService.listQuestionClassifyTreeSelect();
        List<PfTreeSelectVo> list = JSON.parseArray(str, PfTreeSelectVo.class);
        PfTreeSelectVo pfTreeSelectVo = new PfTreeSelectVo();
        pfTreeSelectVo.setId("0");
        pfTreeSelectVo.setName("全部");
        pfTreeSelectVo.setOpen(true);
        pfTreeSelectVo.setChildren(list);
        return pfTreeSelectVo;
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

    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/question/list", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject listQuestion(@RequestBody PfInquisitionQuestionDto dto) {
        // 默认取1000，一般没这么多
        dto.setPage(1);
        dto.setLimit(1000);
        PageResult pageResult = pfInquisitionService.listQuestion(dto);
        return ResultObject.createSuccess("listQuestion", ResultObject.DATA_TYPE_LIST, pageResult.getData());
    }

}
