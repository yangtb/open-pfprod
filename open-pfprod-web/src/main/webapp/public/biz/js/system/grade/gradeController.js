layui.config({
    base: basePath + '/public/layui/build/js/'
}).use(['table', 'form', 'jquery', 'common', 'tableSelect'], function () {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , common = layui.common
        , tableSelect = layui.tableSelect;

    //执行渲染
    table.render({
        elem: '#gradeTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'gradeTableId'
        , height: 'full-68' //容器高度
        , cols: [[
            {type: 'radio', fixed: true},
            {field: 'fgActive', width: 100, title: '数据状态', fixed: true, templet: '#fgActiveTpl'},
            {field: 'name', title: '班级名称'},
            {fixed: 'right', title: '操作', width: 85, align: 'center', toolbar: '#gradeBar'}
        ]] //设置表头
        , url: basePath + '/pf/p/class/list'
        , limit: 15
        , page: {//支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
            layout: ['limit', 'prev', 'page', 'next', 'count'] //自定义分页布局
            //,curr: 5 //设定初始在第 5 页
            , groups: 1 //只显示 1 个连续页码
            //, first: false //不显示首页
            //, last: false //不显示尾页
            , limits: [15, 30, 100]
        }
    });

    //执行渲染
    table.render({
        elem: '#studentTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'studentTableId'
        , height: 'full-68' //容器高度
        , cols: [[
            {checkbox: true, fixed: true},
            //{field: 'id', width: 60, title: 'ID', fixed: true},
            {field: 'realName', width: 120, title: '学生姓名'},
            {field: 'className', width: 140, title: '班级名称'},
            {field: 'sex', width: 70, title: '性别', templet: '#sexTpl'},
            {field: 'phoneNo', width: 150, title: '电话'},
            {field: 'email', width: 200, title: '邮箱'},
            {fixed: 'right', width: 100, title: '操作', align: 'center', toolbar: '#studentBar'}
        ]] //设置表头
        , url: basePath + '/pf/p/class/student/list'
        , limit: 15
        , even: true
        , limits: [15, 30, 100]
        , page: true
    });

    //监听提交
    form.on('submit(gradeSearchFilter)', function (data) {
        table.reload('gradeTableId', {
            where: {
                className: data.field.gradeName
            },
            height: 'full-68'
        });
    });

    //监听提交
    form.on('submit(studentSearchFilter)', function (data) {
        table.reload('studentTableId', {
            where: {
                studentName: data.field.studentName
            },
            height: 'full-68'
        });
    });

    //监听行双击事件
    table.on('rowDouble(gradeTableFilter)', function (obj) {
        _addOrEditGrade("edit", obj.data);
    });


    $('#addGrade').on('click', function () {
        _addOrEditGrade("add");
    });

    $('#editGrade').on('click', function () {
        var currentData = _getGradeCheckData();
        if (currentData.length == 0) {
            layer.tips('请先选中一行记录', '#editGrade', {tips: 1});
            return;
        }
        if (currentData.length > 1) {
            layer.tips('请选中一行记录进行操作', '#editGrade', {tips: 1});
            return;
        }
        _addOrEditGrade("edit", currentData[0]);

    });

    var _addOrEditGrade = function (formType, currentEditData) {
        if (formType == 'add') {
            common.open('新增班级', basePath + '/pf/p/class/form?formType=' + formType, 400, 210);
        } else {
            common.open('编辑班级', basePath + '/pf/p/class/form?formType=' + formType, 400, 210, _successGradeFunction(currentEditData));
        }
    };

    var _successGradeFunction = function (data) {
        return function (layero, index) {
            var iframe = window['layui-layer-iframe' + index];
            //调用子页面的全局函数
            iframe.fullForm(data);
        }
    };

    //单击行选中radio
    table.on('row(gradeTableFilter)', function (obj) {
        obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');//选中行样式
        obj.tr.find('input[lay-type="layTableRadio"]').prop("checked", true);
        form.render('radio');
        rowClick(obj)
    });

    var currentGradeRowData = [];

    function rowClick(obj) {
        currentGradeRowData.push(obj.data);
        table.reload('studentTableId', {
            url: basePath + '/pf/p/class/student/list'
            , where: {
                idClass: obj.data.idClass
            }
            , height: 'full-68' //容器高度
            , page: {
                curr: 1 //重新从第 1 页开始
            }
        });

        tableSelect.render({
            elem: '#addStudent',
            searchKey: 'conditionValue',
            checkedKey: 'addStudentId',
            searchPlaceholder: '请输入关键字',
            table: {
                url: basePath + '/pf/p/class/student/search/list',
                cols: [[
                    {type: 'checkbox'},
                    {field: 'real_name', width: 100, title: '姓名'},
                    {field: 'sex', width: 70, templet: '#sexTpl', title: '性别'},
                    {field: 'phone_no', width: 120, title: '手机号'},
                    {field: 'email', width: 160, title: '邮箱'},
                    {field: 'enabled', width: 70, title: '状态', templet: '#enabledTpl'},
                ]]
                , limits: [10, 20, 50]
                , page: true
            },
            done: function (elem, data) {
                _addStudent(data.data);
            }
        });
    }

    // 获取编辑行数据
    var _getGradeCheckData = function () {
        return currentGradeRowData;
    }

    $('#addStudent').on('click', function () {
        var checkStatus = table.checkStatus('gradeTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            layer.tips('请先在左侧列表选择班级', '#addStudent', {tips: 1});
            return;
        }
    });

    function _addStudent(data) {
        // 学生列表刷新
        var gradeData = currentGradeRowData;
        var idClass = gradeData.idClass;

        if (data.length == 0) {
            return;
        }
        // 班级中添加学生
        var studentData = {},
            studentList = [];
        for (var i = 0; i < data.length; i++) {
            studentList.push(data[i].user_id);
        }
        studentData.list = studentList;
        studentData.extId = idClass;
        common.commonPost(basePath + '/pf/r/class/student/add', studentData, '添加', '', _studentCallback);
    };

    function _studentCallback() {
        var gradeData = currentGradeRowData;
        var idClass = gradeData.idClass;

        table.reload('studentTableId', {
            height: 'full-68'
            , where: {
                idClass: idClass
            }
        });
    };

    //监听工具条
    table.on('tool(gradeTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            var currentData = [];
            currentData.push(data);
            _delGrade(currentData);
        }
    });

    var _delGrade = function (currentData) {
        var url = basePath + '/pf/r/class/del';
        var reqData = new Array();
        var name = '';
        $.each(currentData, function (index, content) {
            if (name) {
                name += ', ';
            }
            name += '【' + content.name + '】';
            reqData.push(content.idClass);
        });
        var data = {};
        data.list = reqData;
        layer.confirm('真的要删除班级' + name + '么？', {
            title: '删除班级提示',
            resize: false,
            btn: ['确定', '取消'],
            btnAlign: 'c',
            icon: 3
        }, function (index) {
            _commonAjax(index, url, data, "删除", 'grade');
        })
    }

    //监听工具条
    table.on('tool(studentTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            var currentData = [];
            currentData.push(data);
            _delStudent(currentData);
        }
    });

    $("#delStudent").on('click', function () {
        var checkStatus = table.checkStatus('studentTableId')
            , data = checkStatus.data;
        if (data.length == 0) {
            layer.tips('请先选中一行记录', '#delStudent', {tips: 1});
            return;
        }
        _delStudent(data);
    });

    var _delStudent = function (currentData) {
        var url = basePath + '/pf/r/class/student/del';
        var reqData = new Array();
        var name = '';
        $.each(currentData, function (index, content) {
            if (name) {
                name += ', ';
            }
            name += '【' + content.realName + '】';
            reqData.push(content.idMemo);
        });
        var data = {};
        data.list = reqData;
        layer.confirm('真的要删除学生' + name + '么？', {
            title: '删除学生提示',
            resize: false,
            btn: ['确定', '取消'],
            btnAlign: 'c',
            icon: 3
        }, function (index) {
            _commonAjax(index, url, data, "删除", 'student');
        })
    }

    var _commonAjax = function (index, url, reqData, msg, type) {
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
                    if (type == 'grade') {
                        _gradeTableReload();
                    } else if (type == 'student') {
                        _studentTableReload();
                    }
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

    var _gradeTableReload = function () {
        table.reload('gradeTableIdId', {
            height: 'full-68'
        });
    };

    var _studentTableReload = function () {
        table.reload('studentTableId', {
            height: 'full-68'
        });
    };

    //监听删除操作
    form.on('switch(fgActiveCheckFilter)', function (obj) {
        var reqData = new Array();
        var data = {}, msg;
        reqData.push(this.value);
        data.list = reqData;
        if (obj.elem.checked) {
            data.status = '1';
            msg = '启用';
        } else {
            data.status = '0';
            msg = '停用';
        }
        common.commonPost(basePath + '/pf/r/class/updateStatus', data, msg, obj.othis);
    });

});

