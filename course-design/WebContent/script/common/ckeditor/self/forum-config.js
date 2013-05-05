CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	config.language = 'en';
	config.uiColor = '#AADC6E';
	//config.stylesSet = 'mystyles';		// 配置自定义的样式文件，toolbar
	
	// 配置toobar--groups方式
	/*
	config.toolbarGroups = [
		{ name: 'document',    groups: [ 'mode', 'document', 'doctools' ] },
		{ name: 'clipboard',   groups: [ 'clipboard', 'undo' ] },
		{ name: 'editing',     groups: [ 'find', 'selection', 'spellchecker' ] },
		{ name: 'forms' },
		'/',
		{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
		{ name: 'paragraph',   groups: [ 'list', 'indent', 'blocks', 'align' ] },
		{ name: 'links' },
		{ name: 'insert' },
		'/',
		{ name: 'styles' },
		{ name: 'colors' },
		{ name: 'tools' },
		{ name: 'others' },
		{ name: 'about' }
	];
	*/
	
	/*Command:
		Redo:	重做
		About: 	关于
		Blockquote: 双引号
		Templates:	模版
		CreateDiv:	创建DIV
		NumberedList: 用数字来列表
		BulletedList：不是用数字来列表，用小黑圈
		Indent：	向左缩进
		Outdent:	和Indent相反缩进，向右
		Find:		查找按钮，在textarea内查找
		Replace:	替换按钮
		HorizontalRule
		
		
		About
		Blockquote
		Templates
		CreateDiv
		NumberedList
		BulletedList
		Indent
		Outdent
		Find
		Replace
		Flash
		HorizontalRule
		Iframe
		Image
		Smiley			QQ表情
		JustifyLeft
		JustifyCenter
		JustifyRight
		JustifyBlock
		Link
		Unlink
		Anchor
		Maximize
		NewPage
		PageBreak
		PasteText
		PasteFromWord
		Preview
		Print
		RemoveFormat
		Save
		SelectAll
		ShowBlocks
		Source
		SpecialChar
		Table
		Undo
		Redo
	*/
	
	// item-item方式配置工具栏
	config.toolbar = [
		//{ name: 'document', items: [ 'Source', '-', 'NewPage', 'Preview', '-', 'Templates' ] },
		//{ name: 'clipboard', items: [ 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo' ] },
		//'/',
		//{ name: 'basicstyles', items: [ 'Bold', 'Italic' ] }
		
		//{ name: 'picture', items: ['NewPage', 'Templates', '-', 'Cut', 'Copy', '-', 'Undo', 'Redo','About','Blockquote','Templates','CreateDiv','NumberedList','BulletedList','Indent','Outdent','Find','Replace','Flash','HorizontalRule','Iframe','Image','Smiley','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','Link','Unlink','Anchor','Maximize','NewPage','PageBreak','PasteText','PasteFromWord','Preview','Print','RemoveFormat','Save','SelectAll','ShowBlocks','Source','SpecialChar','Table','Undo','Redo'] },
		{ name: 'mytoolbar', items: ['NewPage', 'Smiley','Link','Unlink','NumberedList','Indent','Outdent','SpecialChar', 'Maximize','Preview']}
		
	];
	
	config.height = 120;
	// 配置QQ表情
	config.smiley_images = CKEDITOR.config.smiley_images;
};