layui.config({
    base: basePath + '/public/layui/build/js/'
}).extend({
    formSelects: 'formSelects-v4'
}).use(['form', 'jquery', 'common', 'formSelects'], function () {
    var $ = layui.$
        , form = layui.form
        , common = layui.common
        , formSelects = layui.formSelects;

    if (!sdEva) {
        alert("评估阶段不能为空")
    }
    var selectArr = [],
        url = basePath;
    if (sdEva == '1') {
        url += '/pf/r/inquisition/question/classify/tree';
    } else if (sdEva == '2') {
        url += '/pf/r/check/question/classify/tree';
    } else if (sdEva == '3') {
        url += '/pf/r/exam/question/classify/tree';
    }
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        contentType: "application/json",
        success: function (data) {
            var selectData = data.data;
            selectArr = toTree(selectData);
            formSelects.data('dicSelect', 'local', {
                arr: selectArr
                , linkage: true
            });
            return true;
        },
        error: function () {
            return false;
        }
    });

    formSelects.filter('dicSelect', function (id, inputVal, val, isDisabled) {
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
            item.expand = false;
            map[item.id] = item;

        });
        var val = [];
        data.forEach(function (item) {
            // 以当前遍历项，的pid,去map对象中找到索引的id
            var parent = map[item.pId];
            // 好绕啊，如果找到索引，那么说明此项不在顶级当中,那么需要把此项添加到，他对应的父级中
            if (parent) {
                (parent.children || (parent.children = [])).push(item);
            } else {
                //如果没有在map中找到对应的索引ID,那么直接把 当前的item添加到 val结果集中，作为顶级
                val.push(item);
            }
        });
        return val;
    };

    //监听提交
    form.on('submit(addAnswerFilter)', function (data) {
        var dataSelect = formSelects.value('dicSelect', 'all');

        var answerDataArr = [];
        for (var i = 0; i < dataSelect.length; i++) {
            var idDieArr = dataSelect[i].value.split('/');
            var idDieTextArr = dataSelect[i].name.split('/');
            var answerData = {
                idEvaCaseItem: idEvaCaseItem,
                sdEvaReferral: sdEva,
                idDie: idDieArr[idDieArr.length - 1],
                idDieText: idDieTextArr[idDieArr.length - 1]
            }
            answerDataArr.push(answerData);
        }

        console.log(JSON.stringify(answerDataArr));
        parent.addAnswer(answerDataArr);
        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        parent.layer.close(index);
        common.sucMsg('已添加');
        return false;
    });


    $('#close').on('click', function () {
        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        parent.layer.close(index);
    });


});

