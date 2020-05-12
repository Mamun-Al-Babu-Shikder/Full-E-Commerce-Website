
// SS Tools
jQuery(document).ready(function($) {
	$('.so-groups-sticky .sticky-backtop').on('click', function() {
		$('html, body').animate({ scrollTop: 0 }, 'slow', function () {});
	});

	$('.so-groups-sticky *[data-target="popup"]').on('click', function() {
		$('html').addClass('overflow-hidden');
		$($(this).attr('data-popup')).removeClass('popup-hidden');
		$('.popup').animate({
			scrollTop:'0px'
		}, 500);
	});

	$('.so-groups-sticky *[data-target="popup-close"]').on('click', function() {
		$('html').removeClass('overflow-hidden');
		$($(this).attr('data-popup-close')).addClass('popup-hidden');
	});

	$(document).keyup(function(e) {
	     if (e.keyCode == 27) {
	        $('html').removeClass('overflow-hidden');
			$('.so-groups-sticky .popup').addClass('popup-hidden');
	    }
	});

	$('.so-groups-sticky .nav-secondary ul li span i').click(function(){
		if ($(this).hasClass('more')) {
			$('.so-groups-sticky .nav-secondary ul li').removeClass('active');
			$(this).parent().parent().addClass('active');
	    	$(this).parent().parent().children('ul').stop(true, true).slideDown('slow');
	    	$('.so-groups-sticky .nav-secondary ul li').each(function() {
				if ($(this).hasClass('active')) {
					$(this).parent('ul').parent('li').addClass('active');
					$(this).children('ul').slideDown('slow');
				}
			})
			$('.so-groups-sticky .nav-secondary ul li:not(".active") ul').stop(true, true).slideUp('slow');
	    }
	    else {
	    	$(this).parent().parent().children('ul').stop(true, true).slideUp('slow');
	    	$(this).parent().parent().removeClass('active');
	    }
	});

	$('.so-groups-sticky #button-search, .so-groups-sticky .sbmsearch').on('click', function() {
		$('.so-groups-sticky #button-search').attr('disabled','disabled');
		$('.so-groups-sticky #button-search').addClass('loading disabled');
		$('.so-groups-sticky #button-search').prepend('<i class="fa fa-refresh fa-spin"></i>');
		var url = '/search';
		var value = $('.so-groups-sticky #input-search').val();
		if (value) {
			url += '?q=' + encodeURIComponent(value);
		}
		location = url;
	});
	$('.so-groups-sticky #input-search').on('keydown', function(e) {
		if (e.keyCode == 13) {
			$('.so-groups-sticky #button-search').trigger('click');
		}
	});

	$('.so-groups-sticky select[name="select-currency"]').on('change', function() {
		$(this).attr('disabled','disabled');
		$('#form-currency input[name="code"]').val(this.value);
		$('#form-currency').submit();
	});

	$('.so-groups-sticky select[name="select-language"]').on('change', function() {
		$(this).attr('disabled','disabled');
		$('#form-language input[name="code"]').val(this.value);
		$('#form-language').submit();
	});
})