layui.config({
    base: basePath + '/public/layui/build/js/'
}).extend({
    formSelects: 'formSelects-v4'
}).use(['table', 'form', 'upload', 'jquery', 'element', 'tableSelect', 'common', 'formSelects'], function () {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , upload = layui.upload
        , common = layui.common
        , element = layui.element
        , tableSelect = layui.tableSelect
        , formSelects = layui.formSelects;

    formSelects.config('select15', {
        success: function (id, url, val, result) {
            console.log("success回调: " + url);
        },
        error: function (id, url, val, err) {
            console.log("err回调: " + url);
        }
    });


    var selectArr = [];
    $.ajax({
        url: basePath + '/pf/r/inquisition/question/classify/tree',
        type: 'post',
        dataType: 'json',
        contentType: "application/json",
        success: function (data) {
            if (data.code != 0) {
                common.errorMsg(data.msg);
                return false;
            } else {
                var selectData = data.data;
                selectArr = toTree(selectData);
                formSelects.data('select15', 'local', {
                    arr: selectArr
                    , linkage: true
                });
                return true;
            }
        },
        error: function () {
            return false;
        }
    });

    formSelects.on('select15', function(id, vals, val, isAdd, isDisabled) {
        //id:           点击select的id
        //vals:         当前select已选中的值
        //val:          当前select点击的值
        //isAdd:        当前操作选中or取消
        //isDisabled:   当前选项是否是disabled
        //alert("选择了: " + val.value);
        //如果return false, 那么将取消本次操作
        if (val.value == 1) {
            //return false;
        }
    });

    formSelects.filter('select15', function (id, inputVal, val, isDisabled) {
        if (
            //PY.fullPY(val.name).toLowerCase().indexOf(inputVal) != -1 ||    //拼音全拼是否包含
            PY.fullPY(val.name, true).indexOf(inputVal) != -1 ||            //拼音简拼是否包含
            val.name.indexOf(inputVal) != -1                                //文本是否包含
        ) {
            return false;
        }
        return true;
    });

    function toTree(data) {
        // 删除 所有 children,以防止多次调用
        data.forEach(function (item) {
            delete item.children;
        });

        // 将数据存储为 以 id 为 KEY 的 map 索引数据列
        var map = {};
        data.forEach(function (item) {
            item.value = item.id;
            map[item.id] = item;

        });
        console.log(map);
        var val = [];
        data.forEach(function (item) {
            // 以当前遍历项，的pid,去map对象中找到索引的id
            var parent = map[item.pId];
            // 好绕啊，如果找到索引，那么说明此项不在顶级当中,那么需要把此项添加到，他对应的父级中
            if (parent) {
                (parent.children || ( parent.children = [] )).push(item);
            } else {
                //如果没有在map中找到对应的索引ID,那么直接把 当前的item添加到 val结果集中，作为顶级
                val.push(item);
            }
        });
        return val;
    }



    //执行渲染
    table.render({
        elem: '#partConsTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'partConsTableId'
        , height: '330' //容器高度
        , cols: [[
            {type: 'radio'},
            {field: 'desInques', width: 80, title: '评估阶段'},
            {field: 'desAnswer', minWidth: 100, title: '评估项'},
            {field: 'desAnswer1', width: 60, title: '分值'},
            {fixed: 'right', title: '操作', width: 60, align: 'left', toolbar: '#partConsBar'}
        ]] //设置表头
        , url: basePath + '/pf/p/kb/part/cons/list'
        , where: {
            idMedCase: 1
        }
        , page: false
    });

    form.verify({
        desAnswer: function (value) {
            if (value.length > 255) {
                return '长度不能超过255个字';
            }
        }
    });


});

