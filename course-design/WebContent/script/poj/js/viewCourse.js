$(function(){
	window.$studBody = $("#studsBody");
	window.$hwBody = $("#hwBody");
	window.$studPagingDiv = $("#studPagingDiv");
	window.$hwPagingDiv = $("#hwPagingDiv");
	window.courseId = $("#courseId").val();
	
	// 加载权限数据
	window.removeCourseStudent = hasPriv('poj/removeCourseStudent');	
	window.studentSolveStatus = hasPriv('poj/studentSolveStatus');
	window.homeworkSolveStatus = hasPriv('poj/homeworkSolveStatus');
	window.removeHomework = hasPriv('poj/removeHomework');
	window.isMyCourse = isMyCourse(courseId);
	window.isMyStudent = isMyStudent(courseId);
	window.solveHomework = false;
	if(isStudent()) {
		window.solveHomework = hasPriv('poj/solveHomework');
	}
	
	// 显示课程中的学生列表
	showCourseSudents(courseId);
	// 显示课程的作业列表
	showCourseHomeworks(courseId);
});

/**
 * 显示课程的作业列表
 * @param {} courseId
 * @param {} currentPage
 * @param {} pageSize
 */
function showCourseHomeworks(courseId, currentPage, pageSize) {
	$hwBody.html("");
	$hwPagingDiv.html("");
	if(!courseId) return ;
	currentPage = !currentPage ? 1 : currentPage ;
	pageSize = (!pageSize || pageSize > 8) ? 8 : pageSize;
	$.ajax({
		url: ajaxUrl,
		dataType: "json",
		type: "GET",
		data: {
			func: "poj:getCourseHomeworks",
			courseId: courseId,
			currentPage: currentPage,
			pageSize: pageSize
		},
		success: function(json){
			showHomeworks(json);
		}
	});
}

/**
 * 显示作业
 * @param {} pb
 */
function showHomeworks(pb) {
	if(pb && pb.recordCount > 0) {
		var hwArray = pb.recordList;
		for(var i=0; i<hwArray.length; ++i) {
			var hw = hwArray[i];
			
			var trStr = '<tr>' +
						'	<td>' + hw.id + '</td>' +
						'	<td>' + hw.problemName + '</td>' +
						'	<td width="50%">' + hw.problemDescription + '</td>' +
						'	<td width="20%">';
			if(isMyCourse && removeHomework) {
				trStr += '<a href="poj/removeHomework.html?courseId=' + courseId + '&hwId=' + hw.id + '">删除</a>';
			}
			if(studentSolveStatus) {
				trStr += ' <a href="poj/homeworkSolveStatus.html?courseId=' + courseId + '&hwId=' + hw.id + '">作业明细</a>';
			}
			if(isMyStudent && solveHomework) {
				trStr += ' <a href="poj/solveHomeworkUI.html?hwId=' + hw.id + '">做作业</a>';
			}
			trStr += '	</td>' +
					'</tr>';
			$hwBody.append($(trStr));
		}
		showHomeworksPaging(pb);
	}
}

/**
 * 显示作业的分页bar
 * @param {} pb
 */
function showHomeworksPaging(pb) {
	if(!pb || pb.recordCount <= 0) return ;
	$hwPagingDiv.myPaging({
			currentPage: pb.currentPage,
			pageCount: pb.pageCount,
			pageSize: pb.pageSize,
			totalRecord: pb.recordCount,
			showSize: 5,
			callback: function(currentPage, pageSize) {
				// 回调函数，当点击页码的时候就会执行这个函数
				showCourseHomeworks(courseId, currentPage, pageSize);
			}
		});
} 

/**
 * 使用ajax方式获取课程的分页学生列表
 * @param {} currentPage
 * @param {} pageSize
 * @return {}
 */
function showCourseSudents(courseId, currentPage, pageSize) {
	$studBody.html("");
	$studPagingDiv.html("");
	if(!courseId) return ;
	currentPage = !currentPage ? 1 : currentPage ;
	pageSize = (!pageSize || pageSize > 8) ? 8 : pageSize;
	$.ajax({
		url: ajaxUrl,
		dataType: "json",
		type: "GET",
		data: {
			func: "poj:getCourseStudents",
			courseId: courseId,
			currentPage: currentPage,
			pageSize: pageSize
		},
		success: function(json){
			showStudents(json);
		}
	});
}

/**
 * 显示学生
 * @param {} pb
 */
function showStudents(pb) {
	if(pb && pb.recordCount > 0) {
		var userArray = pb.recordList;
		for(var i=0; i<userArray.length; ++i) {
			var user = userArray[i];
			
			var trStr = '<tr>' +
						'	<td>' + user.id + '</td>' +
						'	<td>' + (!user.name ? user.account : user.name) + '</td>' +
						'	<td width="10%">' + user.sex + '</td>' +
						'	<td width="60%">';
			if(isMyCourse && removeCourseStudent) {
				trStr += '<a href="poj/removeCourseStudent.html?courseId=' + courseId + '&studentIds=' + user.id + '">删除</a>';
			}
			if(studentSolveStatus) {
				trStr += ' <a href="poj/studentSolveStatus.html?courseId=' + courseId + '&studentId=' + user.id + '">作业明细</a>';
			}
			trStr += '	</td>' +
					'</tr>';
			$studBody.append($(trStr));
		}
	}
	showStudentsPaging(pb);
}

/**
 * 显示分页信息
 * @param {} pb
 */
function showStudentsPaging(pb) {
	if(!pb || pb.recordCount <= 0) return ;
	$studPagingDiv.myPaging({
			currentPage: pb.currentPage,
			pageCount: pb.pageCount,
			pageSize: pb.pageSize,
			totalRecord: pb.recordCount,
			showSize: 5,
			callback: function(currentPage, pageSize) {
				// 回调函数，当点击页码的时候就会执行这个函数
				showCourseSudents(courseId, currentPage, pageSize);
			}
		});
}

/**
 * 检测当前用户有没有对应的权限
 * @param {} action
 * @return {}
 */
function hasPriv(action) {
	var flag = false;
	$.ajax({
		url: ajaxUrl,
		type: "GET",
		dataType: "json",
		async: false,
		data: {
			func: "base:hasPriv",
			action: action
		},
		success: function(json) {
			flag = json;
		}
	});
	return flag;
}

/**
 * 判断当前用户是不是学生用户
 */
function isStudent() {
	var flag = false;
	$.ajax({
		url: ajaxUrl,
		type: "GET",
		dataType: "json",
		async: false,
		data: {
			func: "poj:isStudent"
		},
		success: function(json) {
			flag = json;
		}
	});
	return flag;
}

/**
 * 检测是否是自己创建的课程
 * @param {} courseId
 */
function isMyCourse(courseId) {
	var flag = false;
	$.ajax({
		url: ajaxUrl,
		type: 'GET',
		dataType: "json",
		async: false,
		data: {
			func: "poj:isMyCourse",
			courseId: courseId
		},
		success: function(json) {
			flag = json;
		}
	});
	return flag;
}

/**
 * 检测当前用户是不是在这个课程中的学生
 * @param {} courseId
 */
function isMyStudent(courseId) {
	var flag = false;
	$.ajax({
		url: ajaxUrl,
		type: "GET",
		dataType: "json",
		async: false,
		data: {
			func: "poj:isMyStudent",
			courseId: courseId
		},
		success: function(json) {
			flag = json;
		}
	});
	return flag;
}