$(function() {
	$("body").append($('<div id="tipDlg"></div>'));
	$("#exeDesc,#sourceEdit,#executeInfo").draggable({
				containment : "parent",
				revert : false, // 拖动后回到原地方
				stack : "#exeDesc,#sourceEdit,#executeInfo"
			}).resizable();
	$("#clearSource").bind("click", function() {
		$("#source").val("");
	});
	
	$("input[name='language']").click(function() {
		var name = $(this).val();
		$.ajax({
			url: ajaxUrl,
			type: "GET",
			dataType: "text",
			data: {
				func: "poj:sourceTemplate",
				name: name
			},
			success: function(source) {
				$("#source").val(source);
			}
		});
	});
	
	$("#submitSource").bind("click", function() {
	
		// 弹出对话框等待确认
		$("#dialogModal").dialog({
			height : 250,
			width : 500,
			modal : true,
			title : '系统提示',
			hide : 'slide',
			buttons : {
				'确定' : function() {
					// 提交代码
					$(this).dialog('close');
	
					// 发出ajax请求
					var source = $("#source").val();
					var language = "Java";
					$("input[name='language']").each(function(item, index){
						if($(this).attr("checked")) {
							language = $(this).val();
						}
					});
					
					var id = $("#proId").val();
					
					// 等待
					$("#tipDlg").dialog({
						height : 250,
						width : 500,
						modal : true,
						title : '系统提示',
						open : function(event, ui) {
							$(this).html("");
							$(this).append("<p>请等待，系统正在进行执行...</p>");
						}
					});
					
					$.getJSON(ajaxUrl, {
							func: "poj:fixProblem",
							language: language, 
							source: source, 
							id: id, 
							date: new Date()
						}, 
						function(result){
							$("#tipDlg").dialog("close");
							$("#info").html(result);
						}
					);
				},
				'取消' : function() {
					$(this).dialog('close');
				}
			},
			open : function(event, ui) {
				$(this).html("");
				$(this).append("<p>一旦提交代码，无论正确与否，都会记录为本次的成绩！<br/><br/><font color='red'>你确定现在就提交吗？</font></p>");
			}
		});
	
	});

	$("input[name='language']:first").click();
});