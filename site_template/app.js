/* 메뉴바 토글-아이콘 애니메이션 시작 */
function MobileTopBar__init() {
	$('.menu-bar__status-icon').click(function() {
		let $this = $(this);

		if ($this.hasClass('active')) {
			$this.removeClass('active');
		}
		else {
			$this.addClass('active');
		}
	});
}

MobileTopBar__init();
/* 메뉴바 토글-아이콘 애니메이션 끝 */


/* 메뉴바 토글-메뉴 나오기 시작 */
$(document).ready(function() {
	$(".menu-bar__status-icon").click(function() {
		var submenu = $(this).parent().next("div");

		if (submenu.is(":visible")) {
			submenu.slideUp();
		} else {
			submenu.slideDown();
		}

	});
});
/* 메뉴바 토글-메뉴 나오기 끝 */


/* MobileTopBar 옵션 시작 */
function MobileSideBar__init() {
	$('.mobile-menu-bar__status-icon').click(function() {
		$('.mobile-side-bar').addClass('active');
		$('.mobile-side-bar').on('scroll touchmove mousewheel', function(e) {
			e.preventDefault();
			e.stopPropagation();
			return false;
		})
	});
}
function MobileSideBar__cancle() {
	$('.mobile-side-bar_X').click(function() {
		let $this = $(this);
		$this.removeClass('active');
		$('.mobile-side-bar').removeClass('active');
		$('.mobile-side-bar').off('scroll touchmove mousewheel');
	});
}
MobileSideBar__init();
MobileSideBar__cancle();
/* MobileTopBar 옵션 끝 */

/* mobile-side-bar_menu 옵션 시작 */
$(document).ready(function() {
	$(".mobile-side-bar_menu>ul>li>a").click(function() {
		var submenu = $(this).next(".hide2");
		if (submenu.is(":visible")) {
			submenu.slideUp();
		} else {
			submenu.slideDown();
		}
	});
});
/* mobile-side-bar_menu 옵션 끝 */





/* wellcome 애니메이션 시작 */
const showWelcome = $(document).ready(function() {
	var visTime = 600;
	$('.span1').animate({
		opacity: '1'
	}, visTime, function() {
		$('.span2').animate({
			opacity: '1'
		}, visTime, function() {
			$('.span3').animate({
				opacity: '1'
			}, visTime, function() {
				$('.span4').animate({
					opacity: '1'
				}, visTime, function() {
					$('.span5').animate({
						opacity: '1'
					}, visTime, function() {
						$('.span6').animate({
							opacity: '1'
						}, visTime, function() {
							$('.span7').animate({
								opacity: '1'
							}, visTime, function() {
								$('.span8').animate({
									opacity: '0'
								}, visTime, function() {
									$('.span8').animate({
										opacity: '1'
									}, visTime);
								});
							});
						});
					});
				});
			});
		});
	});
});

/* wellcome 애니메이션 끝 */


/* top-button 옵션 시작 */
$(function() {
	// 보이기 | 숨기기
	$(window).scroll(function() {
		if ($(this).scrollTop() < 200) {
			//200 넘으면 버튼이 보임 
			$('.top-button').fadeOut();
		} else {
			$('.top-button').fadeIn();
		}
	});
	// 버튼 클릭시 0 까지 animation 이동합니다. 
	$(".top-button").click(function() {
		$('html, body').animate({
			scrollTop: 0
		}, 200); // 속도 200 
		return false;
	});

});

/* top-button 옵션 끝 */



/* TOAST UI Editor 게시물 BODY에 적용 시작 */
function ArticleDetail__Body__init() {
	if (toastui === undefined) {
		return;
	}

	/* 유튜브 플러그인 함수 시작 */
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
	/* 유튜브 플러그인 함수 끝 */


	/* codepen 플러그인 함수 시작 */
	function codepenPlugin() {
		toastui.Editor.codeBlockManager.setReplacer('codepen', url => {
			const wrapperId = `yt${Math.random().toString(36).substr(2, 10)}`;
			
			setTimeout(renderCodepen.bind(null, wrapperId, url), 0);

			return `<div id="${wrapperId}"></div>`;
		});
	}

	function renderCodepen(wrapperId, url) {
		const el = document.querySelector(`#${wrapperId}`);
		var urlParams = new URLSearchParams(url.split('?')[1]);
		var height = urlParams.get('height');
		el.innerHTML = `<div class="toast-ui-codepen-plugin-wrap"><iframe height="${height}" scrolling="no" src="${url}" frameborder="no" loading="lazy" allowtransparency="true" allowfullscreen="true"></iframe></div>`;
	}
	/* codepen 플로그인 함수 끝 */


	/* toastui-viewr 함수 시작 */
	function EditorViewer__init() {
		$('.toast-ui-viewer').each(function(index, node) {
			var initialValue = $(node).prev().html().trim().replace(/t-script/gi, 'script');
			var viewer = new toastui.Editor.factory({
				el: node,
				initialValue: initialValue,
				viewer: true,
				plugins: [toastui.Editor.plugin.codeSyntaxHighlight, youtubePlugin, codepenPlugin]
			});
		});
	}
	EditorViewer__init();
	/* toastui-viewr 함수 끝 */
}

ArticleDetail__Body__init();

/* TOAST UI Editor 게시물 BODY에 적용 끝 */