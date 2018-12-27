layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['element', 'table', 'form', 'jquery', 'common'], function () {
    var $ = layui.$
        , element = layui.element
        , table = layui.table
        , form = layui.form
        , common = layui.common;

    //执行渲染
    table.render({
        elem: '#tagTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'tagTableId'
        , height: 'full-20' //容器高度
        , cols: [[
            {field: 'path', width: 60, title: 'logo', align: 'center', templet: '#imgTpl'},
            {field: 'name', width: 100, title: '标签名称'},
            {field: 'sdProcess', width: 100, title: '流程类型', align: 'center', templet: '#sdProcessTpl'},
            {field: 'processSerialno', title: '序号', minWidth: 50, templet: '#processSerialnoTpl'},
        ]] //设置表头
        , url: basePath + '/pf/p/clinic/template/tag/caseHistory/list'
        , where: {
            idDemo: idDemo,
            fgShowExec: '1'
        }
        , limit: 500
        , page: false
        , done: function (res, curr, count) {
            //如果是异步请求数据方式，res即为你接口返回的信息。
            //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
            loadPanel(res.data);
        }
    });


    function loadPanel(data) {
        if (data.length == 0) {
            return;
        }
        var erialData = [], parallelData = [];
        $.each(data, function (i, item) {
            if (item.sdProcess == '2') {
                var bizData = {
                    logo: item.path,
                    title: item.name,
                    serialno: item.processSerialno ? item.processSerialno : item.sort
                };
                erialData.push(bizData);
            } else {
                var bizData = {
                    logo: item.path,
                    title: item.name
                };
                parallelData.push(bizData);
            }
        });

        loadSerial(erialData);
        loadParallel(parallelData);
    }

    function loadSerial(data) {
        if (data.length == 0) {
            $("#step").empty();
            return;
        }

        var titleArr = [];

        data = data.sort(function (a, b) {
            return a.serialno - b.serialno;
        });

        $.each(data, function (i, item) {
            titleArr.push('<img style="display: inline-block; width: 30px; height: 30px;" src="' + item.logo + '"><br>' + item.title);
        });

        $("#step").empty();
        var step = $("#step");
        var index = $("#index");

        step.step({
            index: data.length - 1,
            time: 1000,
            title: titleArr
        });

        index.text(step.getIndex());
    }

    function loadParallel(data) {
        if (data.length == 0) {
            $("#parallelTag").empty();
            return;
        }
        var resultHtml = '';
        $.each(data, function (i, item) {
            resultHtml += appendParallelHtml(item);
        });

        $("#parallelTag").empty();
        $("#parallelTag").append(resultHtml)
    }

    function appendParallelHtml(data) {
        return '<li>\n' +
            '          <a href="#"><img src="' + data.logo + '"></a>\n' +
            '          <a href="#" class="wenzi">' + data.title + '</a>\n' +
            '       </li>';
    }

    form.on('switch(sdProcessFilter)', function (obj) {
        var idTag = this.value;
        var sdProcess, processSerialno;
        if (obj.elem.checked) {
            sdProcess = '1';
            processSerialno = null;
            $('#processSerialno-' + idTag).val("");
            $('#processSerialno-' + idTag).hide();
            //$('#processSerialno-' + idTag).addClass("layui-btn-disabled");
            //$('#processSerialno-' + idTag).attr("disabled", "disabled");
        } else {
            sdProcess = '2';
            processSerialno = $('#processSerialno-' + idTag).val();
            $('#processSerialno-' + idTag).show();
            $('#processSerialno-' + idTag).removeClass("layui-btn-disabled");
            $('#processSerialno-' + idTag).removeAttr("disabled");
        }
        var bizData = {
            idTag: idTag,
            sdProcess: sdProcess,
            processSerialno: processSerialno
        };
        $.ajax({
            url: basePath + '/pf/r/clinic/serialno/save',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(bizData),
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    layer.tips(data.msg, obj.othis, {tips: [1, '#FF5722']});
                    return false;
                } else {
                    //layer.tips('保存成功', obj.othis, {tips: 1});
                    saveCallBack(obj, bizData);
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                return false;
            }
        });

        //common.commonPost(basePath + '/pf/r/clinic/serialno/save', bizData, null, obj.othis, saveCallBack);
    });

    function saveCallBack(obj, data) {
        table.reload('tagTableId', {
            done: function (res, curr, count) {
                loadPanel(res.data);
                if (data.sdProcess == '2') {
                    layer.tips('请输入序号', '#processSerialno-' + data.idTag, {tips: 1});
                    $('#processSerialno-' + data.idTag).focus();
                }
            }
        });

    }

});


function saveSerialNo(ele, idTag) {
    var processSerialno = $('#processSerialno-' + idTag).val();
    if (!processSerialno.match(/^[1-9]\d*$/)) {
        layer.tips('序号必须是整数', '#processSerialno-' + idTag, {tips: 1});
        return;
    }
    var bizData = {
        idTag: idTag,
        sdProcess: '2',
        processSerialno: processSerialno
    }
    $.ajax({
        url: basePath + '/pf/r/clinic/serialno/save',
        type: 'post',
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(bizData),
        success: function (data) {
            layer.closeAll('loading');
            if (data.code != 0) {
                layer.tips(data.msg, '#processSerialno-' + idTag, {tips: [1, '#FF5722']});
                return false;
            } else {
                //layer.tips('保存成功', '#processSerialno-' + idTag, {tips: 1});
                callBack();
                return true;
            }
        },
        error: function () {
            layer.closeAll('loading');
            return false;
        }
    });
}

function callBack() {
    layui.use('table', function () {
        layui.table.reload('tagTableId', {
            done: function (res, curr, count) {
                loadPanel(res.data);
                if (data.sdProcess == '2') {
                    layer.tips('请输入序号', '#processSerialno-' + data.idTag, {tips: 1});
                    $('#processSerialno-' + data.idTag).focus();
                }
            }
        });
    });
}

function loadPanel(data) {
    if (data.length == 0) {
        return;
    }
    var erialData = [], parallelData = [];
    $.each(data, function (i, item) {
        if (item.sdProcess == '2') {
            var bizData = {
                logo: item.path,
                title: item.name,
                serialno: item.processSerialno ? item.processSerialno : item.sort
            };
            erialData.push(bizData);
        } else {
            var bizData = {
                logo: item.path,
                title: item.name
            };
            parallelData.push(bizData);
        }
    });

    loadSerial(erialData);
    loadParallel(parallelData);
}

function loadSerial(data) {
    if (data.length == 0) {
        return;
    }

    var titleArr = [];

    data = data.sort(function (a, b) {
        return a.serialno - b.serialno;
    });

    $.each(data, function (i, item) {
        titleArr.push('<img style="display: inline-block; width: 30px; height: 30px;" src="' + item.logo + '"><br>' + item.title);
    });

    $("#step").empty();
    var step = $("#step");
    var index = $("#index");

    step.step({
        index: data.length - 1,
        time: 1000,
        title: titleArr
    });

    index.text(step.getIndex());
}

function loadParallel(data) {
    if (data.length == 0) {
        return;
    }
    var resultHtml = '';
    $.each(data, function (i, item) {
        resultHtml += appendParallelHtml(item);
    });

    $("#parallelTag").empty();
    $("#parallelTag").append(resultHtml)
}

function appendParallelHtml(data) {
    return '<li>\n' +
        '          <a href="#"><img src="' + data.logo + '"></a>\n' +
        '          <a href="#" class="wenzi">' + data.title + '</a>\n' +
        '       </li>';
}

