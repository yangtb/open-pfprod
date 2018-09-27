/**
 * 机构表单
 */
layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['form', 'layer', 'jquery', 'laydate', 'common'], function () {
    var $ = layui.$
        , form = layui.form
        , common = layui.common
        , laydate = layui.laydate;

    //日期框渲染
    laydate.render({
        elem: '#gmtValid',
        min: 0
    });

    //Form自定义验证规则
    form.verify({
        name: function (value) {
            if (value.length > 64) {
                return '机构名称最多64个字';
            }
        },
        des: function (value) {
            if (value.length > 255) {
                return '机构描述最多255个字';
            }
        },
        addr: function (value) {
            if (value.length > 255) {
                return '联系地址最多255个字';
            }
        },
        phone: function (value) {
            if (value.length > 64) {
                return '联系电话最多64个字';
            }
        },

        e_mail: function (value) {
            if (!value) return;
            if (!value.match(/^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/)) {
                return '邮箱格式不正确';
            }
        },
        sort: [/^[1-9]\d*$/, '排序必须是正整数']
    });

    //监听指定开关
    form.on('switch(fgActiveSwitch)', function (data) {
        if (this.checked == false) {
            layer.tips('温馨提示：关闭认证后，该机构将不可用', data.othis)
        }
    });

    //监听指定开关
    form.on('switch(fgValidSwitch)', function (data) {
        if (this.checked == false) {
            layer.tips('温馨提示：删除后，该机构将不可用', data.othis)
        }
    });

    form.on('submit(addOrg)', function (data) {
        if (!data.field.fgActive) {
            data.field.fgActive = '0';
        }
        var url = basePath + '/pf/r/org/';
        if (formType == 'add') {
            url += 'add';
        } else if (formType == 'edit') {
            url += 'edit';
        }
        $.ajax({
            url: url,
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(data.field),
            success: function (data) {
                if (data.code != 0) {
                    common.errorMsg(data.msg);
                    return false;
                } else {
                    common.sucMsg("保存成功");
                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    parent.layer.close(index); //再执行关闭
                    //刷新父页面table
                    if (parent.layui) {
                        parent.layui.common.refreshCurrentPage();
                    }
                    return true;
                }
            },
            error: function () {
                common.errorMsg("保存失败");
                return false;
            }
        });
        return false;
    });

    form.on('submit(activeOrg)', function (data) {
        var bizData = {}
        bizData.idOrg = data.field.idOrg;
        var url = basePath + '/pf/r/org/active';
        return common.commonPost(url, bizData, "申请激活");
    });

    form.on('submit(renewOrg)', function (data) {
        var bizData = {}
        bizData.idOrg = data.field.idOrg;
        var url = basePath + '/pf/r/org/active';
        return common.commonPost(url, bizData, "申请续期");
    });

});

