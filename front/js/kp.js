$(document).on('click', '#barbtn', openCloseBar);
$(document).on('click', '#top-nav > a', tabMove);
$(document).on('click', '#btn-nav', openDrawer);
$(document).on('click', '#drawer-close', closeDrawer);
$(document).on('click', '#drawer-left > a', menuDrawer);
$(document).on('click', '.drawer-menu > li.plus > a', menu2dpsDrawer);
//$(document).on('submit', '#kpa-form', returnReload);
$(document).on('click', '#kpa-submit', returnReload);

function returnReload(){
	var data = $('#kpa-form').serialize();
	
	$.ajax({
		url: 'http://116.39.0.146:7777/connect',
		type: 'POST',
		data: data,
		success: function(data){
			console.log(data)
		},
		error: function(error){
			console.log(error)
		}
	});
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