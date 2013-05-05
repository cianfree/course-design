var jsMenu={
	arrowimages: {
		down: ['downarrowclass', '', 0],
		right:['rightarrowclass', '']
	},

	transition: {
		overtime:300, 
		outtime:300
	}, // 滑出、入动画持续时间，单位是毫秒
	
	shadow: {
		enabled:false, 
		offsetx:5, 
		offsety:5
	},

///////以上均为配置信息///////////////////////////

	detectwebkit: navigator.userAgent.toLowerCase().indexOf("applewebkit") != -1, // 浏览器检测(Safari, Chrome 等等)

	getajaxmenu: function($, setting) { //function to fetch external page containing the panel DIVs
	var $menucontainer=$('#'+setting.contentsource[0]) //reference empty div on page that will hold menu
	$menucontainer.html("Loading Menu...")
	$.ajax({
		url: setting.contentsource[1], //path to external menu file
		async: true,
		error:function(ajaxrequest){
			$menucontainer.html('Error fetching content. Server Response: '+ajaxrequest.responseText)
		},
		success:function(content){
			$menucontainer.html(content)
			jsMenu.buildmenu($, setting)
		}
	})
},

buildshadow:function($, $subul){
	
},

buildmenu:function($, setting){
	var smoothmenu=jsMenu
	var $mainmenu=$("#"+setting.mainmenuid+">ul") //reference main menu UL
	var $headers=$mainmenu.find("ul").parent()
	$headers.each(function(i){
		var $curobj=$(this).css({zIndex: 100-i}) //reference current LI header
		var $subul=$(this).find('ul:eq(0)').css({display:'block'})
		this._dimensions={w:this.offsetWidth, h:this.offsetHeight, subulw:$subul.outerWidth(), subulh:$subul.outerHeight()}
		this.istopheader=$curobj.parents("ul").length==1? true : false //is top level header?
		$subul.css({top:this.istopheader? this._dimensions.h+"px" : 0})
		$curobj.children("a:eq(0)").css(this.istopheader? {paddingRight: smoothmenu.arrowimages.down[2]} : {}).append( //add arrow images
			'<img src="'+ (this.istopheader? smoothmenu.arrowimages.down[1] : smoothmenu.arrowimages.right[1])
			+'" class="' + (this.istopheader? smoothmenu.arrowimages.down[0] : smoothmenu.arrowimages.right[0])
			+ '" style="border:0;" />'
		)
		if (smoothmenu.shadow.enabled){
			this._shadowoffset={x:(this.istopheader?$subul.offset().left+smoothmenu.shadow.offsetx : this._dimensions.w), y:(this.istopheader? $subul.offset().top+smoothmenu.shadow.offsety : $curobj.position().top)} //store this shadow's offsets
			if (this.istopheader)
				$parentshadow=$(document.body)
			else{
				var $parentLi=$curobj.parents("li:eq(0)")
				$parentshadow=$parentLi.get(0).$shadow
			}
			this.$shadow=$('<div class="ddshadow'+(this.istopheader? ' toplevelshadow' : '')+'"></div>').prependTo($parentshadow).css({left:this._shadowoffset.x+'px', top:this._shadowoffset.y+'px'})  //insert shadow DIV and set it to parent node for the next shadow div
		}
		$curobj.hover(
			function(e){
				var $targetul=$(this).children("ul:eq(0)")
				this._offsets={left:$(this).offset().left, top:$(this).offset().top}
				var menuleft=this.istopheader? 0 : this._dimensions.w
				menuleft=(this._offsets.left+menuleft+this._dimensions.subulw>$(window).width())? (this.istopheader? -this._dimensions.subulw+this._dimensions.w : -this._dimensions.w) : menuleft //calculate this sub menu's offsets from its parent
				if ($targetul.queue().length<=1){ //if 1 or less queued animations
					$targetul.css({left:menuleft+"px", width:this._dimensions.subulw+'px'}).animate({height:'show',opacity:'show'}, jsMenu.transition.overtime)
					if (smoothmenu.shadow.enabled){
						var shadowleft=this.istopheader? $targetul.offset().left+jsMenu.shadow.offsetx : menuleft
						var shadowtop=this.istopheader?$targetul.offset().top+smoothmenu.shadow.offsety : this._shadowoffset.y
						if (!this.istopheader && jsMenu.detectwebkit){ //in WebKit browsers, restore shadow's opacity to full
							this.$shadow.css({opacity:1})
						}
						this.$shadow.css({overflow:'', width:this._dimensions.subulw+'px', left:shadowleft+'px', top:shadowtop+'px'}).animate({height:this._dimensions.subulh+'px'}, jsMenu.transition.overtime)
					}
				}
			},
			function(e){
				var $targetul=$(this).children("ul:eq(0)")
				$targetul.animate({height:'hide', opacity:'hide'}, jsMenu.transition.outtime)
				if (smoothmenu.shadow.enabled){
					if (jsMenu.detectwebkit){ //in WebKit browsers, set first child shadow's opacity to 0, as "overflow:hidden" doesn't work in them
						this.$shadow.children('div:eq(0)').css({opacity:0})
					}
					this.$shadow.css({overflow:'hidden'}).animate({height:0}, jsMenu.transition.outtime)
				}
			}
		) //end hover
	}) //end $headers.each()
	$mainmenu.find("ul").css({display:'none', visibility:'visible'})
},

init:function(setting){
	if (typeof setting.customtheme=="object" && setting.customtheme.length==2){
		var mainmenuid='#'+setting.mainmenuid
		document.write('<style type="text/css">\n'
			+mainmenuid+', '+mainmenuid+' ul li a {background:'+setting.customtheme[0]+';}\n'
			+mainmenuid+' ul li a:hover {background:'+setting.customtheme[1]+';}\n'
		+'</style>')
	}
	jQuery(document).ready(function($){ //override default menu colors (default/hover) with custom set?
		if (typeof setting.contentsource=="object"){ //if external ajax menu
			jsMenu.getajaxmenu($, setting)
		}
		else{ //else if markup menu
			jsMenu.buildmenu($, setting)
		}
	})
}

} //end jsMenu variable

//Initialize Menu instance(s):

jsMenu.init({
	mainmenuid: "navMenu", //menu DIV id
	customtheme: [], //override default menu CSS background values? Uncomment: ["normal_background", "hover_background"]
	contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
})