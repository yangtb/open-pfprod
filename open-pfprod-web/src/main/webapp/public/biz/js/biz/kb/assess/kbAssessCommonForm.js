

function getAssesssFormHtml() {

    if (!sheets) {
        alert('组件类型不能为空');
    }

    var optionStr = '';

    var sheetList = eval("(" + sheets + ")");

    for (var i = 0; i < sheetList.length; i++) {
        optionStr += appendOptionContent(sheetList[i]);
    }

    // select拼接
    function appendOptionContent(sheet) {
        return '<option value="' + sheet.cdEvaAsse + '">' + sheet.name + '</option>';
    };




    var assesssFormHtml = ' <form class="layui-form" id="useCaseForm" style="margin-top: 5px">\n' +
    '            <div hidden>\n' +
    '                <input name="idMedCase" hidden>\n' +
    '                <input name="cdMedAsse" hidden>\n' +
    '            </div>\n' +
    '            <div class="layui-form-item form-item-my">\n' +
    '                <div class="layui-inline">\n' +
    '                    <label class="layui-form-label">用例名称<i class="iconfont icon-required"\n' +
    '                                                           style="color: #f03f2d"></i></label>\n' +
    '                    <div class="layui-input-block">\n' +
    '                        <input name="name" autocomplete="off" class="layui-input"\n' +
    '                               placeholder="请输入用例名称" lay-verify="required|commonLength" lay-vertype="tips">\n' +
    '                    </div>\n' +
    '                </div>\n' +
    '                <div class="layui-inline">\n' +
    '                    <label class="layui-form-label">用例描述</label>\n' +
    '                    <div class="layui-input-block">\n' +
    '                        <input name="descript" autocomplete="off" class="layui-input"\n' +
    '                               lay-verify="descript" lay-vertype="tips"\n' +
    '                               placeholder="请输入用例描述">\n' +
    '                    </div>\n' +
    '                </div>\n' +
    '                <div class="layui-inline">\n' +
    '                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n' +
    '                    <input type="checkbox" checked="" name="fgActive" lay-skin="switch"\n' +
    '                           value="1" lay-text="启用|停用">\n' +
    '                    &nbsp;&nbsp;&nbsp;\n' +
    '                    <input type="checkbox" name="fgGroup" value="1" title="等效评估">\n' +
    '                </div>\n' +
    '            </div>\n' +
    '\n' +
    '            <div class="layui-form-item form-item-my">\n' +
    '                <div class="layui-inline">\n' +
    '                    <label class="layui-form-label">组件类型<i class="iconfont icon-required"\n' +
    '                                                           style="color: #f03f2d"></i></label>\n' +
    '                    <div class="layui-input-block" style="width: 158px;">\n' +
    '                        <select name="cdEvaAsse" autocomplete="off">\n' +
    '                            <option value="">请选择组件类型</option>\n' +
                                    optionStr +
    '                        </select>\n' +
    '                    </div>\n' +
    '                </div>\n' +
    '                <div class="layui-inline">\n' +
    '                    <label class="layui-form-label">用例归属</label>\n' +
    '                    <div class="layui-input-block" style="width: 158px;">\n' +
    '                        <select name="fgPlat" id="fgPlat" lay-verify="required" lay-vertype="tips" disabled>\n' +
    '                            <option value="">请选择</option>\n' +
    '                            <option value="0">机构</option>\n' +
    '                            <option value="1">平台</option>\n' +
    '                        </select>\n' +
    '                    </div>\n' +
    '                </div>\n' +
    '                <div class="layui-inline">\n' +
    '                    <label class="layui-form-label">使用次数</label>\n' +
    '                    <div class="layui-input-block">\n' +
    '                        <input name="count" autocomplete="off" class="layui-input">\n' +
    '                    </div>\n' +
    '                </div>\n' +
    '            </div>\n' +
    '            <div class="layui-form-item form-item-my">\n' +
    '                <div class="layui-inline">\n' +
    '                    <label class="layui-form-label">上限分值</label>\n' +
    '                    <div class="layui-input-block">\n' +
    '                        <input name="scoreUpper" autocomplete="off" class="layui-input"\n' +
    '                               placeholder="请输入上限分值" lay-verify="required|commonLength" lay-vertype="tips">\n' +
    '                    </div>\n' +
    '                </div>\n' +
    '                <div class="layui-inline">\n' +
    '                    <label class="layui-form-label">下限分值</label>\n' +
    '                    <div class="layui-input-block">\n' +
    '                        <input name="scoreLower" autocomplete="off" class="layui-input"\n' +
    '                               lay-verify="descript" lay-vertype="tips"\n' +
    '                               placeholder="请输入下限分值">\n' +
    '                    </div>\n' +
    '                </div>\n' +
    '                <div class="layui-inline">\n' +
    '                    <label class="layui-form-label">默认分值</label>\n' +
    '                    <div class="layui-input-block">\n' +
    '                        <input name="scoreDefault" autocomplete="off" class="layui-input"\n' +
    '                               lay-verify="descript" lay-vertype="tips"\n' +
    '                               placeholder="请输入默认分值">\n' +
    '                    </div>\n' +
    '                </div>\n' +
    '            </div>\n' +
    '            <hr class="layui-bg-cyan" style="margin: 0px 10px 0px 10px">\n' +
    '        </form>';

    return assesssFormHtml;
}