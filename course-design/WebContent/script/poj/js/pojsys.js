
// 选中按钮
function checkSiblingRadio(index) {
	$("input[name='level']").each(function(i, radio){
		if(i == index) {
			$(this).attr("checked", "checked");
		}
	});
}