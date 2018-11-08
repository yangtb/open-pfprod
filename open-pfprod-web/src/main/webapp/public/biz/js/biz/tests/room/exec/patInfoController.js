layui.config({
    base: basePath + '/public/layui/build/js/'
        .use(['jquery'], function () {
            var $ = layui.$
                , common = layui.common;

        })
});

