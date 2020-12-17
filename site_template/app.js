

	function ArticleDetail__Body__init() {
		if(toastui === undefined){
			return;
		}
		
		var body = document.querySelector('.article-detail-cell__body > div > span');
		var initValue = body.innerHTML.trim();
		
		var viewer = new toastui.Editor.factory({
			el : body,
			initialValue : initValue,
			viewer : true,
			plugins: [toastui.Editor.plugin.codeSyntaxHighlight]
		});
	};

	ArticleDetail__Body__init();
