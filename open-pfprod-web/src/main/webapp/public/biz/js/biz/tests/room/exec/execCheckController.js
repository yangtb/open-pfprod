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
        var bodyHeight = $(this).height() - 422;
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

    var operation;

    $(document).ready(function () {

        $('#question-select').on("change", function () {
            _tableReload(this.value, null)
        });

    });

    function zTreeOnClick(event, treeId, treeNode) {
        _tableReload(treeNode.id, null);
    };

    function _tableReload(extItemId, keyword) {
        table.reload('partCheckTableId', {
            where: {
                idMedicalrec: idMedicalrec,
                cdMedAsse: cdMedAsse,
                idTestexecResult: idTestexecResult,
                //sdBody: '',
                extItemId: extItemId == '0' ? '' : extItemId,
                keyword: keyword
            }
            , height: '604'
            , page: {
                curr: 1 //重新从第 1 页开始
            }
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

    $('#switch-but').on('click', function () {
        let $switch = $('#switch-text');
        let text = $switch.text();
        if (text == '背面') {
            document.getElementById('body-img').src = basePath + '/public/biz/img/body-back.png';
            $switch.text('正面');
            $('#group-spot-front').hide();
            $('#group-spot-back').show();
        } else {
            document.getElementById('body-img').src = basePath + '/public/biz/img/body-front.png';
            $switch.text('背面');
            $('#group-spot-front').show();
            $('#group-spot-back').hide();
        }
    });

    $('.show-spot').on('click', function () {

        let $1 = $(this);
        $1.children("div").addClass("click-on");
        $1.siblings().children("div").removeClass("click-on");

        let key = $1.children("div").children("span").text();
        let fieldsJSONElement = fieldsJSON[key.replace(/\s*/g,"")];
        $(".spot-circle").hide();

        for(let fields_key in fieldsJSONElement){
            $(".spot-circle[value='" + fields_key + "']").show();
        }

        let $switch = $('#switch-text');
        let text = $switch.text();
        if (text == '背面') {
            $('#group-spot-front').show();
        } else {
            $('#group-spot-back').show();
        }
        operation = $(this).attr('value');
    });

    $('.spot-circle').on('click', function () {

        let position = $(this).attr('value');

        $('#body-check').hide();
        $("#question-select option[value='"+ operation +"']").prop("selected",true).trigger('change');
        $("#item-select option[value='"+ position +"']").prop("selected",true).trigger('change');

        $('#question').show();

    });

    $('#live').on('click', function () {

        let $1 = $(this);
        $1.children("div").addClass("click-on");
        $1.siblings().children("div").removeClass("click-on");
        let key = $1.children("div").children("span").text();
        $(".spot-circle").hide();

        $('#body-check').hide();

        $("#question-select option[value='1']").attr("selected",true).trigger('change');

        $('#question').show();
    });

    $('#return').on('click', function () {
        $('#question').hide();
        $('#body-check').show();
    });

    //执行渲染
    table.render({
        elem: '#partCheckTable' //指定原始表格元素选择器（推荐id选择器）
        , id: 'partCheckTableId'
        , height: '604' //容器高度
        , cols: [[
            {type: 'numbers', title: 'R'},
            {field: 'desBody', minWidth: 140, title: '检查项'},
            //{field: 'cdCheckText', width: 90, title: '检查方式'},
            {field: 'idDieText', width: 90, title: '选择拟诊', toolbar: '#nzTpl'},
            {field: 'qa', width: 60, title: '提问', fixed: 'right', align: 'center', templet: '#qaTpl'}
        ]] //设置表头
        , url: basePath + '/pf/p/waiting/room/test/check/list'
        , data: []
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
                    {type: 'checkbox', fixed: true},
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
                var nameArr = [],  idArr = []
                layui.each(data.data, function (index, item) {
                    nameArr.push(item.name)
                    idArr.push(item.idDie)
                })
                var idStr = idArr.join(",");
                var nameStr = nameArr.join(",");
                elem.attr('data-index', idStr)
                elem.attr('ts-selected', idStr)
                elem.text(nameStr)

                // if check
                if ($('#nz' + id).attr('data-qa-check') == 'true') {
                    var bizData = {
                        idTestexecResult: idTestexecResult,
                        idMedCaseList: id,
                        idDie: idStr
                    }
                    var url = basePath + '/pf/r/waiting/room/check/qa/edit';
                    common.commonPost(url, bizData, null, null, null, false);
                }
            }
        });
    };

    // 页面加载完成查询问答
    $(document).ready(function () {
        init();
    });

    function init() {

        $('#item-select').on("change", function () {
            reloadTable(this.value)
        });

        reloadTable(0);
    }


    function reloadTable(sdBody) {
        table.reload('partCheckTableId', {
            where: {
                idMedicalrec: idMedicalrec,
                cdMedAsse: cdMedAsse,
                sdBody: sdBody == '0' ? '' : sdBody,
                idTestexecResult: idTestexecResult,
                //extItemId: null,
                keyword: ''
            }, page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    };


    //监听行双击事件
    table.on('rowDouble(partCheckTableFilter)', function (obj) {
        let checkInput = $('#check' + obj.data.idMedCaseList);

        if (!checkInput[0].checked) {
            var data = {
                idTestexecResult: idTestexecResult,
                idBody: obj.data.idBody,
                idMedCaseList: obj.data.idMedCaseList,
                fgValid: '0',
                idDie: $('#nz' + obj.data.idMedCaseList).attr('data-index')
            };
            var url = basePath + '/pf/r/waiting/room/check/qa/save';
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

    form.on('checkbox(qaCheckFilter)', function (obj) {
        var qaValue = this.value;
        var qaArr = qaValue.split("-");
        var data = {
            idTestexecResult: idTestexecResult,
            idBody: qaArr[0],
            idMedCaseList: qaArr[1],
            fgValid: obj.elem.checked ? '0' : '1',
            idDie: $('#nz' + qaArr[1]).attr('data-index')
        }
        $('#nz' + qaArr[1]).attr('data-qa-check', 'true');
        var url = basePath + '/pf/r/waiting/room/check/qa/save';
        common.commonPost(url, data, null, null, queryQa, false);
        if (obj.elem.checked) {
            $('#check' + qaArr[1]).attr("disabled", true);
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
            url: basePath + '/pf/r/waiting/room/check/qa/list',
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

        var objDiv = document.getElementById("autoScroll");
        objDiv.scrollTop = objDiv.scrollHeight;

        $('.cons-reply').on('click', function () {
            var idTestexecResultBody = this.getAttribute("data-index");
            var desExpert = this.getAttribute("data-expert");

            layer.prompt({title: '<strong>解释患者回复</strong>', formType: 2}, function(text, index){
                layer.close(index);
                editExmMedResultBody(idTestexecResultBody, '', text, desExpert);
            });
        })

        registerexpertLinkEvent();
    }

    function editExmMedResultBody(id, desReason, desReply, desExpert) {
        var bizData = {
            id : id,
            desReason : desReason,
            desReply : desReply,
            type : 2
        };
        $.ajax({
            url: basePath + '/pf/r/waiting/room/qa/edit',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(bizData),
            success: function (data) {
                layer.closeAll('loading');
                if (data.code != 0) {
                    layer.msg(data.msg);
                    return false;
                } else {
                    if (desReason) {
                        $('#reason-' + id).empty();

                        var html =
                            "                           <div style='background-color: #F2F2F2; padding: 10px; margin: 5px 5px 5px 0;'>\n" +
                            "                               <div style='font-weight: bold; padding: 2px;'>● 问诊理由</div>\n" +
                            "                               <div>" + desReason + "</div>\n" +
                            "                           </div>\n";
                        $('#reason-' + id).append(html);
                    }
                    if (desReply) {
                        $('#reply-' + id).empty();

                        var html =
                            "                           <div style='background-color: #F2F2F2; padding: 10px; margin: 5px 5px 5px 0;'>\n" +
                            "                               <div style='font-weight: bold; padding: 2px;'>● 解释患者的回复</div>\n" +
                            "                               <div>" + desReply + "</div>\n" +
                            "                           </div>\n";
                        if (desExpert) {
                            html +=
                                '                           <div style="margin-top: 10px;"><a class="expertLink" data-index="' + id + '" href="javascript:;" style="color: #009688; text-decoration: underline;">专家解读？</a></div>\n';
                            html +=
                                "                           <div id='expert-div-" + id + "' style='background-color: #F2F2F2; display: none; padding: 10px; margin: 5px 5px 5px 0;'>\n" +
                                "                               <div style='font-weight: bold; padding: 2px;'>● 专家解读</div>\n" +
                                "                               <div>" + desExpert + "</div>\n" +
                                "                           </div>\n";
                        }
                        $('#reply-' + id).append(html);

                        if (desExpert) {
                            registerexpertLinkEvent();
                        }
                    }
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

    function registerexpertLinkEvent() {
        $('.expertLink').on('click', function () {
            var idTestexecResultBody = this.getAttribute("data-index");
            //alert(idTestexecResultBody)

            if ($("#reply-btn-" + idTestexecResultBody).length > 0) {
                $('#reply-btn-' + idTestexecResultBody).addClass("layui-btn-disabled");
                $('#reply-btn-' + idTestexecResultBody).attr("disabled", "disabled");
            }
            $(this).hide();
            $('#expert-div-' + idTestexecResultBody).css("display","block");
        })
    }

    function appendQaNormalHtml(data) {
        //1 图片 2 音频 3 视频 4 其它

        let patientVar = null;
        switch (data.sdType) {
            case '1':
                patientVar = '<span>' + data.desResult + '</span>\n'+
                    '         <img class="response-img" id="patientImg' + data.idAnswer + '"  src="' + data.path + '" alt="" style="max-width: 350px; height: 200px;cursor: pointer;"' +
                    '                      onerror="onError(this)"' +
                    '                      onclick="openMedia(' + data.sdType + ',' + data.idAnswer + ')">\n';
                break;
            case '2':
                patientVar = '<span>' + data.desResult + '</span>\n'+
                    '           <p class="voice-box">\n' +
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
                patientVar = '<span>' + data.desResult + '</span>\n';
        }

        console.log(data)
        var html = "                <li>\n" +
            "                    <div class='chat'>\n" +
            "                        <div style='overflow: hidden'>\n" +
            "                            <div style='float: left '>\n" +
            '                           <img src=' + basePath + '/public/layui/build/images/body.png alt="">\n' +
            "                                <span>【医生】</span>\n" +
            "                            </div>\n" +
            "                            <div style='float: left;  width:70%'>\n" +
            '                               <span>' + data.desBody + '</span>\n' +
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
            "                        </div>\n";

        var expertFlag = data.desExpert ? '1' : '0';
        var replyStyle = data.fgBack == '1' || data.desReply ? 'padding-top: 10px;' : '';
        html +=
            "                        <div  id='reply-" + data.idTestexecResultBody + "' style='padding-left: 45px; " + replyStyle + "'>\n";
        if (data.fgBack == '1' && !data.desReply) {
            html +=
                '                            <button id="reply-btn-' + data.idTestexecResultBody + '" data-index="' + data.idTestexecResultBody + '" data-expert="' + data.desExpert + '" class="layui-btn layui-btn-xs layui-btn-radius cons-reply" style="background-color: #999999">解释患者的回复</button>\n';
        }
        if (data.desReply) {
            html +=
                "                           <div style='background-color: #F2F2F2; padding: 10px; margin: 5px 5px 5px 0;'>\n" +
                "                               <div style='font-weight: bold; padding: 2px;'>● 解释患者的回复</div>\n" +
                "                               <div>" + data.desReply + "</div>\n" +
                "                           </div>\n";
        }
        if (expertFlag == '1') {
            html +=
                '                           <div style="margin-top: 10px;"><a class="expertLink" data-index="' + data.idTestexecResultBody + '" href="javascript:;" style="color: #009688; text-decoration: underline;">专家解读？</a></div>\n';
            html +=
                "                           <div id='expert-div-" + data.idTestexecResultBody + "' style='background-color: #F2F2F2; display: none; padding: 10px; margin: 5px 5px 5px 0;'>\n" +
                "                               <div style='font-weight: bold; padding: 2px;'>● 专家解读</div>\n" +
                "                               <div>" + data.desExpert + "</div>\n" +
                "                           </div>\n";
        }

        html +=
            "                        </div>\n";
        
        html +=
            "                    </div>\n" +
            "                </li>";

        return html;
    }

    function appendQaShowExpertHtml(data) {
        if (!data.desExpert) {
            data.desExpert = '';
        }
        var html = '<li class="other-side">\n' +
            '           <div class="doctor-details">\n' +
            '               <input class="details-select" type="checkbox" name="复选框" id="">\n' +
            '               <img src="' + basePath + '/public/biz/img/exam/doctor-avatar.png" alt="" class="doctor-avatar" style="width: 40px; height: 40px;">\n' +
            '               <p class="doctor-response">' + data.desBody + '</p>\n' +
            '            </div>\n' +
            '       </li>\n';

        if (!data.sdType) {
            html += '<li class="patient">\n' +
                '       <div class="patient-details">\n' +
                '           <p class="patient-response">' + data.desResult + '</p>\n' +
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
                '       <p class="text">' + data.desResult + '</p>\n' +
                '       <p class="img-box">\n' +
                '           <img class="response-img" id="patientImg' + data.idResult + '"  src="' + data.path + '" alt="" style="max-width: 350px; height: 200px;cursor: pointer;"' +
                '               onerror="onError(this)"' +
                '               onclick="openMedia(' + data.sdType + ',' + data.idResult + ')">\n' +
                '           <img class="patient-img-avatar" src="' + basePath + '/public/biz/img/exam/patient-avatar.png" alt="" style="width: 40px; height: 40px;">\n' +
                '       </p>\n';
            if (data.desExpert) {
                html += '   <div class="official-details">\n' +
                    '           <span class="details-text">专家解读</span>\n' +
                    '           <p class="official-text">' + data.desExpert + '</p>\n' +
                    '       </div>\n';
            }
            html += '   </li>';
        } else if (data.sdType == '2') {
            html += '<li class="patient">\n' +
                '       <p class="text">' + data.desResult + '</p>\n' +
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
                '       <p class="text">' + data.desResult + '</p>\n' +
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


    $("#gooey-v").gooeymenu({
        bgColor: "#009688",
        contentColor: "white",
        style: "vertical",
        horizontal: {
            menuItemPosition: "glue"
        },
        vertical: {
            menuItemPosition: "spaced",
            direction: "up"
        },
        circle: {
            radius: 90
        },
        margin: "small",
        size: 50,
        bounce: true,
        bounceLength: "small",
        transitionStep: 100,
        hover: "#44AAA0"
    });

    $('.gooey-menu-item').on('click', function () {
        var type = this.getAttribute("data-type");
        if (type == 1) {
            // 拟诊
            parent.openNzPage();
        }
        if (type == 2) {
            // 病情描述
            var bizData = {
                idTestexecResult: idTestexecResult
            };
            $.ajax({
                url: basePath + '/pf/r/waiting/room/summary/select',
                type: 'post',
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify(bizData),
                success: function (data) {
                    if (data.code != 0) {
                        layer.msg(data.msg);
                        return false;
                    } else {
                        layer.prompt({
                                title: '<strong>体检小结</strong>',
                                formType: 2,
                                anim: 2,
                                value: data.data && data.data.desConditionPe ? data.data.desConditionPe : '' ,
                                offset: [$(window).height() - 300, 100]
                            },
                            function (text, index) {
                                layer.close(index);

                                var bizData = {
                                    idTestexecResult: idTestexecResult,
                                    desConditionPe: text
                                }
                                common.commonPost(basePath + '/pf/r/waiting/room/summary/save', bizData, '保存', null, null);
                            });
                    }
                },
                error: function () {
                    layer.msg("网络异常");
                    return false;
                }
            });
        }
    });

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
        } else if (sdType == '3') {
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
        url: basePath + '/pf/r/waiting/room/check/qa/status',
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
}

function onError(obj) {
    obj.src = basePath + '/public/biz/img/tupianjiazaishibai.png';
}

