/**
 * @author XJQ
 * @since 2012-12-29
 * @version 1.0
 * @argument div object
 * @function 分页
 *  
 */
;
(function($){
	$.fn.extend({
		myPaging: function(config) {
			var options = config || {};
			
			var Paging = window.Config || {
				title: {
					first: 'First',
					prev: 'Prev',
					next: 'Next',
					last: 'Last',
					total: 'Total'
				},
				cssOptions: {
					pagingBar: 'pagingBar',
					first: 'first',
					prev: 'prev',
					next: 'next',
					last: 'last',
					page: 'page',
					selPage: 'selPage',
					total: 'total'
				}
			};
			
			// 默认配置
			var defaults = {
				currentPage: 0,	// 当前页
				pageCount: 0,	// 总页数目
				pageSize: 10,	// 每页数目
				totalRecord: 0,	// 总记录数目
				showSize: 10,	// 分页条显示
				callback: function() {
					return;
				},
				cssOptions: {
					pagingBar: getCss('pagingBar'),
					first: getCss('first'),
					prev: getCss('prev'),
					next: getCss('next'),
					last: getCss('last'),
					page: getCss('page'),
					selPage: getCss('selPage'),
					total: getCss('total')
				},
				title: {
					first: getTitle('first') || 'First',
					prev: getTitle('prev') || 'Prev',
					next: getTitle('next') || 'Next',
					last: getTitle('last') || 'Last',
					total: getTitle('total') || 'Total'
				}
			};			
			
			var self = $(this);
			
			// 渲染
			render();
			// 监听
			addListener();
			
			/**
			 * 添加事件监听
			 */
			function addListener() {
				// 添加函数响应
				self.find("." + defaults.cssOptions.pagingBar + " div").bind("click", function(){
					if($(this).hasClass("total")) {
						return;
					}
					if(config && config.callback && getNextCurrentPage(this) != getCurrentPage()) {
						config.callback.call(this, getNextCurrentPage(this), getPageSize());
					} else {
						return;
					}
				});
			}
			
			/**
			 * 渲染到组件
			 */
			function render() {
				// 添加一个div
				var pagingBar = $('<div class="'+ defaults.cssOptions.pagingBar+'"></div>');
				self.append(pagingBar);
				
				// 创建First,Prev,Next,Last按钮
				var firstBtn = $('<div class="' + defaults.cssOptions.first+'">' + defaults.title.first + '</div>');
				var prevBtn = $('<div class="' + defaults.cssOptions.prev+'">' + defaults.title.prev + '</div>');
				var nextBtn = $('<div class="' + defaults.cssOptions.next+'">' + defaults.title.next + '</div>');
				var lastBtn = $('<div class="' + defaults.cssOptions.last+'">' + defaults.title.last + '</div>');
				
				$(pagingBar).append(firstBtn).append(prevBtn);
				
				for(var i=getBegIndex(); i<=getEndIndex(); ++i) {
					if(i != getCurrentPage()) {
						pagingBar.append('<div class="' + defaults.cssOptions.page+'">' + i + '</div>');
					} else {
						pagingBar.append('<div class="' + defaults.cssOptions.selPage+'">' + i + '</div>');
					}
				}
				
				// 显示next和last按钮
				pagingBar.append(nextBtn).append(lastBtn);
				
				// 显示总页数
				pagingBar.append('<div class="' + defaults.cssOptions.total+'">'+defaults.title.total+': </div>')
						 .append('<div class="' + defaults.cssOptions.total+'">' + getPageCount() + '/' + getTotalRecord()+'</div>');
			}
			
			function getCss(name) {
				if(defaults && defaults.cssOptions[name]) {
					return defaults.cssOptions[name];
				}
				if(options && options.cssOptions && options.cssOptions[name]) {
					return options.cssOptions[name];
				}
				if(Paging && Paging.cssOptions && Paging.cssOptions[name]) {
					return Paging.cssOptions[name];
				}
				return name;
			}
			
			function getTitle(name) {
				if(defaults && defaults.title[name]) {
					return defaults.title[name];
				}
				if(options && options.title && options.title[name]) {
					return options.title[name];
				}
				if(Paging && Paging.title && Paging.title[name]) {
					return Paging.title[name];
				}
				return null;
			}
			
			function getTotalRecord() {
				if(options && options.totalRecord) {
					return options.totalRecord;
				}
				return defaults.totalRecord;
			}
			
			function getPageSize() {
				if(options && options.pageSize) {
					return options.pageSize;
				}
				return defaults.pageSize;
			}
			
			function getCurrentPage() {
				if(options && options.currentPage) {
					return options.currentPage > 0 ? (options.currentPage > getPageCount() ? getPageCount() : options.currentPage) : 1;
				}
				return defaults.currentPage > 0 ? (defaults.currentPage > getPageCount() ? getPageCount() : defaults.currentPage ) : 1;
			}
			
			function getPageCount() {
				if(options && options.pageCount) {
					return options.pageCount;
				}
				return defaults.pageCount;
			}
			
			// 获取下一个当前页，默认是1
			function getNextCurrentPage(obj) {
				var $div = $(obj);
				var nextCurrentPage = 1;
				
				if($div.hasClass(getCss('prev'))) {
					// 如果单击的是prev按钮，计算当前页，当前页面减一
					if(getCurrentPage() > 1) {
						nextCurrentPage = getCurrentPage() - 1;
					}
				} else if($div.hasClass(getCss('next'))) {
					if((getCurrentPage() + 1) > getPageCount()) {
						nextCurrentPage = getPageCount();
					} else {
						nextCurrentPage = (getCurrentPage() + 1);
					}
				} else if($div.hasClass(getCss('last'))) {
					nextCurrentPage = getPageCount();
				} else if($div.hasClass(getCss('page')) || $div.hasClass(getCss('selPage'))){
					nextCurrentPage = $div.html();
				}
				return nextCurrentPage;
			}
			
			function getShowSize() {
				if(options && options.showSize) {
					return options.showSize;
				} else {
					return defaults.showSize;
				}
			}
			
			// 计算begIndex和endIndex
			var indexAdapterFlag = false;
			function indexAdapter() {
				// showSize=10: 左4=10/2-1， 中间选中， 右5=10/2
				// showSize=5:  左2=(5-1)/2, 中间一个，右2=(5-1)/2
				
				indexAdapterFlag = true;
				// 计算showSize
				var showSize = getShowSize();
				var leftSize, rightSize;
				if(showSize % 2 == 0) {
					leftSize = showSize/2 -1;
					rightSize = leftSize + 1;
				} else {
					leftSize = (showSize - 1) / 2;
					rightSize = leftSize;
				}
				
				if(getCurrentPage() - leftSize <= 0) {
					// if((1-4) <= -4)
					begIndex = 1;
					rightSize += (leftSize - getCurrentPage() + 1);
				} else {
					begIndex = getCurrentPage() - leftSize;
				}
				
				if(getCurrentPage() + rightSize > getPageCount()) {
					endIndex = getPageCount();
					begIndex -= (getCurrentPage() + rightSize - getPageCount());
				} else {
					endIndex = getCurrentPage() + rightSize;
				}
				if(begIndex <= 0) begIndex = 1;
				if(endIndex > getPageCount()) endIndex = getPageCount();
			}
			
			function getBegIndex() {
				if(!indexAdapterFlag) {
					indexAdapter();
				}
				return begIndex;
			}
			
			function getEndIndex() {
				if(!indexAdapterFlag) {
					indexAdapter;
				}
				return endIndex;
			}
			
			return $(this);
		}
	});
})(jQuery);
;