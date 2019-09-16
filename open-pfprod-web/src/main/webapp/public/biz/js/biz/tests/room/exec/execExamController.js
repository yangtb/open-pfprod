layui.config({
    base: basePath + '/public/layui/build/js/'
}).extend({
    ckplayer: 'ckplayer/ckplayer'
}).use(['table', 'jquery', 'form', 'common', 'tableSelect'], function () {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , common = layui.common
        , tableSelect = layui.tableSelect;

    $(document).ready(function () {
        var bodyHeight = $(this).height() - 93;
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
            url: basePath + '/pf/r/exam/question/classify/tree',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    common.errorMsg(data.msg);
                    return false;
                } else {
                    var zNodes = data.data;
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
        _tableReload(treeNode.id, null);
    };

    function _tableReload(extItemId, keyword) {
        table.reload('execExamTableId', {
            where: {
                idMedicalrec: idMedicalrec,
                cdMedAsse: cdMedAsse,
                idTestexecResult: idTestexecResult,
                extItemId: extItemId,
                keyword: keyword
            }
            , height: '604'
        });
    }

    $('#queryBtn').on('click', function () {
        _tableReload(null, $('#keyword').val())
    });

    $('#keyword').bind('keypress', function (event) {
        if (event.keyCode == "13") {
            _tableReload(null, $('#keyword').val())
            return false;
        }
    });

    //执行渲染
    table.render({
        elem: '#execExamTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'execExamTableId'
        , height: '604' //容器高度
        , cols: [[
            {type: 'numbers', title: 'R'},
            {field: 'naItem', minWidth: 150, title: '检验项目'},
            {field: 'idDieText', width: 110, title: '拟诊', toolbar: '#nzTpl'},
            {field: 'qa', width: 60, title: '提问', fixed: 'right', align: 'center', templet: '#qaExamTpl'}
        ]] //设置表头
        , url: basePath + '/pf/p/waiting/room/test/exam/list'
        , where: {
            idMedicalrec: idMedicalrec,
            cdMedAsse: cdMedAsse,
            idTestexecResult: idTestexecResult
        }
        , limit: 20
        , page: {//支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
            layout: ['limit', 'count', 'prev', 'page', 'next'] //自定义分页布局
            , groups: 1 //只显示 1 个连续页码
            , first: false //不显示首页
            , last: false //不显示尾页
            , limits: [20, 30, 50]
        }
        , done: function (res, curr, count) {
            $.each(res.data, function (i, item) {
                tableSelectRender(item.idMedCaseList);
            });
        }
    });

    function tableSelectRender(id) {
        tableSelect.render({
            elem: '#nz' + id,
            checkedKey: 'idDie',
            searchKey: 'keywords',
            table: {
                url: basePath + '/pf/p/waiting/room/all/referral/die'
                , cols: [[
                    {type: 'radio', fixed: true},
                    {field: 'name', minWidth: 160, title: '疾病名称'},
                    {field: 'cdDieclassText', minWidth: 120, title: '疾病目录'},
                    {field: 'icd', width: 80, title: 'ICD'}
                ]] //设置表头
                , where: {
                    idTestexecResult: idTestexecResult
                }
                , limit: 100
                , page: false
            },
            done: function (elem, data) {
                var selectData = data.data[0];
                $('#nz' + id).attr('data-index', selectData.idDie)
                $('#nz' + id).text(selectData.name);
                // if check
                if ($('#nz' + id).attr('data-qa-check') == 'true') {
                    var bizData = {
                        idTestexecResult: idTestexecResult,
                        idMedCaseList: id,
                        idDie : selectData.idDie
                    }
                    var url = basePath + '/pf/r/waiting/room/exam/qa/edit';
                    common.commonPost(url, bizData, null, null, null, false);
                }
            }
        });
    };


    //监听行双击事件
    table.on('rowDouble(execExamTableFilter)', function (obj) {
        let checkInput = $('#exam' + obj.data.idMedCaseList);

        if (!checkInput[0].checked) {
            var data = {
                idTestexecResult: idTestexecResult,
                idInspectItem: obj.data.idInspectItem,
                idMedCaseList: obj.data.idMedCaseList,
                fgValid: '0',
                idDie: $('#nz' + obj.data.idMedCaseList).attr('data-index')
            };
            var url = basePath + '/pf/r/waiting/room/exam/qa/save';
            common.commonPost(url, data, null, null, queryQa, false);
            checkInput.attr("disabled", true);

            let checkCell = obj.tr.find(".layui-form-checkbox");
            if (!obj.tr.hasClass('layui-table-click')) {
                obj.tr.addClass('layui-table-click');
                checkCell.addClass('layui-form-checked');
                checkCell.addClass('layui-checkbox-disbaled');
            }
        }
    });

    form.on('checkbox(qaExamFilter)', function (obj) {
        var qaValue = this.value;
        var qaArr = qaValue.split("-");
        var data = {
            idTestexecResult: idTestexecResult,
            idMedCaseList: qaValue,
            idInspectItem: qaArr[0],
            idMedCaseList: qaArr[1],
            fgValid: obj.elem.checked ? '0' : '1',
            idDie: $('#nz' + qaArr[1]).attr('data-index')
        }
        $('#nz' + qaArr[1]).attr('data-qa-check', 'true');
        var url = basePath + '/pf/r/waiting/room/exam/qa/save';
        common.commonPost(url, data, null, null, queryQa, false);
        if (obj.elem.checked) {
            $('#exam' + qaArr[1]).attr("disabled", true);
        }
    });

    // 页面加载完成查询问答
    $(document).ready(function () {
        queryQa();
    });

    $('#refreshRight').on('click', function () {
        layer.load(2);
        queryQa();
        return false;
    });

    // 显示专家解读
    var showExpertFlag = executingShowExpert == 'Y' ? true : false;
    if (showExpertFlag == false) {
        showExpertFlag = sdTestexec == '2' && completedShowExpert == 'Y' ? true : false;
    }

    function queryQa() {
        var bizData = {
            idTestexecResult: idTestexecResult
        };
        $.ajax({
            url: basePath + '/pf/r/waiting/room/exam/qa/list',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(bizData),
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    common.errorMsg(data.msg);
                    return false;
                } else {
                    qaHtml(data.data, showExpertFlag);
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                common.errorMsg("查询问答失败");
                return false;
            }
        });
    }

    function qaHtml(data, showExpertFlag) {
        var result = '<li style="padding-top: 1px;">\n' +
            ' <div class=\'chat\' >\n' +
            '  <div style=\'overflow: hidden\'>\n' +
            '   <div style=\'float: left \'>\n' +
            '    <img src=' + basePath + '/public/layui/build/images/p3.png alt="">\n' +
            '   </div>\n' +
            '   <div style=\'float: left;  width:70%\'>\n' +
            '    <span>&nbsp  开始接诊</span>\n' +
            '   </div>\n' +
            '  </div>\n' +
            '\n' +
            ' </div >\n' +
            ' </div >\n' +
            '</li >'+
            '<li>\n' +
            ' <div class=\'chat\' >\n' +
            '  <div style=\'overflow: hidden\'>\n' +
            '   <div style=\'float: left \'>\n' +
            '    <img src=' + basePath + '/public/layui/build/images/p2.png alt="">\n' +
            '   </div>\n' +
            '   <div style=\'float: left;  width:70%\'>\n' +
            '    <span>&nbsp  '+ name + "，"+ sex + "，" + age + "岁" +'</span>\n' +
            '   </div>\n' +
            '  </div>\n' +
            '\n' +
            ' </div >\n' +
            ' </div >\n' +
            '</li >'+
            '<li>\n' +
            ' <div class=\'chat\' >\n' +
            '  <div style=\'overflow: hidden\'>\n' +
            '   <div style=\'float: left; \'>\n' +
            '    <img src=' + basePath + '/public/layui/build/images/p1.png alt="">\n' +
            '   </div>\n' +
            '   <div style=\'float: left; margin-left: 8px;  width:70%\'>\n' +
            '    <span>'+ complaint +'</span>\n' +
            '   </div>\n' +
            '  </div>\n' +
            '\n' +
            ' </div >\n' +
            ' </div >\n' +
            '</li >';
        for (var i = 0; i < data.length; i++) {
            if (showExpertFlag == false) {
                result += appendQaNormalHtml(data[i]);
            } else {
                result += appendQaShowExpertHtml(data[i]);
            }
        }
        $('#serviceBox').empty();
        $('#serviceBox').append(result);

    }

    function appendQaNormalHtml(data) {

        //1 图片 2 音频 3 视频 4 其它
        let t = "";
        if (data.isMasculine == "1") {
            t = '↓';
        } else if (data.isMasculine == "2") {
            t = '↑';
        }

        let patientVar = '<span>' + data.valResult + t + '</span>\n' + '<span class="des-stand"> 标准值:' + data.desStand  + '</span>';
        switch (data.sdType) {
            case '1':
                patientVar +=
                    '         <img class="response-img" id="patientImg' + data.idAnswer + '"  src="' + data.path + '" alt="" style="max-width: 350px; height: 200px;cursor: pointer;"' +
                    '                      onerror="onError(this)"' +
                    '                      onclick="openMedia(' + data.sdType + ',' + data.idAnswer + ')">\n';
                break;
            case '2':
                patientVar += '           <p class="voice-box">\n' +
                    '               <audio class="patient-voice" id="patientVoice' + data.idAnswer + '" src="' + data.path + '"></audio>\n' +
                    '               <button class="sound-icon" style="cursor: pointer;" onclick="control(' + data.idAnswer + ')"><img src=' + basePath + '/public/layui/build/images/horn.png alt=""></button>\n' +
                    '           </p>\n';

                break;
            case '3':
                patientVar = '  <span class="time">12"</span>\n' +
                    '           <p class="voice-box">\n' +
                    '               <audio class="patient-voice" id="patientVideo' + data.idAnswer + '" src="' + data.path + '"></audio>\n' +
                    '               <button class="sound-icon" style="cursor: pointer;" onclick="openMedia(' + data.sdType + ',' + data.idAnswer + ')"><i class="iconfont icon-11"></i></button>\n' +
                    '           </p>\n';
                break;
            default:
        }

        var html = "                <li>\n" +
            "                    <div class='chat'>\n" +
            "                        <div style='overflow: hidden'>\n" +
            "                            <div style='float: left '>\n" +
            '                           <img src=' + basePath + '/public/layui/build/images/support.png alt="">\n' +
            "                                <span>【医生】</span>\n" +
            "                            </div>\n" +
            "                            <div style='float: left;  width:70%'>\n" +
            '                               <span>' +  data.naItem + '</span>\n' +
            "                            </div>\n" +
            "                        </div>\n" +
            "                        <div style='margin-left: 41px; margin-top: 7px; overflow: hidden'>\n" +
            "                            <div style='float: left'>\n" +
            "                                <span>【患者】</span>\n" +
            "                            </div>\n" +
            "                            <div style='float: left; width: 70%'>\n" +
            patientVar
            +
            "                            </div>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                </li>";

/*        var html = '<li class="other-side">\n' +
            '           <div class="doctor-details">\n' +
            '               <input class="details-select" type="checkbox"';
        if (data.fgClue == '1') {
            html += 'checked="checked" ';
        }
        html += '            value="' + data.idTestexecResultInspect + '" onclick="checkQa(this)">\n' +
            '               <img src="' + basePath + '/public/biz/img/exam/doctor-avatar.png" alt="" class="doctor-avatar" style="width: 40px; height: 40px;">\n' +
            '               <p class="doctor-response">' + data.naItem + '</p>\n' +
            '            </div>\n' +
            '       </li>\n';

        if (!data.sdType) {
            html += '<li class="patient">\n' +
                '       <div class="patient-details">\n' +
                '           <p class="patient-response">结果:' + data.valResult + '&nbsp;<span class="des-stand"> '+'标准值:'+ data.desStand + '</span></p>\n' +
                '           <img class="patient-avatar" src="' + basePath + '/public/biz/img/exam/patient-avatar.png" alt="" style="width: 40px; height: 40px;">\n' +
                '       </div>\n' +
                '    </li>';
        } else if (data.sdType == '1') {
            html += '<li class="patient-img-response">\n' +
                '       <p class="text" style="right: 70px;">结果:' + data.valResult + '<span class="des-stand"> '+'标准值:'+ data.desStand + '</span></p>\n' +
                '       <p class="img-box">\n' +
                '           <img class="response-img" id="patientImg' + data.idResult + '"  src="' + data.path + '" alt="" style="max-width: 350px; height: 200px;cursor: pointer;"' +
                '               onerror="onError(this)"' +
                '               onclick="openMedia(' + data.sdType + ',' + data.idResult + ')">\n' +
                '           <img class="patient-img-avatar" src="' + basePath + '/public/biz/img/exam/patient-avatar.png" alt="" style="width: 40px; height: 40px;">\n' +
                '       </p>\n' +
                '   </li>';
        } else if (data.sdType == '2') {
            html += '<li class="patient">\n' +
                '       <p class="text">结果:' + data.valResult + '<span class="des-stand"> '+'标准值:'+ data.desStand + '</p>\n' +
                '       <div class="patient-details">\n' +
                '           <span class="time">12"</span>\n' +
                '           <p class="voice-box">\n' +
                '               <audio class="patient-voice" id="patientVoice' + data.idResult + '" src="' + data.path + '"></audio>\n' +
                '               <button class="sound-icon" style="cursor: pointer;" onclick="control(' + data.idResult + ')"><i class="iconfont icon-shengyin"></i></button>\n' +
                '           </p>\n' +
                '           <img class="patient-avatar" src="' + basePath + '/public/biz/img/exam/patient-avatar.png" alt="" style="width: 40px; height: 40px;">\n' +
                '       </div>\n' +
                '    </li>';
        } else if (data.sdType == '3') {
            html += '<li class="patient">\n' +
                '       <p class="text">结果:' + data.valResult + '<span class="des-stand"> '+'标准值:'+ data.desStand + '</p>\n' +
                '       <div class="patient-details">\n' +
                '           <span class="time">12"</span>\n' +
                '           <p class="voice-box">\n' +
                '               <audio class="patient-voice" id="patientVideo' + data.idResult + '" src="' + data.path + '"></audio>\n' +
                '               <button class="sound-icon" style="cursor: pointer;" onclick="openMedia(' + data.sdType + ',' + data.idResult + ')"><i class="iconfont icon-11"></i></button>\n' +
                '           </p>\n' +
                '           <img class="patient-avatar" src="' + basePath + '/public/biz/img/exam/patient-avatar.png" alt="" style="width: 40px; height: 40px;">\n' +
                '       </div>\n' +
                '   </li>';
        }*/

        return html;
    }

    function appendQaShowExpertHtml(data) {
        if (!data.desExpert) {
            data.desExpert = '';
        }

        var html = '<li class="other-side">\n' +
            '           <div class="doctor-details">\n' +
            '               <input class="details-select" type="checkbox"';
        if (data.fgClue == '1') {
            html += 'checked="checked" ';
        }
        html += '            value="' + data.idTestexecResultInspect + '" onclick="checkQa(this)">\n' +
            '               <img src="' + basePath + '/public/biz/img/exam/doctor-avatar.png" alt="" class="doctor-avatar" style="width: 40px; height: 40px;">\n' +
            '               <p class="doctor-response">' + data.naItem + '</p>\n' +
            '            </div>\n' +
            '       </li>\n';

        if (!data.sdType) {
            html += '<li class="patient">\n' +
                '       <div class="patient-details">\n' +
                '           <p class="patient-response">结果:' + data.valResult + '&nbsp;<span class="des-stand"> '+'标准值:'+ data.desStand + '</span></p>\n' +
                '           <img class="patient-avatar" src="' + basePath + '/public/biz/img/exam/patient-avatar.png" alt="" style="width: 40px; height: 40px;">\n' +
                '       </div>\n';
            if (data.desExpert) {
                html += '   <div class="official-details">\n' +
                    '           <span class="details-text">专家解读</span>\n' +
                    '           <p class="official-text">' + data.desExpert + '</p>\n' +
                    '       </div>\n';
            }
            html += '   </li>';
        } else if (data.sdType == '1') {
            html += '<li class="patient-img-response">\n' +
                '       <p class="text" style="right: 70px;">结果:' + data.valResult + '<span class="des-stand"> '+'标准值:'+ data.desStand + '</span></p>\n' +
                '       <p class="img-box">\n' +
                '           <img class="response-img" id="patientImg' + data.idResult + '"  src="' + data.path + '" alt="" style="max-width: 350px; height: 200px;;cursor: pointer;"' +
                '               onerror="onError(this)"' +
                '               onclick="openMedia(' + data.sdType + ',' + data.idResult + ')">\n' +
                '           <img class="patient-img-avatar" src="' + basePath + '/public/biz/img/exam/patient-avatar.png" alt="" style="width: 40px; height: 40px;">\n' +
                '       </p>\n' ;
            if (data.desExpert) {
                html += '   <div class="official-details">\n' +
                    '           <span class="details-text">专家解读</span>\n' +
                    '           <p class="official-text">' + data.desExpert + '</p>\n' +
                    '       </div>\n';
            }
            html += '   </li>';
        } else if (data.sdType == '2') {
            html += '<li class="patient">\n' +
                '       <p class="text">结果:' + data.valResult + '<span class="des-stand"> '+'标准值:'+ data.desStand + '</p>\n' +
                '       <div class="patient-details">\n' +
                '           <span class="time">12"</span>\n' +
                '           <p class="voice-box">\n' +
                '               <audio class="patient-voice" id="patientVoice' + data.idResult + '" src="' + data.path + '"></audio>\n' +
                '               <button class="sound-icon" style="cursor: pointer;" onclick="control(' + data.idResult + ')"><i class="iconfont icon-shengyin"></i></button>\n' +
                '           </p>\n' +
                '           <img class="patient-avatar" src="' + basePath + '/public/biz/img/exam/patient-avatar.png" alt="" style="width: 40px; height: 40px;">\n' +
                '       </div>\n';
            if (data.desExpert) {
                html += '   <div class="official-details">\n' +
                    '           <span class="details-text">专家解读</span>\n' +
                    '           <p class="official-text">' + data.desExpert + '</p>\n' +
                    '       </div>\n';
            }
            html += '   </li>';
        } else if (data.sdType == '3') {
            html += '<li class="patient">\n' +
                '       <p class="text">结果:' + data.valResult + '<span class="des-stand"> '+'标准值:'+ data.desStand + '</p>\n' +
                '       <div class="patient-details">\n' +
                '           <span class="time">12"</span>\n' +
                '           <p class="voice-box">\n' +
                '               <audio class="patient-voice" id="patientVideo' + data.idResult + '" src="' + data.path + '"></audio>\n' +
                '               <button class="sound-icon" style="cursor: pointer;" onclick="openMedia(' + data.sdType + ',' + data.idResult + ')"><i class="iconfont icon-11"></i></button>\n' +
                '           </p>\n' +
                '           <img class="patient-avatar" src="' + basePath + '/public/biz/img/exam/patient-avatar.png" alt="" style="width: 40px; height: 40px;">\n' +
                '       </div>\n';
            if (data.desExpert) {
                html += '   <div class="official-details">\n' +
                    '           <span class="details-text">专家解读</span>\n' +
                    '           <p class="official-text">' + data.desExpert + '</p>\n' +
                    '       </div>\n';
            }
            html += '   </li>';
        }

        return html;
    }

});


