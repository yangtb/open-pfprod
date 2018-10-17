/**
 * 首页
 */
layui.config({
    base: basePath + '/public/layui/plugins/'
}).extend({
    index: 'lib/index' //主入口模块
}).use(['index', 'common'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        common = layui.common;

    $("#modifyPass").on('click', function () {
        var index = layui.layer.open({
            title: '<b>' + '<i class="iconfont icon-modifyPass"></i>' + ' 修改密码' + '</b>',
            //skin: 'layui-layer-molv', //样式类名
            type: 2,
            area: ['450px', '280px'],
            anim: 1,
            fixed: false, //不固定
            maxmin: true,
            content: basePath + '/pf/p/user/modifyPass',
            shadeClose: true
        });
        return index;
    });

    var url = basePath + '/openplatformlogout';
    $('#logout').on('click', function () {
        parent.layer.confirm('你真的确定要退出系统吗？', {
            title: '<b>' + '退出登录提示！' + '</b>',
            resize: false,
            btn: ['确定退出系统', '不，我点错了！'],
            btnAlign: 'c',
            icon: 3
        }, function () {
            location.href = url
        }, function () {

        })
    })

    if (fgActive == '0') {
        $('#orgInfo').click();
    } else {
        var msg;
        if (moment(gmtValid).isBefore(new Date())) {
            msg = "您的平台试用已到期！";
        } else {
            msg = '您的平台试用将于' + moment(gmtValid).format('YYYY月MM月DD日') + '到期！';
        }
        if (moment(gmtValid).isBefore(moment().add(orgExpiryNoticeDay, 'days').calendar())) {
            layer.open({
                type: 1
                ,
                title: false //不显示标题栏
                ,
                closeBtn: false
                ,
                area: '300px;'
                ,
                shade: 0.8
                ,
                id: 'notice_org_expired' //设定一个id，防止重复弹出
                ,
                btn: ['火速续期', '残忍拒绝']
                ,
                btnAlign: 'c'
                ,
                moveType: 1 //拖拽模式，0或者1
                ,
                content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">' +
                    '尊敬的用户：<br><br>' + msg + '<br><br>' +
                    '为了不影响您的使用，请您尽快申请续期 ^_^</div>'
                ,
                success: function (layero) {
                    //var btn = layero.find('.layui-layer-btn');
                    $('.layui-layer-btn0').on('click', function () {
                        $('#orgInfo').click();
                    })
                }
            });
        }
    }

    if (isAnonymousUser == 'true') {
        layer.msg('您当前已进入游客模式！', {
            time: 0 //不自动关闭
            , btn: ['知道了', '已有账号，点击登陆']
            , yes: function (index) {
                layer.close(index);
            }, btn2: function () {
                if (window != top) {
                    top.location.href = basePath + "/login";
                } else {
                    window.location.href = basePath + "/login";
                }
            }
        });
    }

});
