
/* 유튜브 함수 시작 */

	//유튜브 영상임을 감지하고 공간을 형성하는 함수
	function youtubePlugin() {
		toastui.Editor.codeBlockManager.setReplacer('youtube', youtubeId => {
			// Indentify multiple code blocks
			const wrapperId = `yt${Math.random()
				.toString(36)
				.substr(2, 10)}`;

			// Avoid sanitizing iframe tag
			setTimeout(renderYoutube.bind(null, wrapperId, youtubeId), 0);

			return `<div id="${wrapperId}"></div>`;
		});
	}
	//유튜브 영상 렌더링 함수
	function renderYoutube(wrapperId, youtubeId) {
		const el = document.querySelector(`#${wrapperId}`);

		el.innerHTML = `<div class="toast-ui-youtube-plugin-wrap"><iframe src="https://www.youtube.com/embed/${youtubeId}" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe></div>`;
	}
	/* 유튜브 함수 끝 */


/* toastui-viewr 함수 시작 */

function ArticleDetail__Body__init() {
	if (toastui === undefined) {
		return;
	}

	var body = document.querySelector('.article-detail-cell__body > div > span');
	var initValue = body.innerHTML.trim();

	
	var viewer = new toastui.Editor.factory({
		el: body,
		initialValue: initValue,
		viewer: true,
		plugins: [toastui.Editor.plugin.codeSyntaxHighlight, youtubePlugin]
	});
};

ArticleDetail__Body__init();

/* toastui-viewr 함수 끝 */
