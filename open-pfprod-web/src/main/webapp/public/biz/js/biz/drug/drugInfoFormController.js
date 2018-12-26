
layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['form', 'layer', 'jquery', 'common'], function () {
    var $ = layui.$,
        form = layui.form,
        common = layui.common;
    
    

    form.verify({
        commonLength: function (value) {
            if (value.length > 64) {
                return '长度不能超过64个字';
            }
        }
    });

    //监听提交
    form.on('submit(addDrugInfo)', function (data) {
        if (!data.field.fgActive) {
            data.field.fgActive = '0';
        }
        var url = basePath + '/pf/r/drug/info/';
        if (formType == 'add') {
            url += 'add';
        } else if (formType == 'edit') {
            url += 'edit';
        }
        return common.commonParentFormPost(url, data.field, formType, 'drugInfoTableId', '保存');
    });

    var setting = {
        view: {
            dblClickExpand: false,
            showLine: true
        },
        check: {
            enable: true,
            chkStyle: "radio",
            radioType: "all"
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onClick: onClick,
            onCheck: onCheck
        }
    };

    function onClick(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.checkNode(treeNode, !treeNode.checked, null, true);
        return false;
    };

    function onCheck(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
            nodes = zTree.getCheckedNodes(true),
            v = "",
            v_id = "";
        for (var i = 0, l = nodes.length; i < l; i++) {
            v += nodes[i].name + ",";
            v_id += nodes[i].id + ",";
        }
        if (v.length > 0) v = v.substring(0, v.length - 1);
        if (v_id.length > 0) v_id = v_id.substring(0, v_id.length - 1);
        $("#cdDrugsclassText").val(v);
        $("#cdDrugsclass").val(v_id);
        hideMenu();
    };

    function showMenu() {
        var cityObj = $("#cdDrugsclassText");
        var cityOffset = $("#cdDrugsclassText").offset();
        $("#menuContent").css({
            left: cityOffset.left + "px",
            top: cityOffset.top + cityObj.outerHeight() + "px"
        }).slideDown("fast");

        $("body").bind("mousedown", onBodyDown);
    };

    function hideMenu() {
        $("#menuContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown);
    };

    function onBodyDown(event) {
        if (!(event.target.id == "menuBtn" || event.target.id == "cdDrugsclassText" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
            hideMenu();
        }
    };

    var zNodes = [];
    $(document).ready(function () {
        layer.load(2);
        $.ajax({
            url: basePath + "/pf/r/drug/catalogue/tree",
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            success: function (data) {
                layer.closeAll('loading');
                zNodes = data.data;
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                return true;
            },
            error: function () {
                layer.closeAll('loading');
                return false;
            }
        });
    });

    $('#cdDrugsclassText').on('click', function () {
        showMenu();
        return false;
    });
    
});


function fullForm(data) {
    $(document).ready(function () {
        $("#drugInfoForm").autofill(data);
        layui.use('form',function(){
            layui.form.render();
        });
    });

}