function control(idResult) {
    var audio = document.querySelector('#patientVoice' + idResult);
    if (audio !== null) {
        //检测播放是否已暂停.audio.paused 在播放器播放时返回false.
        if (audio.paused) {
            audio.play();//audio.play();// 这个就是播放
        } else {
            audio.pause();// 这个就是暂停
        }
    }
}

/**
 * 打开多媒体
 */
function openMedia(sdType, idResult) {
    layui.use('common', function () {
        if (sdType == '1') {
            var path = $('#patientImg' + idResult).attr('src');
            layui.common.openSinglePhoto(path);
        }
        else if (sdType == '3') {
            var path = $('#patientVideo' + idResult).attr('src');
            layui.common.openTopVideo(basePath + '/video/form?path=' + path, 890, 504);
        }
    });
}

function checkQa(obj) {
    var reqData = new Array();
    reqData.push(obj.value);

    var data = {};
    data.list = reqData;

    data.status = obj.checked ? '1' : '0';
    $.ajax({
        url: basePath + '/pf/r/waiting/room/exam/qa/status',
        type: 'post',
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (data) {
            layer.closeAll('loading');
            if (data.code != 0) {
                layui.use('common', function () {
                    layui.common.errorMsg(data.msg);
                });
                return false;
            }
        },
        error: function () {
            layer.closeAll('loading');
            layui.use('common', function () {
                layui.common.errorMsg("线索标记失败");
            });
            return false;
        }
    });
};

function onError(obj) {
    obj.src = basePath + '/public/biz/img/tupianjiazaishibai.png';
}

