$(function(){
	$(".privBtn").bind("click", function(){
		switch($(this).html()) {
		case "提交":
			/*var strs = "";
			$("#browser input:checked").each(function(index, item){
				if($(this).attr("checked")) {
					strs += $(item).val() + "\n";
				}
			});
			alert(strs);*/
			$("#privForm").submit();
			break;
		case "返回":
			window.history.back();
			break;
		default: 
			break;
		}
	});
	
	$("#browser").treeview();
	
	// 添加事件监听
	$("li input").bind("click", function(){
		// 将本li下面的所有input都置为相同的状态
		var state = $(this).attr("checked") || false;
		$(this).parent("li").find("ul:first").find("input").attr("checked", state);
		
		// 如果与本input相邻的其他input都选中了，就把上一层的input也选中
		var flag = true;
		var parentInput = $(this).parent("li").parent("ul").parent("li").find("input:first");
		
		var siblingsInput = $(this).parent("li").siblings().find("input");
		
		if(!state) {	// 如果当前checkbox没有选中，那么就检查兄弟checkbox，如果都没有选中就修改父checkebox状态
			siblingsInput.each(function(index, item){
				//alert(index + "\n" + item);
				if($(this).attr("checked")) {
					flag = false;
					return;
				}
			});
			if(flag) {
				parentInput.attr("checked", false);
			}
		} else {	// 如果选中了就选择父checkbox
			parentInput.attr("checked", "checked");
		}
		
	});
	
	// 全选按钮
	$("#selectAll").bind("click", function(){
		$("#browser input").attr("checked", $(this).attr("checked") || false);
	});
	
});