layui.config({
    base: basePath + '/public/layui/build/js/'
}).extend({
    loading: 'loading/loading'
}).use(['jquery', 'common', 'layer', 'util', 'loading'], function () {
    var $ = layui.$,
        common = layui.common
        , layer = layui.layer
        , util = layui.util;

    var linkList = eval("(" + linkPath + ")");

    //固定块
    util.fixbar({
        bar1: '<i class="iconfont icon-zhenduan" style="font-size: 30px;"></i><span id="nzImg" style="display: block;margin-top: -32px;"></span>'
        , css: {right: 20, bottom: 50}
        , bgcolor: '#393D49'
        , click: function (type) {
            if (type === 'bar1') {
                common.open('拟诊', basePath + '/pf/p/waiting/room/test/referral/page'
                    + '?idMedicalrec=' + idMedicalrec + '&cdMedAsse=009'
                    + '&idTestexecResult=' + $('#idTestexecResult').val()
                    + '&sdTestexec=' + sdTestexec, 850, 450);
            }
        }
    });

    var execTags;
    $(document).ready(function () {
        if (!$('#idTestexec').val()) {
            layer.confirm('请点击【开始】按钮答题', {
                closeBtn: 0, //不显示关闭按钮
                btn: ['开始'] //按钮
            }, function () {
                $('#startBtn').click();
            });
        }

        execTags = document.querySelectorAll(".execTag");
        for (var i = 0; i < execTags.length; i++) {
            execTags[i].addEventListener('click', function () {
                $(this).addClass("active").siblings().removeClass("active");
                loadIframe(this.getAttribute('exec-ref'), this.getAttribute('exec-code'), this.getAttribute('exec-no'));
            });
        }
        execTags[0].click();

        var currentStepNo = $('#currentStepNo').val();

        var nextIndex = 0;
        $.each(linkList, function (i, item) {
            var codeArr = item.split("-");
            if (!currentStepNo) {
                if (i == 0) {
                    $('#currentStepNo').val(codeArr[1]);
                } else if (i == 1) {
                    $('#nextStepExecCode').val(codeArr[0]);
                    $('#nextStepNo').val(codeArr[1]);
                }
            } else {
                if (codeArr[1] == currentStepNo) {
                    if (i == linkList.length - 1) {
                        nextIndex = i;
                    } else {
                        nextIndex = i + 1;
                    }
                }
                if (i == nextIndex) {
                    $('#nextStepExecCode').val(codeArr[0]);
                    $('#nextStepNo').val(codeArr[1]);
                }
            }

        });

    });

    function loadIframe(execRef, execCode, execNo) {
        // 设置下一步节点
        var showStepBtn = false, delShade = false, serialPart = false;

        var currentStepNumber = $('#currentStepNo').val();
        $.each(linkList, function (i, item) {
            var codeArr = item.split("-");
            // 当前执行节点显示
            if (execCode == codeArr[0]) {
                if (currentStepNumber == codeArr[1]) {
                    delShade = true;
                    showStepBtn = true;
                    if (i == linkList.length - 1) {
                        showStepBtn = false;
                    }
                }
                serialPart = true;
            }

            // 小于和大于当前节点加遮罩
            if (serialPart && (execNo < currentStepNumber || execNo > currentStepNumber)) {
                hideAsse(execCode);
            }
            if (linkList.length == 1) {
                delShade = true;
            }
            if (delShade) {
                showAsse(execCode);
            }
        });

        // 下一步按钮显示
        if (showStepBtn) {
            $('#nextStepDiv').show();
        } else {
            $('#nextStepDiv').hide();
        }

        if (!$('#iframe' + execCode).attr("src")) {
            if (!execRef) {
                $('#iframe' + execCode).attr('src', basePath + '/empty/page');
            } else {
                $('#iframe' + execCode).attr('src', basePath + execRef
                    + '?idMedicalrec=' + idMedicalrec + '&cdMedAsse=' + execCode
                    + '&idTestexecResult=' + $('#idTestexecResult').val()
                    + '&sdTestexec=' + sdTestexec);
            }
        }
        $('#iframe' + execCode).removeClass("display-my").siblings().addClass("display-my");
        $('#page' + execCode).removeClass("display-my").siblings().addClass("display-my");
        if (execCode == '004' || execCode == '005' || execCode == '006') {
            showfixBar();
            layer.tips('点击此处可添加拟诊', '#nzImg', {
                tips: [4, '#FF5722'],
                time: 3000
            });
        } else {
            hiddenFixBar()
        }
    }

    function showfixBar() {
        $(".layui-fixbar").show();
    }

    function hiddenFixBar() {
        $(".layui-fixbar").hide();
    }

    $('#startBtn').on('click', function () {
        if ($('#endTime').text()) {
            layer.msg('您已交卷', {icon: 1});
            return;
        }
        if ($('#idTestexec').val()) {
            layer.msg('请开始答题', {icon: 1});
            return;
        }
        var url = basePath + '/pf/r/waiting/room/start';
        var bizData = {
            idTestplanDetail: idTestplanDetail,
            idMedicalrec: idMedicalrec,
            sdTestexec: '0',
            idStudent: $('#studentId').val()
        };
        return common.commonPost(url, bizData, '', null, _starCallback);
    });

    function _starCallback(data) {
        $('#idTestexec').val(data.data.idTestexec);
        $('#idTestexecResult').val(data.data.idTestexecResult);
        $('#beginTime').text(moment(new Date()).format('YYYY-MM-DD HH:mm:ss'));
        layer.msg('请答题', {icon: 1});
    };

    $('#endBtn').on('click', function () {
        if ($('#endTime').text()) {
            layer.msg('您已交卷成功', {icon: 1});
            return;
        }

        layer.confirm('确定要交卷么？', {
            title: '交卷提示',
            resize: false,
            offset: 'rt',
            btn: ['确定', '取消'],
            btnAlign: 'c',
            icon: 3
        }, function (index) {
            var url = basePath + '/pf/r/waiting/room/end';
            var bizData = {
                idTestexec: $('#idTestexec').val(),
                idTestplanDetail: idTestplanDetail,
                sdTestexec: '2'
            };
            common.commonPost(url, bizData, '', null, _endCallback);
            layer.close(index);
        })
    });

    function _endCallback(data) {
        $('#endTime').text(moment(new Date()).format('YYYY-MM-DD HH:mm:ss'));
        layer.msg('交卷成功', {icon: 1});
    };

    // 配置说明
    options = {
        overlayClassName: ''            // 类型String，自定义遮罩层className，可多个，默认空String
        , imgClassName: ''              // 类型String，自定义image的className，可多个，默认空String
        , background: '#fff'            // 类型String，自定义遮罩层背景色，默认#fff
        , opacity: 0.1                  // 类型Number，自定义遮罩层的透明度，默认0.6，注：为0时无遮罩层
        , text: ''                      // 类型String，自定义loading文本，默认空String，注：非空时参考offsetTop设置
        , textCss: {}                   // 类型Object，自定义loading文本样式，默认空{}
        , textClassName: ''             // 类型String，自定义文本的className，可多个，默认空String
        , title: ''                     // 类型String，自定义div、img、text的title，默认空String
        , offsetTop: 0                  // 类型Number，自定义图片+文本模式的top偏移量，注：text为空时无需设置offsetTop
        , imgSrc: 0                     // 类型String|null|Number，自定义loading图片，默认为图片序列的0索引（共0-10），可自定义url路径，注：为null时无图片
        , beforeShow: function () {
        }    // 类型Function，自定义loading显示前的回调，默认空Function，参数1=this，参数2=jQuery
        , afterShow: function () {
        }     // 类型Function，自定义loading显示后的回调，默认空Function，参数1=this，参数2=jQuery，参数3=$loading
        , imgZIndex: 19999999 + 1         // 类型Number，自定义图片的z-index值，默认19999999+1
        , overlayZIndex: 19999999       // 类型Number，自定义遮罩层的z-index值，默认19999999
        , afterHide: function () {
        }     // 类型Function，自定义loading隐藏/销毁后的回调，默认空Function，参数1=this，参数2=jQuery，参数3=$loading(销毁时无参数3)
        , afterHideAll: function () {
        }  // 类型Function，自定义全部loading隐藏/销毁后的回调，默认空Function，参数1=this，参数2=jQuery，参数3=$loading(销毁时无参数3)
        , animateTime: 600              // 类型Number，自定义loading显示/隐藏的动画时长，默认600毫秒，注：为0时无动画
        , clickHide: false              // 类型Boolean，自定义单击loading遮罩层/图片/文字是否隐藏loading，默认false
        , inheritRadius: false          // 类型Boolean，自定义遮罩层是否继承父节点的边框效果，默认false
    }

    function hideAsse(execCode) {
        $('#page' + execCode).loading('show', {
            imgSrc: null,
            background: '#fff',
            text: '',
            textCss: {color: '#393D49'},
            inheritRadius: true,
            clickHide: false
        });
    }

    function showAsse(execCode) {
        $('#page' + execCode).loading('hide');
    }


    $('#nextStep').on('click', function () {
        var bizData = {
            idTestexec: $('#idTestexec').val(),
            curSerialno: $('#nextStepNo').val()
        };
        $.ajax({
            url: basePath + '/pf/r/waiting/room/exec/serial/save',
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
                    // 页签跳转
                    stepPage();
                    return true;
                }
            },
            error: function () {
                layer.closeAll('loading');
                common.errorMsg("查询问答失败");
                return false;
            }
        });

    });

    function stepPage() {
        var index = 0;
        var nextStepExecCode = $('#nextStepExecCode').val();
        for (var i = 0; i < execTags.length; i++) {
            if (execTags[i].getAttribute('exec-code') == nextStepExecCode) {
                index = i;
            }
        }
        setNextInfo(nextStepExecCode);
        execTags[index].click();
    }

    function setNextInfo(execCode) {
        var nextIndex = 0;
        $.each(linkList, function (i, item) {
            var codeArr = item.split("-");
            if (execCode == codeArr[0]) {
                nextIndex = i + 1;
                $('#currentStepNo').val(codeArr[1]);
            } else if (i == nextIndex) {
                $('#nextStepExecCode').val(codeArr[0]);
                $('#nextStepNo').val(codeArr[1]);
            }
        });
    }

});

