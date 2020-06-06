layui.config({
    base: basePath + '/public/layui/plugins/'
}).extend({
    index: 'lib/index' //主入口模块
}).use(['table', 'form', 'jquery', 'index', 'common', 'view'], function () {
    let $ = layui.$,
        table = layui.table,
        form = layui.form,
        common = layui.common
        , view = layui.view;

    $(document).ready(function () {
        var bodyHeight = $(this).height() - 60;
        $("#treeDemo").css("min-height", bodyHeight);
        $("#treeDemo").css("max-height", bodyHeight);
    });

    //********************zTree***********************
    var setting = {
        data: {
            simpleData: {
                enable: true
            }
        },
        view: {
            dblClickExpand: false
        },
        callback: {
            onClick: zTreeOnClick
        }
    };

    var zTree;
    $(document).ready(function () {
        layer.load(2);
        $.ajax({
            url: basePath + '/pf/r/case/history/classify/tree',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    common.errorMsg(data.msg);
                    return false;
                } else {
                    let zNodes = data.data;
                    $.each(zNodes, function (i, val) {
                        if (val.noR == false) {
                            val.font = {'color': '#009688', 'font-weight':'bold'};
                        }
                    });
                    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                    zTree = $.fn.zTree.getZTreeObj("treeDemo");
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                return false;
            }
        });

    });

    function zTreeOnClick(event, treeId, treeNode) {
        $("#patInfo").empty();
        let v_idMedicalrecCa = treeNode.id;
        loadPatInfo(v_idMedicalrecCa);
    };

    function loadPatInfo(v_idMedicalrecCa) {
        console.log(v_idMedicalrecCa)
        layer.load(2);

        $.ajax({
            url: basePath + '/pf/p/waiting/room/student/page?idMedicalrecCa=' + v_idMedicalrecCa,
            type: 'get',
            dataType: 'json',
            contentType: "application/json",
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    layer.msg(data.msg);
                    return false;
                } else {
                    appendPatHtml(data);
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                layer.msg("网络异常");
                return false;
            }
        });
    }

    function appendPatHtml(data) {
        console.log(data)
        if (!data || !data.data || data.data.length == 0) {
            return;
        }
        let patHtml = '';
        $.each(data.data, function (i, item) {
            patHtml += '<div class="layui-col-md4">\n' +
                '                        <div class="layui-card">\n' +
                '                            <div class="layui-card-body ';
            if (item.status == 0) {
                patHtml +='layui-bg-green">';
            } else if(item.status == 1 ) {
                patHtml += 'bg-diagnosing">';
            }

            patHtml +=
                '                                <div class="layout-flex-self">\n' +
                '                                    <div>\n' +
                '                                        <img src="https://jiayiyixue.oss-cn-beijing.aliyuncs.com/582BD4D0DFBB4236BBF296D2A0ED46E7.jpg">\n' +
                '                                    </div>\n' +
                '\n' +
                '                                    <div class="d-flex flex-column">\n' +
                '                                        <div>\n' +
                '                                            <span>姓名：</span>\n' +
                '                                            <span>' + item.patName + '</span>\n' +
                '                                        </div>\n' +
                '                                        <div>\n' +
                '                                            <span>性别：</span>\n';

            if (item.patSex == 2) {
                patHtml +='<span>女</span>';
            } else if(item.patSex == 1 ) {
                patHtml += '<span>男</span>';
            }

            patHtml +=
                '                                        </div>\n' +
                '                                        <div>\n' +
                '                                            <span>年龄：</span>\n' +
                '                                            <span>' + item.patAge + '</span>\n' +
                '                                        </div>\n' +
                '                                        <div style="height: 70px">\n' +
                '                                            <span>患者主诉：</span>\n' +
                '                                            <span>' + item.complaint + '</span>\n' +
                '                                        </div>\n' +
                '                                    </div>\n' ;
            if (item.status == 0) {
                patHtml +='<span class="status-float">待接诊</span>';
            } else if(item.status == 1 ) {
                patHtml += '<span class="status-float color-diagnosing-status">正在接诊</span>';
            }

            patHtml +=
                '\n' +
                '                                    <input class="' + item.idTestplanDetail + '" value="' + item.idDemo + '" hidden>\n' +
                '                                    <input class="' + item.idTestplanDetail + '" value="' + item.idTestplan + '" hidden>\n' +
                '                                    <input class="' + item.idTestplanDetail + '" value="' + item.idStudent + '" hidden>\n' +
                '                                    <input class="' + item.idTestplanDetail + '" value="' + item.idTestpaper + '" hidden>\n' +
                '                                    <input class="' + item.idTestplanDetail + '" value="' + item.idMedicalrec + '" hidden>\n' +
                '                                    <input class="' + item.idTestplanDetail + '" value="' + item.patName + '" hidden>\n' +
                '                                    <input class="' + item.idTestplanDetail + '" value="' + item.patSex + '" hidden>\n' +
                '                                    <input class="' + item.idTestplanDetail + '" value="' + item.patAge + '" hidden>\n' +
                '                                    <input class="' + item.idTestplanDetail + '" value="' + item.complaint + '" hidden>\n' +
                '                                    <input class="' + item.idTestplanDetail + '" value="' + item.idMedCase + '" hidden>\n' +
                '\n' +
                '                                </div>\n' +
                '                            </div>\n' +
                '                            <div class="layui-card-header">\n' +
                '                                <button class="layui-btn layui-btn-primary layui-btn-fluid receivePat" style="border:none" value="' + item.idTestplanDetail + '">\n' ;
            if (item.status == 0) {
                patHtml +='待接诊';
            } else if(item.status == 1 ) {
                patHtml += '继续接诊';
            }

            patHtml +=
                '                                </button>\n' +
                '                            </div>\n' +
                '                        </div>\n' +
                '                    </div>';
        });

        $('#patInfo').append(patHtml);
    }

    $('.receivePat').on("click", function (e) {
        let id = e.target.value;
        let data = $("." + id).map(function(){
            return this.value;
        }).toArray();

        setAttr(data, id)
    });

    function setAttr(data, id) {
        let $test = $('#test');
        $test.attr('lay-href',
            basePath + '/pf/p/waiting/room/exam/page?idTestplanDetail=' + id
            + '&idDemo=' + data[0] + '&idTestplan=' + data[1] + '&idStudent=' + data[2]
            + '&idTestpaper=' + data[3] + '&idMedicalrec=' + data[4] + ' + &patName=' + data[5]
            + ' + &patSex=' + data[6]+ ' + &patAge=' + data[7]+ ' + &complain=' + data[8]+ ' + &idMedCase=' + data[9]);
        $('#patName').text(data[5] + '患者');
        $test.click();
        parent.spreadMenu();
    }


});


