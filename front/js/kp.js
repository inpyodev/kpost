$(document).on('click', '#barbtn', openCloseBar);
$(document).on('click', '#top-nav > a', tabMove);
$(document).on('click', '#btn-nav', openDrawer);
$(document).on('click', '#drawer-close', closeDrawer);
$(document).on('click', '#drawer-left > a', menuDrawer);
$(document).on('click', '.drawer-menu > li.plus > a', menu2dpsDrawer);
$(document).on('ajax:complete', '#kpa-form', returnReload);

function returnReload(){
	window.location.href = 'webapp.html';
}

function menu2dpsDrawer(){
	if ($(this).parent().hasClass('active')) $(this).parent().removeClass('active');
	else $(this).parent().addClass('active');
}

function menuDrawer(){
	$(this).addClass('active').siblings().removeClass('active');
	$('.drawer-content').removeClass('active').eq($(this).index()).addClass('active');
}

function openDrawer(){
	$('body').addClass('drawer');
	$('#bar').removeClass('opened');
}

function closeDrawer(){
	$('body').removeClass('drawer');
}

function openCloseBar(){
	if ($('#bar').hasClass('opened')){
		$(this).attr('src', 'img/kp-btnov_up.png');
		$('#bar').removeClass('opened');
	} else {
		$(this).attr('src', 'img/kp-btnov.png');
		$('#bar').addClass('opened');
	}
}

function tabMove(){
	$(this).addClass('active').siblings().removeClass('active');
	$('.content-tab').removeClass('active').eq($(this).index()).addClass('active');
}