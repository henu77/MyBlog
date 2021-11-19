layui.use(['element', 'jquery', 'form', 'layedit', 'flow'], function () {
    var element = layui.element;
    var form = layui.form;
    var $ = layui.jquery;
    var layedit = layui.layedit;
    var flow = layui.flow;
    //评论和留言的编辑器的验证
    form.verify({
        content: function (value) {
            value = $.trim(layedit.getContent(editIndex));
            if (value == "") return "请输入内容";
            layedit.sync(editIndex);
        },
        replyContent: function (value) {
            if (value == "") return "请输入内容";
        }
    });

    //回复按钮点击事件
    $('#blog-comment').on('click', '.btn-reply', function () {
        var parentCommentId = $(this).data('parentCommentId')
            , repliedUserId = $(this).data('repliedUserId')
            , $container = $(this).parent('p').parent().siblings('.replycontainer');
        if ($(this).text() == '回复') {
            console.log(parentCommentId)
            console.log(repliedUserId)
            // console.log(JSON.parse(repliedUserId))
            // console.log(JSON.parse(repliedUserId).nickname)
            $container.find('textarea').attr('placeholder', '回复【' + repliedUserId + '】');
            $container.removeClass('layui-hide');
            $container.find('input[name="parentCommentId"]').val(parentCommentId);
            $(this).parents('.message-list li').find('.btn-reply').text('回复');
            $(this).text('收起');
        } else {
            $container.addClass('layui-hide');
            $container.find('input[name="parentCommentId"]').val(0);
            $(this).text('回复');
        }
    });
});
 