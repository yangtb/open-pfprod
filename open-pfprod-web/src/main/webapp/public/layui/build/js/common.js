/**自定义模块*/
layui.define(['layer'], function (exports) {
    var $ = layui.jquery,
        layer = layui.layer;
    var common = {

        /**错误msg提示 */
        errorMsg: function (text) {
            layer.msg(text, {icon: 5});
        },

        errorMsg: function (text, offset) {
            layer.msg(text, {
                offset: offset,
                icon: 5
            });
        },

        /**成功 父页面msg提示 */
        sucMsg: function (text) {
            parent.layer.msg(text);
        },

        /**成功 msg提示 */
        sucChildMsg: function (text) {
            layer.msg(text);
        },

        toastTop: function (text) {
            layer.msg(text, {
                offset: 't',
                anim: 6
            });
        },

        toastTip: function (offset, text) {
            layer.msg(text, {
                offset: offset,
                anim: 6
            });
        },

        /**Confirm 对话框*/
        confirm: function (title, text, confirmFun) {
            layer.confirm(text, {
                title: title,
                resize: false,
                btn: ['确定', '取消'],
                btnAlign: 'c',
                icon: 3
            }, confirmFun)
        },

        /**弹出层 - 视频*/
        openTopVideo: function (title, url, width, height, sucBack, anim) {
            var index = top.layui.layer.open({
                title: title ? '<b>' + title + '</b>' : false,
                //skin: 'layui-layer-molv', //样式类名
                type: 2,
                area: [width + 'px', height + 'px'],
                anim: anim,
                fixed: false, //不固定
                //maxmin: true,
                content: url,
                shadeClose: true,
                success: sucBack
            });
            return index;
        },

        /**弹出层 - 音频*/
        openAudio: function (url) {
            var index = layer.open({
                type: 1,
                title: false, //不显示标题
                shadeClose: true,
                closeBtn: 0, //不显示关闭按钮
                //skin: 'layui-layer-lan',
                area: ['350px', '110px'], //宽高
                content: ' <div style="margin:25px ;">\n' +
                    ' <audio controls=true>\n' +
                    ' <source src=' + url + '.mp3 />\n' +
                    ' <source src= ' + url + '.ogg />\n' +
                    ' 你的浏览器不支持video标签。\n' +
                    ' </audio>\n' +
                    '</div>'
            });
            return index;
        },

        openPhoto: function (json) {
            var index = layui.layer.photos({
                photos: json
                , anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
            });
            return index;
        },


        /**弹出层*/
        open: function (title, url, width, height, sucBack, anim) {
            var index = layui.layer.open({
                title: '<b>' + title + '</b>',
                //skin: 'layui-layer-molv', //样式类名
                type: 2,
                area: [width + 'px', height + 'px'],
                anim: anim,
                fixed: false, //不固定
                maxmin: true,
                content: url,
                shadeClose: true,
                success: sucBack
            });
            return index;
        },

        commonPost: function (url, bizData, msg, selectId) {
            layer.load(1);
            $.ajax({
                url: url,
                type: 'post',
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify(bizData),
                success: function (data) {
                    layer.closeAll('loading');
                    if (data.code != 0) {
                        if (selectId) {
                            layer.tips(data.msg, '#' + selectId, {tips: 1});
                        } else {
                            common.errorMsg(data.msg);
                        }
                        return false;
                    } else {
                        if (selectId) {
                            layer.tips(msg + "成功", '#' + selectId, {tips: 1});
                        } else {
                            common.sucMsg(msg + "成功");
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
            return false;
        },

        commonParentFormPost: function (url, bizData, formType, tableId, msg) {
            layer.load(1);
            $.ajax({
                url: url,
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
                        common.sucMsg(msg + "成功");
                        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                        parent.layer.close(index); //再执行关闭
                        //刷新父页面table
                        if (formType == 'edit') {
                            parent.layui.common.refreshCurrentPage();
                        } else {
                            parent.layui.table.reload(tableId, {
                                height: 'full-68'
                            });
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
            return false;
        },

        /**退出*/
        logOut: function (title, text, url, type, dataType, data, callback) {
            parent.layer.confirm(text, {
                title: title,
                resize: false,
                btn: ['确定退出系统', '不，我点错了！'],
                btnAlign: 'c',
                icon: 3
            }, function () {
                location.href = url
            }, function () {

            })
        },

        /**
         * 刷新table当前页
         */
        refreshCurrentPage: function () {
            $(".layui-laypage-btn").click()
        }
    };
    exports('common', common)
})





