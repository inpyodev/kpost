$(document).on('click', '#barbtn', openCloseBar);
$(document).on('click', '#top-nav > a', tabMove);
$(document).on('click', '#btn-nav', openDrawer);
$(document).on('click', '#drawer-close', closeDrawer);
$(document).on('click', '#drawer-left > a', menuDrawer);
$(document).on('click', '.drawer-menu > li.plus > a', menu2dpsDrawer);
//$(document).on('submit', '#kpa-form', returnReload);
$(document).on('click', '#kpa-submit', returnReload);
$(document).on('click', '#btn-more', protoList);
$(document).on('click', '#proto-btn1', protoDetail1);
$(document).on('click', '#proto-btn2', protoDetail2);
$(document).on('click', '#proto-final', protoFinal);

var uuid;

function getPage(){
	$.ajax({
		url: 'http://116.39.0.146:5000/getScreen',
		type: 'POST',
		data: uuid,
		success: function(data){
			console.log(data)
		}
	});
}

function protoFinal(){
	$(this).addClass('highlighted');
}

function protoDetail1(){
	if ($('.highlighted').length == 0){
		$(this).addClass('highlighted');		
	} else {
		$('.highlighted').removeClass('highlighted');
		$('#proto1').scrollTop(0);
		$('#proto-0').removeClass('active');
		$('#proto-1').addClass('active');
	}	
}

function protoDetail2(){
	if ($('.highlighted').length == 0){
		$(this).addClass('highlighted');		
	} else {
		$('.highlighted').removeClass('highlighted');
		$('#proto1').scrollTop(0);
		$('#proto-1').removeClass('active');
		$('#proto-2').addClass('active');
	}
}

function protoList(){
	if ($('.highlighted').length == 0){
		$(this).addClass('highlighted');		
	} else {
		$('.highlighted').removeClass('highlighted');
		$('#proto1').scrollTop(0);
		$('#proto1').addClass('active');
		$('#proto-0').addClass('active');	
	}
}

function returnReload(){
	var data = $('#kpa-form').serialize();
	
	$.ajax({
		url: 'http://116.39.0.146:7777/connect',
		type: 'POST',
		data: data,
		dataType: 'JSON',
		success: function(data){
			uuid = data.uuid;
			console.log(uuid, data.uuid)
			closeDrawer();
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
	if ($('.highlighted').length == 0){
		$(this).addClass('highlighted');
	} else {
		$('.highlighted').removeClass('highlighted');
		$(this).addClass('active').siblings().removeClass('active');
		$('.content-tab').removeClass('active').eq($(this).index()).addClass('active');
	}
}