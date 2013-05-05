// 本文件用于创建隔行变色的表格，关联与tables.css文件
$(function(){
	$("table tbody tr:even").addClass("evenTr");
	$("table tbody tr:odd").addClass("oddTr");
});