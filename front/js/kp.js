$(document).on('click', '#barbtn', openCloseBar);
$(document).on('click', '#top-nav > a', tabMove);

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
	var ind = $(this).index();
	$(this).addClass('active').siblings().removeClass('active');
	$('.content-tab').removeClass('active').eq(ind).addClass('active');
}