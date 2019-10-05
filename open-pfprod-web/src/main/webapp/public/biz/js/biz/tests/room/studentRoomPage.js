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


