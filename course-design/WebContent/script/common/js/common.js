// 公共js

// 当删除题目的时候发出警告
function deleteExe(obj) {
	var url = $(obj).attr("action");
	sysComfirm("您确定要删除吗？", function(){
			$(this).dialog('close');
			$(window).attr("location", url);
		},
		function(){
			$(this).dialog('close');
		}
	);
	/*
	$.jBox.confirm("确定要删除吗？", "提示", function(value, h, f) {
		if(value == "ok") {
			$(window).attr("location", url);
		} else {
			$.jBox.tip("已取消删除！", "提示");
		}
	});
	*/
}

function sysComfirm(content, ok, cancel) {
	$("#dialogModal").dialog({
			height: 200,
			modal: true,
			title: '系统提示',
			hide: 'slide',
			buttons: {
				'确定': function(){
					ok.call(this);
				},
				'取消': function(){
					cancel.call(this);
				}
			},
			open: function(event, ui) {
				$(this).html("");
				$(this).append("<p>" + content + "</p>");
			}
		});
}