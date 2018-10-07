/**
 * 公告
 * @constructor
 */
layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['table', 'form', 'jquery', 'laydate', 'common'], function () {
    var $ = layui.$,
        table = layui.table,
        form = layui.form,
        laydate = layui.laydate,
        common = layui.common;

    //日期范围
    laydate.render({
        elem: '#publishTime'
        //,theme: '#393D49'
        , theme: 'molv'
        , range: true
        , calendar: true
        , max: 0
    });

    //执行渲染
    table.render({
        elem: '#noticeTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'noticeTableId'
        , height: 'full-68' //容器高度
        , cols: [[
            {checkbox: true, fixed: true},
            //{field: 'id', width: 60, title: 'ID', fixed: true},
            {field: 'noticeTitle', width: 470, title: '公告标题'},
            {field: 'noticeType', width: 100, title: '公告类型', templet: '#noticeTypeTpl'},
            {field: 'operator', width: 100, title: '发布人'},
            {field: 'gmtCreate', width: 170, sort: true, title: '发布时间'},
            {fixed: 'right', width: 160, title: '操作', align: 'center', toolbar: '#noticeBar'}
        ]] //设置表头
        , url: basePath + '/pf/p/notice/list'
        , limit: 15
        , even: true
        , limits: [15, 30, 100]
        , page: true
    });

    //监听工具条
    table.on('tool(noticeTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'edit') {
            _addOrEdit("edit", data);
        } else if (obj.event === 'detail') {
            var index = common.open('公告详情', 'detail', 880, 430, _successFunction(data));
            layer.full(index);
        }
    });

    //监听提交
    form.on('submit(noticeSearchFilter)', function (data) {
        var publishTime = data.field.publishTime;
        table.reload('noticeTableId', {
            where: {
                publishTimeBegin: publishTime.substr(0, 10),
                publishTimeEnd: publishTime.substr(13, publishTime.length)
            },
            height: 'full-68'
        });
    });

    $('.add').on('click', function () {
        _addOrEdit("add");
    });

    $('.edit').on('click', function () {
        var checkStatus = table.checkStatus('noticeTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            common.toastTop("请先选中一行记录");
            return;
        }
        if (data.length > 1) {
            common.toastTop("请选中一行记录进行编辑");
            return;
        }
        var currentEditData = data[0];
        _addOrEdit("edit", currentEditData);
    });

    var _addOrEdit = function (formType, currentEditData) {
        if (formType == 'add') {
            var addIndex = common.open('新增公告', 'form?formType=' + formType, 880, 430);
            layer.full(addIndex);
        } else {
            var editIndex = common.open('编辑公告', 'form?formType=' + formType, 880, 430, _successFunction(currentEditData));
            layer.full(editIndex);
        }
    };

    var _successFunction = function (data) {
        return function (layero, index) {
            var iframe = window['layui-layer-iframe' + index];
            //调用子页面的全局函数
            iframe.fullForm(data);
        }
    };

    $(".del").on('click', function () {
        var checkStatus = table.checkStatus('noticeTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            common.toastTop("请先选中一行记录");
            return;
        }
        _delNotice(data);
    });

    var _delNotice = function (currentData) {
        var url = basePath + '/pf/r/notice/del';
        var reqData = new Array();
        var noticeTitle = '';
        $.each(currentData, function (index, content) {
            if (noticeTitle) {
                noticeTitle += ', ';
            }
            noticeTitle += '【' + content.noticeTitle + '】';
            reqData.push(content.id);
        });
        var data = {};
        data.list = reqData;
        layer.confirm('真的要删除公告' + noticeTitle + '么？', {
            title: '删除公告提示',
            resize: false,
            btn: ['确定', '取消'],
            btnAlign: 'c',
            icon: 3
        }, function (index) {
            _commonAjax(index, url, data, "删除");
        })
    }

    var _commonAjax = function (index, url, reqData, msg) {
        layer.load(2);
        $.ajax({
            url: url,
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(reqData),
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    common.errorMsg(data.msg);
                    return false;
                } else {
                    common.sucMsg(msg + "成功");
                    if (index) {
                        layer.close(index);
                    }
                    _noticeTableReload();
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                common.errorMsg(msg + "失败");
                return false;
            }
        });
    }

    var _noticeTableReload = function () {
        table.reload('noticeTableId', {
            where: {
                //type: type
            },
            height: 'full-68'
        });
    }

});

