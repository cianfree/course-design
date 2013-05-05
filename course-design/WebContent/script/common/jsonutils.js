(function(window){
	var oldJsonParse = window.JSON.parse;
	window.JSON.toJson = function (obj) {
		return window.JSON.stringify(obj);
	}
	
	window.JSON.parse = function(jsonStr) {
		var result = null;
		try {
			result = oldJsonParse(jsonStr);
		} catch (e) {
			result = eval("("+jsonStr+")");
		}
		return result;
	}
})(window);