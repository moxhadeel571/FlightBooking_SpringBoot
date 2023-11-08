$(document).ready(function($) {

	"use strict";

	var fullHeight = function() {

		$('.columns').css('height', $(window).height());
		$(window).resize(function(){
			$('.columns').css('height', $(window).height());
		});

	};
	fullHeight();



})(jQuery);
