$(document).ready(function(){

	var ua = navigator.userAgent.toLowerCase();
	var isAndroid = ua.indexOf("android") > -1; //&& ua.indexOf("mobile");
	if(isAndroid) {
	  $('head').append('<link rel="stylesheet" href="assets/css/android.css" charset="UTF-8" />');
	}

	var isBlackBerry = ua.indexOf("blackberry") > -1; //&& ua.indexOf("mobile");
	if(isBlackBerry) {
	  $('head').append('<link rel="stylesheet" href="assets/css/blackberry.css" charset="UTF-8" />');
	  $(".radioBtn").removeClass("radioBtn");
	}

});