 
/**
 * 页面一加载就添加两个对话框，一个是确认对话框，另一个是提示对话框
 */
$(function(){
	$(document.body).append('<div id="dialogConfirm"></div>').append('<div id="dialogTips"></div>');
});

/**
 * 删除用户的时候发出警告对话框
 * @param {} obj
 * @return {}
 */
function deleteComfirm(url, content) {
	$("#dialogConfirm").dialog({
			height: 200,
			modal: true,
			title: '系统提示',
			hide: 'slide',
			buttons: {
				'确定': function(){
					$(this).dialog('close');
					$(window).attr("location", url);
				},
				'取消': function(){
					$(this).dialog("close");
				}
			},
			open: function(event, ui) {
				$(this).html("");
				$(this).append("<p>" + content + "</p>");
			}
		});
}

function deleteUserConfirm(url) {
	deleteComfirm(url, "确定要删除该用户吗？");
}

function deleteRoleConfirm(url) {
	deleteComfirm(url, "确定要删除该角色吗？");
}

function initPassword(id, action, pwd) {
	if(!id) return;
	pwd = pwd || '123456';
	action = action || ajaxUrl;
	
	$("#dialogConfirm").dialog({
			height: 200,
			modal: true,
			title: '系统提示',
			hide: 'slide',
			width: 380,
			buttons: {
				'确定': function(){
					$(this).dialog('close');
					// 此处发起ajax请求，直接在后台修改密码
					$.getJSON(action, {func: "base:initpwd", id: id, pwd: pwd}, function(flag){
						if(flag) {
							simpleTips("密码修改成功");
						} else {
							simpleTips("对不起，服务器出错了，请稍后再尝试！");
						}
					});
				},
				'取消': function(){
					$(this).dialog("close");
				}
			},
			open: function(event, ui) {
				$(this).html("");
				$(this).append("<p>确定要将密码初始化为【" + pwd + "】吗？</p>");
			}
		});
}

function simpleTips(content) {
	$("#dialogTips").dialog({
			height: 200,
			modal: true,
			title: '系统提示',
			hide: 'slide',
			width: 380,
			buttons: {
				'确定': function(){
					$(this).dialog('close');
				}
			},
			open: function(event, ui) {
				$(this).html("");
				$(this).append("<p>" + content + "</p>");
			}
		});
}