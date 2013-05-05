$(function(){
	$("body").append($('<div id="tipDlg"></div>'));
	window.courseDiv = $("#courseDiv");
	window.problemDiv = $("#problemDiv");
	window.pageCount = 0;
	window.totalRecord = 0;
});

// 选择课程
function selectCourse() {
	showCourses(1, 8);	
	$("#coursePagingDiv").html("");
	$("#tipDlg").dialog({
		height : 500,
		width: 800,
		modal : true,
		title : '选择课程',
		hide : 'slide',
		buttons : {
			'确定' : function() {
				var courseId = $("#courseDiv").find("input[type=checkbox]:checked").val();
				$("#courseId").val(courseId);
				$(this).dialog('close');
			},
			'取消' : function() {
				$(this).dialog('close');
			}
		},
		open : function(event, ui) {
			$(this).html("");
			courseDiv.show();
			$(this).append(courseDiv);
			resetCoursesPaging(1, 10);
		}
	});
}

function resetCoursesPaging(currentPage, pageSize) {
	$("#coursePagingDiv").html("");
	$("#coursePagingDiv").myPaging({
		currentPage: currentPage,
		pageCount: window.pageCount,
		pageSize: pageSize,
		totalRecord: window.totalRecord,
		showSize: 5,
		callback: resetCoursesPaging
	});
}

function resetProblemsPaging(currentPage, pageSize) {
	$("#problemPagingDiv").html("");
	$("#problemPagingDiv").myPaging({
		currentPage: currentPage,
		pageCount: window.pageCount,
		pageSize: pageSize,
		totalRecord: window.totalRecord,
		showSize: 5,
		callback: resetProblemsPaging
	});	
}

function take(obj, id) {
	$("#" + id).find("input[type=checkbox]").attr("checked", false);
	$(obj).attr("checked", true);
}

function submitForm() {
	$("#hwForm").trigger("submit");
}

function showCourses(currentPage, pageSize) {
	var courses = getCourses(currentPage, pageSize);
	var tbody = $("#courseDiv").find("tbody");
	tbody.html("");
	if(!courses) return ;
	window.pageCount = courses[courses.length - 1].pageCount;
	window.totalRecord = courses[courses.length - 1].recordCount;
	for(var i=0; i<courses.length - 1; ++i) {
		tbody.append('<tr>' +
				'<td><input type="checkbox" value="'+courses[i].id+'" onclick="take(this, \'courseDiv\')"></td>' + 
				'<td>'+ courses[i].id +'</td>' +
				'<td>'+ courses[i].name +'</td>' + 
				'<td>'+ courses[i].owner +'</td>' +
			'</tr>');
	}
}
function showProblems(currentPage, pageSize) {
	var problems = getProblems(currentPage, pageSize);
	var tbody = $("#problemDiv").find("tbody");
	tbody.html("");
	if(!problems) return ;
	window.pageCount = problems[problems.length - 1].pageCount;
	window.totalRecord = problems[problems.length - 1].recordCount;
	for(var i=0; i<problems.length - 1; ++i) {
		tbody.append('<tr>' +
				'<td><input type="checkbox" value="'+problems[i].id+'" onclick="take(this, \'problemDiv\')"></td>' + 
				'<td>'+ problems[i].id +'</td>' +
				'<td>'+ problems[i].name +'</td>' + 
				'<td>'+ problems[i].description +'</td>' +
			'</tr>');
	}
}

function getCourses(currentPage, pageSize) {
	var result = [];
	$.ajax({
		url: ajaxUrl,
		dataType: "json",
		type: "GET",
		async: false,
		data: {
			func: "poj:getCourses",
			currentPage: currentPage,
			pageSize: pageSize
		},
		success: function(json){
			result = json;
		},
		complete: function(req, state) {
		}
	});
	return result;
}

function getProblems(currentPage, pageSize) {
	var result = [];
	$.ajax({
		url: ajaxUrl,
		dataType: "json",
		type: "GET",
		async: false,
		data: {
			func: "poj:getProblems",
			currentPage: currentPage,
			pageSize: pageSize
		},
		success: function(json){
			result = json;
		},
		complete: function(req, state) {
			//alert(req.responseText);
		}
	});
	return result;
}

function selectProblem() {
	showProblems(1, 10);	
	$("#problemPagingDiv").html("");
	$("#tipDlg").dialog({
		height : 500,
		width: 800,
		modal : true,
		title : '选择题目',
		hide : 'slide',
		buttons : {
			'确定' : function() {
				var $checkedInput = $("#problemDiv").find("input[type=checkbox]:checked");
				var courseId = $checkedInput.val();
				$("#problemId").val(courseId);
				$("#proIdTd").html($($checkedInput).parent().siblings("td:eq(1)").html());
				$(this).dialog('close');
			},
			'取消' : function() {
				$(this).dialog('close');
			}
		},
		open : function(event, ui) {
			$(this).html("");
			problemDiv.show();
			$(this).append(problemDiv);
			resetProblemsPaging(1, 10);
		}
	});
}
