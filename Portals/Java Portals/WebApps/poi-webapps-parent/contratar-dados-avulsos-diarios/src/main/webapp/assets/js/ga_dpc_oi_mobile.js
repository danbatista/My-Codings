/**
 * Dpc Jtag v6.1
 *
 * @license Copyright (c) 2009 Direct Performance
 * jTag by Direct Performance (http://www.directperformance.com.br) is licensed under a Creative Commons Atribuicao-Uso Nao-Comercial-Compartilhamento pela mesma Licenca 2.5 Brasil License (http://creativecommons.org/licenses/by-nc-sa/2.5/br/).
 *
 * $Date: 2011/04/11 14:47:32 $
 * @fileoverview funcoes para auxiliar a implementacao do Google Analytics
 * @author dp6 -  http://www.dp6.com.br/ <contato@dp6.com.br>
 * @version $Revision: 8c21d3d578ed $
 
 * ÚLTIMA MODIFICAÇÃO: 2013-06-20 (gustavo.secchim@dp6.com.br)
 */
 
var dpc_conf = {
	version : '6.1211',
	ga_account : 'UA-15029943-1',
	campaign_cookie_timeout : undefined,
	domain_name : ['.oi.com.br','.telemar.com.br','.oi.net.br','.montreal.com.br','.ezconet.com.br','.oipontos.com.br','.prepagoja.com.br','.oinegocios.com.br','.oiloja.com.br','.m4u.com.br','.contax.com.br','.mzweb.com.br','.brasiltelecom.com.br', 'torpedo.oiloja.com.br', 'superfones.com.br'],
	urchin_domains : ['utm.oi.com.br','utm.telemar.com.br','utm.oi.net.br','utm.montreal.com.br','utm.ezconet.com.br','utm.oipontos.com.br','utm.prepagoja.com.br','utm.mzweb.com.br','utm.brasiltelecom.com.br', 'utm.oiloja.com.br'],
	cookie_path : ['/'],
	ignored_ref : ['.oi.com.br','.telemar.com.br','.oi.net.br','.montreal.com.br','.ezconet.com.br','.oipontos.com.br','.prepagoja.com.br','.oinegocios.com.br','.oiloja.com.br','.m4u.com.br','.contax.com.br','.mzweb.com.br','.brasiltelecom.com.br', 'torpedo.oiloja.com.br', 'superfones.com.br', 'serasa.com.br'],
	allow_anchor : true,
	enable_auto_pageview : true,
	link_track_event : {
		external : ["a:external, area:external", "href"]
	},
	link_track_pageview : {},
	allow_linker : true,
	allow_hash : false,
	cookie_copy_href : ['www.oi.com.br','montreal.com.br','oi.net.br','telemar.com.br','ezconet.com.br','oipontos.com.br','prepagoja.com.br','oinegocios.com.br','oiloja.com.br','m4u.com.br','contax.com.br','mzweb.com.br','brasiltelecom.com.br','torpedo.oiloja.com.br', 'superfones.com.br', 'serasa.com.br'],
	tag_form_location : [
	
	],
	max_scroll_location : [],
	track_time_on_page : false,
	track_time_to_load : false,
	track_page_load_time : true,
	enable_meta_cluster : false,
	enable_meta_ecommerce : false,
	enable_namespace : false,
	init_data : false
};

/* START DPC CUSTOM */
var dpc_custom = {
	orig_title: document.title,
	random_seed: '',
	hook_asap: function (url, ref) {
		
		//definindo random_seed (para transacoes)
		var tempPath = document.location.pathname.split('/');
		tempPath.splice(0,1);
		tempPath.pop();
		dpc_custom.random_seed = String(tempPath.join('-').toLowerCase()+'-'+((new Date()).getTime()));
		
		//Fix transaction issue. Due to _gaq event ordering
		try {
			if (typeof _aux_gaq !== 'undefined' && _aux_gaq.length > 0)
				var i;
			while (i = _aux_gaq.shift()) {
				_gaq.push(i);
			}
		} catch (e) { }
	},
	hook_set_account: function () {
		//Enable Urchin
		// Get current Urchin Domain
		var domain = location.host;
		// oi sem www
		if (domain === 'oi.com.br') {
			domain = 'www.oi.com.br';
		}
		domain = domain.replace(/^[^.]+/, 'utm');
		// Apenas habilita urchin se estiver dentro dos domínios permitidos
		if (jQuery.inArray(domain, dpc_conf.urchin_domains) >= 0) {
			//VERIFICA SE É HTTP ou HTTPS
			dpc_href = document.location.href.toLowerCase();
			if (dpc_href.indexOf("https") >= 0) { domain = 'https://' + domain; } else { domain = 'http://' + domain; }
			for (i in dpc_conf.ga_account) {
				if (dpc_conf.ga_account.hasOwnProperty(i)) {
					_gaq.push([i + '_setLocalGifPath', domain + '/__utm.gif']);
					_gaq.push([i + '_setLocalRemoteServerMode']);
					break;
				}
			}
		}
	},
	//hook_onload: function (url, ref) {},
	//hook_onunload : function (url, ref) {},
	//hook_pageview: function (url) {},
	hook_event: function (category, action, label, num) {
		if (category == '') category = '-';
		if (action == '') action = '-';
	},
	//hook_setvar : function (obj_cluster) {},
	hook_ecommerce_add_trans: function (obj_tran) {
		
		if(obj_tran.orderid.toLowerCase() === 'random'){
			obj_tran.orderid = 'mobile-' + dpc_custom.random_seed;
		}
		
	},
	hook_ecommerce_add_item: function (obj_item) {
		
		if(obj_item.orderid.toLowerCase() === 'random'){
			obj_item.orderid = 'mobile-' + dpc_custom.random_seed;
			obj_item.category = 'mobile-' + obj_item.category;
		}
		
	},
	//hook_ecommerce_track_trans : function () {},
	dpc_pageview_virtual: function (pageview_virtual) {
		dpc_core.track_pageview(pageview_virtual);
	}
};
/* END DPC CUSTOM */


//Funcao para facilitar uso das tags de transacao de Oi
function dpc_callTrans(cidade, estado, pais, regiao, category, sku, productname, price){

	var transaction = {
		orderid: 'random',
		total: price || '0.0',
		city: cidade || '',
		state: estado || '',
		country: pais || '',
		storename: regiao || ''
	};
	var item = {
		orderid: 'random',
		sku: sku,
		productname: productname,
		category: category,
		unitprice: price || '0.0',
		quantity: '1'
	};
	dpc_ecommerce.add_trans(transaction);
	dpc_ecommerce.add_item(item);
	dpc_ecommerce.track_trans();
}


//EXTRAI PARAMETRO DA URL
function extractParamFromUri(uri, paramName) {
	if (!uri) {
		return;
	}
	var uri = uri.split('#')[0];  // Remove anchor.
	var parts = uri.split('?');  // Check for query params.
	if (parts.length == 1) {
		return;
	}
	var query = decodeURI(parts[1]);
	// Find url param.
	paramName += '=';
	var params = query.split('&');
	for (var i = 0, param; param = params[i]; ++i) {
		if (param.indexOf(paramName) === 0) {
			return unescape(param.split('=')[1]);
		}
	}
}


var _gaq = _gaq || [];
// Zera vetor _gaq
// Vetor _aux_gaq é executado no final depois do pageview padrão
try{
	var _aux_gaq = [];
	var i;
	while (i=_gaq.shift()){
		_aux_gaq.push(i);
	}
} catch (e) { }

//--*Secao de Funcoes de tagueamento de Oi*--

//CUSTOM VARIABLES
function dp6_novaAcaoProd() {
	window.dp6_acaoProd = window.dp6_acaoProd || (+dpc_cookie.get("acaoProd")) || 0;
	dpc_cookie.set("acaoProd", ++window.dp6_acaoProd);
	dpc_cookie.set_custom_var(1, "Acao Produtiva", "Numero " + dpc_cookie.get("acaoProd"), 2);
}

//--*Fim Secao de Funcoes de tagueamento de Oi*--


/* NÃO MEXA DAQUI PRA BAIXO */
var _gaq=_gaq||[],dpc_core={_setAccount:function(a,b){var c=0,d=-1;_gaq.push([b+"_setAccount",a]);if("undefined"!==typeof dpc_conf.domain_name){if(dpc_conf.domain_name instanceof Array){for(c=0;c<dpc_conf.domain_name.length;c+=1){if("auto"===dpc_conf.domain_name[c]||"none"===dpc_conf.domain_name[c]){dpc_conf.domain_name=dpc_conf.domain_name[c];break}if(""===("."+window.location.hostname).split(dpc_conf.domain_name[c]).pop()){dpc_conf.domain_name=dpc_conf.domain_name[c];break}}dpc_conf.domain_name instanceof
Array&&(dpc_conf.domain_name="auto")}_gaq.push([b+"_setDomainName",dpc_conf.domain_name])}if("undefined"!==typeof dpc_custom&&"function"===typeof dpc_custom.hook_set_account)try{dpc_custom.hook_set_account(b)}catch(e){dpc_core.track_error_event(e,"hook_set_account: "+e.message)}"undefined"!==typeof dpc_conf.campaign_cookie_timeout&&_gaq.push([b+"_setCampaignCookieTimeout",parseInt(dpc_conf.campaign_cookie_timeout,10)]);dpc_conf.enable_namespace&&(!0===dpc_conf.enable_namespace&&""!==b)&&_gaq.push([b+
"_setNamespace",b.substr(0,b.length-1)]);"undefined"!==typeof dpc_conf.allow_linker&&_gaq.push([b+"_setAllowLinker",dpc_conf.allow_linker]);"undefined"!==typeof dpc_conf.allow_hash&&_gaq.push([b+"_setAllowHash",dpc_conf.allow_hash]);"undefined"!==typeof dpc_conf.allow_anchor&&_gaq.push([b+"_setAllowAnchor",dpc_conf.allow_anchor]);for(c=0;c<dpc_conf.ignored_ref.length;c+=1)_gaq.push([b+"_addIgnoredRef",dpc_conf.ignored_ref[c]]);if(dpc_conf.cookie_path&&0<dpc_conf.cookie_path.length){for(c=0;c<dpc_conf.cookie_path.length;c+=
1)if(""===document.location.pathname.split(dpc_conf.cookie_path[c])[0]){d=c;_gaq.push([b+"_setCookiePath",dpc_conf.cookie_path[c]]);break}0>d&&_gaq.push([b+"_setCookiePath","/"]);for(c=0;c<dpc_conf.cookie_path.length;c+=1)d!==c&&_gaq.push([b+"_cookiePathCopy",dpc_conf.cookie_path[c]])}_gaq.push([b+"_addOrganic","busca","query"],[b+"_addOrganic","pesquisa","q"],[b+"_addOrganic","uol","q"],[b+"_addOrganic","ig","q",!0],[b+"_addOrganic","globo","query",!0],[b+"_addOrganic","r7","q"],[b+"_addOrganic",
"clicrbs","q"],[b+"_addOrganic","twitter","q"],[b+"_addOrganic","real","query"],[b+"_addOrganic","mywebsearch","searchfor"],[b+"_addOrganic","esnips","searchQurey"],[b+"_addOrganic","topbuscas","search"],[b+"_addOrganic","google.com.br","q",!0]);"number"==typeof dpc_conf.site_speed_sample_rate&&_gaq.push([b+"_setSiteSpeedSampleRate",dpc_conf.site_speed_sample_rate]);dpc_conf.init_data&&_gaq.push([b+"_initData"])},get_generic_tracker:function(){if("undefined"!=typeof _gat)return _gat._getTrackerByName(dpc_conf.tracker_name[0]);
"undefined"!==typeof console&&"undefined"!==typeof console.error&&console.error('[dpc_core.get_generic_tracker] "_gat" is undefined.');return!1},clear_ga_get_params:function(a){var b,c,d,e="",f="utm_campaign utm_medium utm_source utm_term utm_content utm_id utm_nooverride __utma __utmb __utmc __utmx __utmz __utmv __utmk".split(" ");b=a.indexOf("?");if(0<=b){d=a.substring(b+1).split("&");a=a.substring(0,b);for(b=0;b<d.length;b+=1)(c=d[b].split("=")[0])&&0>jQuery.inArray(c,f)&&(e+=(e?"&":"?")+d[b])}return a+
e},get_location_params:function(){try{return dpc_core.clear_ga_get_params(document.location.search)}catch(a){return dpc_core.track_error_event(a,"clear_ga_get_params: "+a.message),document.location.search}},sUrl:document.location.href.toLowerCase(),sReferrer:document.referrer.toLowerCase(),sanitize:function(a,b){a=jQuery.trim(a).toLowerCase().replace(/\s+/g,"_").replace(/[\u00e1\u00e0\u00e2\u00e3\u00e5\u00e4\u00e6\u00aa]/g,"a").replace(/[\u00e9\u00e8\u00ea\u00eb\u0404\u20ac]/g,"e").replace(/[\u00ed\u00ec\u00ee\u00ef]/g,
"i").replace(/[\u00f3\u00f2\u00f4\u00f5\u00f6\u00f8\u00ba]/g,"o").replace(/[\u00fa\u00f9\u00fb\u00fc]/g,"u").replace(/[\u00e7\u00a2\u00a9]/g,"c");!0===b&&(a=a.replace(/[^a-z0-9\-]/g,"_").replace(/_+/g,"_"));return a},track_error_event:function(a,b){try{var c;"undefined"!==typeof console&&"undefined"!==typeof console.error&&console.error("Exception "+(a.name||"Error"),b||a.message||a,dpc_core.sPathname);for(c in dpc_conf.ga_account)dpc_conf.ga_account.hasOwnProperty(c)&&_gaq.push([c+"_trackEvent",
"Exception "+(a.name||"Error"),b||a.message||a,dpc_core.sPathname,0,!0])}catch(d){}},track_timing:function(a,b,c,d,e){d||(d=null);e||(e=null);for(i in dpc_conf.ga_account)dpc_conf.ga_account.hasOwnProperty(i)&&_gaq.push([i+"_trackTiming",a,b,c|0,d,e])},track_pageview:function(a){var b,c;if("undefined"!==typeof dpc_custom&&"function"===typeof dpc_custom.hook_pageview){try{b=dpc_custom.hook_pageview(a||dpc_core.sPathname)}catch(d){dpc_core.track_error_event(d,"hook_pageview: "+d.message)}"undefined"!==
typeof b&&b&&(a=b)}for(c in dpc_conf.ga_account)dpc_conf.ga_account.hasOwnProperty(c)&&_gaq.push([c+"_trackPageview",a||dpc_core.sPathname])},track_event:function(a,b,c,d,e){var f;if("undefined"!==typeof dpc_custom&&"function"===typeof dpc_custom.hook_event)try{dpc_custom.hook_event(a,b,c,d,e)}catch(h){dpc_core.track_error_event(h,"hook_event: "+h.message)}for(f in dpc_conf.ga_account)dpc_conf.ga_account.hasOwnProperty(f)&&(a=String(a).replace(/^\s+$|^$/,"(not set)"),b=String(b).replace(/^\s+$|^$/,"(not set)"),c=String(c).replace(/^\s+$|^$/,"(not set)"),!isNaN(d)&&(d||0===d)?_gaq.push([f+
"_trackEvent",a,b,c||"",d|0,!!e]):_gaq.push([f+"_trackEvent",a,b,c||""]))},set_var:function(a){dpc_cookie.set_custom_var(5,"setvar",a,1)},get_linker_url:function(a){var b=dpc_core.get_generic_tracker();return b?b._getLinkerUrl(a,!!dpc_conf.allow_anchor):a},encode:function(a){return encodeURIComponent instanceof Function?encodeURIComponent(a):escape(a)},decode:function(a){try{return decodeURIComponent instanceof Function?decodeURIComponent(a):unescape(a)}catch(b){if("URIError"===b.name)return unescape(a)}return a},
open_link:function(a,b){_gaq.push(function(){a=dpc_core.get_linker_url(a);if(b)return window.open(a,b);document.location.href=a;return!1})},get_identifier:function(a,b){var c;c=!1!==b?a.attr(b):a.attr("id")||a.text()||a.attr("href")||a.attr("name")||a.attr("alt")||a.attr("title")||a.attr("src")||a.attr("class")||a[0].tagName.toLowerCase();return jQuery.trim(c).substring(0,100)},add_special_events:function(a){if("object"!==typeof a)return!1;var b,c,d;for(b in a)a.hasOwnProperty(b)&&(a[b]instanceof
Array?(c=a[b][0],d=a[b][1]):(c=a[b],d=!1),jQuery(c).bind("mousedown",{i:b,attr:d},function(a){var b=dpc_core.get_identifier(jQuery(this),a.data.attr);dpc_core.track_event(a.data.i,dpc_core.sPathname,b)}));return!0},add_special_pageviews:function(a){if("object"!==typeof a)return!1;var b,c,d;for(b in a)a.hasOwnProperty(b)&&(a[b]instanceof Array?(c=a[b][0],d=a[b][1]):(c=a[b],d=!1),jQuery(c).bind("mousedown",{i:b,attr:d},function(a){var b=dpc_core.get_identifier(jQuery(this),a.data.attr);dpc_core.track_pageview(dpc_core.sPathname+
"/"+a.data.i+"/"+b)}));return!0},add_cookie_to_href:function(a){var b,c,d,e;d=jQuery([]);e=jQuery([]);for(b=0;b<a.length;b+=1)c=a[b],c instanceof RegExp?(c=c.toString(),"/"===c.charAt(0)&&(c=c.substring(1,c.length-1)),d=d.add("a:hrefregex('"+c+"'):external, area:hrefregex('"+c+"'):external"),e=e.add("form:actionregex('"+c+"')")):"string"===typeof c&&(d=d.add("a[href*='"+c+"']:external, area[href*='"+c+"']:external"),e=e.add("form[action*='"+c+"']"));var f=d;_gaq.push(function(){f.bind("mousedown",
function(){var a=jQuery(this);a.data("cookie_copied")||a.attr("href",function(){return dpc_core.get_linker_url(this.href)}).data("cookie_copied",!0)})});e.each(function(){if(!jQuery(this).data("cookie_copied")&&this.action){var a=this;_gaq.push(function(){jQuery(a).submit(function(){dpc_core.get_generic_tracker()._linkByPost(this,!!dpc_conf.allow_anchor)})});jQuery(this).data("cookie_copied",!0)}})},write_img_tag:function(a,b){var c,d;b||(c=new Date,c=c.getTime(),a=0<a.indexOf("?")?a+("&_dpc_cb="+
c):a+("?_dpc_cb="+c));try{d=new Image(1,1),d.src=a}catch(e){jQuery('<img width="1" height="1" src="'+a+'"/>').appendTo("body")}}};try{dpc_core.sPathname=document.location.pathname+dpc_core.get_location_params()}catch(e$$17){dpc_core.sPathname=document.location.pathname+document.location.search,dpc_core.track_error_event(e$$17,"set Pathname: "+e$$17.message)}
if("undefined"!==typeof dpc_custom&&"function"===typeof dpc_custom.hook_pathname)try{var dpc_hook_pathname_aux=dpc_custom.hook_pathname(dpc_core.sPathname);dpc_hook_pathname_aux&&"string"===typeof dpc_hook_pathname_aux&&(dpc_core.sPathname=dpc_hook_pathname_aux)}catch(e$$18){dpc_core.track_error_event(e$$18,"hook_pathname: "+e$$18.message)}
var dpc_cookie={set:function(a,b,c){a=a+"="+dpc_core.encode(b);!0!==c&&(c=parseInt(dpc_conf.campaign_cookie_timeout,10),isNaN(c)&&(c=63072E6),c=new Date((new Date).getTime()+c),a+="; expires="+c.toGMTString());c=dpc_conf.domain_name;"none"===c?c="":("auto"===c&&(c=window.location.hostname,0===c.indexOf("www.")&&(c=c.substring(4))),c=" ;domain="+c);document.cookie=a+(";path=/"+c)},set_session:function(a,b){dpc_cookie.set(a,b,!0)},get:function(a){var b=document.cookie,c=b.indexOf(a+"="),d;if(0>c||""===
a)return"";d=b.indexOf(";",c);0>d&&(d=b.length);return dpc_core.decode(b.substring(c+a.length+1,d))},get_cluster:function(){return dpc_core.get_generic_tracker()._getVisitorCustomVar(5)},get_cluster_var:function(a){var b=dpc_core.get_generic_tracker()._getVisitorCustomVar(5);return a=(a=RegExp(a+"=([^|]*)","g").exec(b))&&a[1]?a[1]:!1},set_cluster_var:function(a){var b,c="";for(b in a)a.hasOwnProperty(b)&&(c+="|"+dpc_core.sanitize(b,!0)+"="+dpc_core.sanitize(a[b],!0));dpc_cookie.set_custom_var(5,"setvar",
c,1);dpc_core.track_event("Load Set Var",dpc_core.sPathname)},set_custom_obj:function(a){for(var b in a)a.hasOwnProperty(b)&&dpc_cookie.set_custom_var(a[b].index,b,a[b].val,a[b].scope);dpc_core.track_event("Load Custom Var",dpc_core.sPathname)},set_custom_var:function(a,b,c,d){var e,f;if("undefined"!==typeof dpc_custom&&"function"===typeof dpc_custom.hook_custom_var)try{f=dpc_custom.hook_custom_var(a,b,c,d),"object"==typeof f&&(a=f.index,b=f.name,c=f.val,d=f.scope)}catch(h){dpc_core.track_error_event(h,
"hook_custom_var: "+h.message)}a=parseInt(a,10);b=b.substr(0,127).toLowerCase();c=c.substr(0,128-b.length);d=parseInt(d,10);for(e in dpc_conf.ga_account)dpc_conf.ga_account.hasOwnProperty(e)&&_gaq.push([e+"_setCustomVar",a,b,c,d])},get_ga_visitnumber:function(){var a=dpc_cookie.get("__utma");return a=(a=/([^.]*$)/.exec(a))&&a[1]?parseInt(a[1],10):1},get_ga_visitorid:function(){var a=dpc_cookie.get("__utma");return a=(a=/^(\^[^.]*[^:]*\:)?[^.]*\.([^.]*\.[^.]*)\.[^.]*\.[^.]*\.[^.]*/.exec(a))&&a[2]?
a[2]:0},get_ga_initialvisit:function(){var a=dpc_cookie.get("__utma"),a=/^(\^[^.]*[^:]*\:)?[^.]*\.[^.]*\.([^.]*)\.[^.]*\.[^.]*\.[^.]*/.exec(a),b=new Date,a=a&&a[2]?parseInt(a[2],10):0;b.setTime(1E3*a);return b},get_ga_previousession:function(){var a=dpc_cookie.get("__utma"),a=/^(\^[^.]*[^:]*\:)?[^.]*\.[^.]*\.[^.]*\.([^.]*)\.[^.]*\.[^.]*/.exec(a),b=new Date,a=a&&a[2]?parseInt(a[2],10):0;b.setTime(1E3*a);return b},get_ga_currentsession:function(){var a=dpc_cookie.get("__utma"),a=/^(\^[^.]*[^:]*\:)?[^.]*\.[^.]*\.[^.]*\.[^.]*\.([^.]*)\.[^.]*/.exec(a),
b=new Date,a=a&&a[2]?parseInt(a[2],10):0;b.setTime(1E3*a);return b},auto_meta_cluster:function(){var a={},b={};jQuery("meta[name^='DPC.CLUSTER.']").each(function(){a[this.name.toLowerCase().substring(12)]=this.content}).each(function(){dpc_cookie.set_cluster_var(a);return!1});jQuery("meta[name^='DPC.CUSTOM_VAR.']").each(function(){var a=this.name.match(/\w+\.\w+\.(\d)\.(\d)\.(\w+)/);b[a[3]]={index:a[1],val:this.content,scope:a[2]}}).each(function(){dpc_cookie.set_custom_obj(b);return!1})}},dpc_ecommerce=
{add_trans:function(a){var b;if("undefined"!==typeof dpc_custom&&"function"===typeof dpc_custom.hook_ecommerce_add_trans)try{dpc_custom.hook_ecommerce_add_trans(a)}catch(c){dpc_core.track_error_event(c,"hook_ecommerce_add_trans: "+c.message)}for(b in dpc_conf.ga_account)dpc_conf.ga_account.hasOwnProperty(b)&&_gaq.push([b+"_addTrans",a.orderid,a.storename||"",a.total,a.tax||"",a.shipping||"",a.city||"",a.state||"",a.country||""])},add_item:function(a){var b;if("undefined"!==typeof dpc_custom&&"function"===
typeof dpc_custom.hook_ecommerce_add_item)try{dpc_custom.hook_ecommerce_add_item(a)}catch(c){dpc_core.track_error_event(c,"hook_ecommerce_add_item: "+c.message)}for(b in dpc_conf.ga_account)dpc_conf.ga_account.hasOwnProperty(b)&&_gaq.push([b+"_addItem",a.orderid,a.sku,a.productname||"",a.category||"",a.unitprice,a.quantity])},track_trans:function(){var a;try{"undefined"!==typeof dpc_custom&&"function"===typeof dpc_custom.hook_ecommerce_track_trans&&dpc_custom.hook_ecommerce_track_trans()}catch(b){dpc_core.track_error_event(b,
"hook_ecommerce_track_trans: "+b.message)}for(a in dpc_conf.ga_account)dpc_conf.ga_account.hasOwnProperty(a)&&_gaq.push([a+"_trackTrans"])},auto_meta:function(){var a,b=[],c=[];jQuery("meta[name^='DPC.ECOMMERCE.TRAN']").each(function(){var a=parseInt(this.name.substring(19),10);"object"!==typeof b[a]&&(b[a]={});b[a][this.name.substring(this.name.indexOf("]")+2).toLowerCase()]=this.content});if(0<b.length&&(jQuery("meta[name^='DPC.ECOMMERCE.ITEM']").each(function(){var a=parseInt(this.name.substring(19),
10);"object"!==typeof c[a]&&(c[a]={});c[a][this.name.substring(this.name.indexOf("]")+2).toLowerCase()]=this.content}),0<c.length)){for(a in b)b.hasOwnProperty(a)&&dpc_ecommerce.add_trans(b[a]);for(a in c)c.hasOwnProperty(a)&&dpc_ecommerce.add_item(c[a]);dpc_ecommerce.track_trans()}}},dpc_form={form_index:0,add_fields_events:function(a){var a=jQuery(a),b,c,d,e;if(a.data("form_taged"))return!1;dpc_form.form_index+=1;b=" ("+jQuery.trim(("string"===typeof a.attr("name")?a.attr("name"):void 0)||("string"===
typeof a.attr("id")?a.attr("id"):void 0)||String(dpc_form.form_index))+")";a.data("form_taged",!0);c=function(a,c){var d=jQuery(c),k="string"===typeof d.attr("type")?d.attr("type"):"",g="string"===typeof d.attr("id")?d.attr("id"):"",d="string"===typeof d.attr("name")?d.attr("name"):"",g=jQuery.trim(g&&jQuery('label[for="'+g+'"]').text()||d||g||k||"");if("radio"===k||"check"===k)g=d+": "+g;dpc_core.track_event("Form",dpc_core.sPathname+b,g+" ("+a.type+")",e)};d=a.find(":button, :reset, :submit, :image").bind("click",
function(a){c(a,this)});a.find(":input").not(d).focus(function(){e=(new Date).getTime()}).change(function(a){e=(new Date).getTime()-e;0>=e&&(e=null);c(a,this);e=0});a.find("label[for]").bind("click",function(){var a=jQuery.trim(jQuery(this).text()||jQuery(this).attr("for"))+" (click on label)";dpc_core.track_event("Form",dpc_core.sPathname+b,a)});return a.submit(function(a){c(a,this)})},track_all:function(a){var b,c,d;d=!1;for(b=0;b<a.length;b+=1)if(c=a[b],c instanceof RegExp){if(c.test(dpc_core.sUrl)){d=
!0;break}}else if("string"===typeof c&&0<=dpc_core.sUrl.indexOf(c)){d=!0;break}return!d?!1:jQuery("form").each(function(){dpc_form.add_fields_events(this)})}},dpc_scroll={lastX:0,lastY:0,get_max_scroll:function(){var a,b,c,d,e,f;a=jQuery(document).width();b=jQuery(document).height();c=jQuery(window).width();d=jQuery(window).height();e=jQuery(window).scrollLeft();f=jQuery(window).scrollTop();a=(c+e)/(a/100);b=(d+f)/(b/100);a>dpc_scroll.lastX&&(dpc_scroll.lastX=a);b>dpc_scroll.lastY&&(dpc_scroll.lastY=
b)},track_max_scroll:function(a){var b,c,d=!1;for(b=0;b<a.length;b+=1)if(c=a[b],c instanceof RegExp){if(c.test(dpc_core.sUrl)){d=!0;break}}else if("string"===typeof c&&0<=dpc_core.sUrl.indexOf(c)){d=!0;break}if(!d)return!1;dpc_scroll.get_max_scroll();a=10*Math.ceil(dpc_scroll.lastY/10);dpc_core.track_timing("MaxScroll-Y",dpc_core.sPathname,dpc_scroll.lastY);return dpc_core.track_event("MaxScroll-Y",dpc_core.sPathname,String(0===a?0:a-9+"-"+a),dpc_scroll.lastY,!0)}},dpc_util={log:function(a){"object"==
typeof console&&"function"==typeof console.log&&console.log(a)},is_valid_str:function(a){return"string"==typeof a&&!/^$|^\s+$/.test(a)}};
try{var ga_account_aux={},i;dpc_conf.tracker_name=[];"object"!==typeof dpc_conf.ga_account&&(dpc_conf.ga_account={"":dpc_conf.ga_account});if("string"!==typeof dpc_conf.gwo_namespace||0===dpc_conf.gwo_namespace.length)dpc_conf.gwo_namespace=!1;for(i in dpc_conf.ga_account)if(dpc_conf.ga_account.hasOwnProperty(i)){i!==dpc_conf.gwo_namespace&&dpc_conf.tracker_name.push(i);var j=0<i.length?i+".":i;ga_account_aux[j]=dpc_conf.ga_account[i]}dpc_conf.ga_account=ga_account_aux;for(i in dpc_conf.ga_account)dpc_conf.ga_account.hasOwnProperty(i)&&
dpc_core._setAccount(dpc_conf.ga_account[i],i);dpc_conf.gwo_namespace&&delete dpc_conf.ga_account[dpc_conf.gwo_namespace+"."];dpc_conf.enable_auto_pageview&&dpc_core.track_pageview(dpc_core.sPathname);if("function"===typeof jQuery)jQuery.extend(jQuery.expr[":"],{regex:function(a,b,c){return RegExp(c[3],"i").test(jQuery(a).text())},hrefregex:function(a,b,c){return!a.href?!1:RegExp(c[3],"i").test(a.href)},actionregex:function(a,b,c){return!a.action?!1:RegExp(c[3],"i").test(a.action)},external:function(a){var b=
String(a.href).toLowerCase();return!a.href||0===b.indexOf("javascript:")||0===b.indexOf("mailto:")?!1:a.hostname&&a.hostname!==document.location.hostname}});else throw Error("jQuery not available");"undefined"!==typeof dpc_custom&&"function"===typeof dpc_custom.hook_asap&&_gaq.push(function(){try{dpc_custom.hook_asap(dpc_core.sUrl,dpc_core.sReferrer)}catch(a){dpc_core.track_error_event(a,"hook_asap: "+a.message)}});var TimeTracker=window.TimeTracker=function(a,b){this.bucket_=b?b.sort(this.sortNumber):
TimeTracker.DEFAULT_BUCKET;this.unity_=a?a:1};TimeTracker.prototype.startTime_=null;TimeTracker.prototype.stopTime_=null;TimeTracker.prototype.bucket_=null;TimeTracker.DEFAULT_BUCKET=[100,500,1500,2500,5E3,7500,1E4,15E3,2E4,3E4];TimeTracker.prototype._getTimeDiff=function(){return Math.round((this.stopTime_-this.startTime_)/this.unity_)};TimeTracker.prototype.sortNumber=function(a,b){return a-b};TimeTracker.prototype._recordStartTime=function(a){this.startTime_=void 0!==a?a:(new Date).getTime()};
TimeTracker.prototype._recordEndTime=function(a){this.stopTime_=void 0!==a?a:(new Date).getTime()};TimeTracker.prototype._track=function(a,b,c,d){var e,f;if(0>=this._getTimeDiff()||this._getTimeDiff()>18E5/this.unity_)return!1;for(e=0;e<this.bucket_.length;e++)if(this._getTimeDiff()<this.bucket_[e]){f=0===e?"0-"+this.bucket_[0]:this.bucket_[e-1]+"-"+(this.bucket_[e]-1);break}f||(f=this.bucket_[e-1]+"+");dpc_core.track_timing(b||"TimeTracker",c,this._getTimeDiff());return a._trackEvent(b||"TimeTracker",
f,c,this._getTimeDiff(),!!d)};TimeTracker.prototype._setHistogramBuckets=function(a){this.bucket_=a.sort(this.sortNumber)};if("function"===typeof TimeTracker){if(dpc_conf.track_time_on_page){var timeOnPage=new TimeTracker(1E3);timeOnPage._setHistogramBuckets([1,2,5,10,20,30,40,50,60,120,180,300,600,900,1200,1500]);timeOnPage._recordStartTime();jQuery(window).unload(function(){""!==dpc_cookie.get("__utmb")&&_gaq.push(function(){var a,b;timeOnPage._recordEndTime();for(a=0;a<dpc_conf.tracker_name.length;a++)b=
_gat._getTrackerByName(dpc_conf.tracker_name[a]),timeOnPage._track(b,"Time on Page(s)",dpc_core.sPathname,!!dpc_conf.enable_non_interaction_time_on_page)})})}if(dpc_conf.track_time_to_load){var timeToLoad=new TimeTracker(1);timeToLoad._recordStartTime();jQuery(window).load(function(){_gaq.push(function(){var a,b;timeToLoad._recordEndTime();for(a=0;a<dpc_conf.tracker_name.length;a++)b=_gat._getTrackerByName(dpc_conf.tracker_name[a]),timeToLoad._track(b,"Time to Load(ms)",dpc_core.sPathname,!0)})})}}jQuery(document).ready(function(){dpc_conf.enable_meta_ecommerce&&
"object"===typeof dpc_ecommerce&&_gaq.push(function(){dpc_ecommerce.auto_meta()});dpc_conf.enable_meta_cluster&&"object"===typeof dpc_cookie&&_gaq.push(function(){dpc_cookie.auto_meta_cluster()});"object"===typeof dpc_form&&_gaq.push(function(){dpc_form.track_all(dpc_conf.tag_form_location)});_gaq.push(function(){dpc_core.add_cookie_to_href(dpc_conf.cookie_copy_href);dpc_core.add_special_pageviews(dpc_conf.link_track_pageview);dpc_core.add_special_events(dpc_conf.link_track_event)});"undefined"!==
typeof dpc_custom&&"function"===typeof dpc_custom.hook_onload&&_gaq.push(function(){try{dpc_custom.hook_onload(dpc_core.sUrl,dpc_core.sReferrer)}catch(a){dpc_core.track_error_event(a,"hook_onload: "+a.message)}})});jQuery(window).unload(function(){""!==dpc_cookie.get("__utmb")&&("object"===typeof dpc_scroll&&_gaq.push(function(){dpc_scroll.track_max_scroll(dpc_conf.max_scroll_location)}),"undefined"!==typeof dpc_custom&&"function"===typeof dpc_custom.hook_onunload&&_gaq.push(function(){try{dpc_custom.hook_onunload(dpc_core.sUrl,
dpc_core.sReferrer)}catch(a){dpc_core.track_error_event(a,"hook_onunload: "+a.message)}}))});"object"===typeof dpc_scroll&&jQuery(window).scroll(function(){dpc_scroll.get_max_scroll()});dpc_conf.enable_window_track_error&&"function"!==typeof window.onerror&&(window.onerror=function(a,b,c){try{return dpc_core.track_event("JS Exception Error",a,b+" ("+c+")",0,!0),"undefined"!==typeof console&&"undefined"!==typeof console.error&&console.error("JS Exception Error:",a,b+" ("+c+")"),!dpc_conf.enable_js_error_alert}catch(d){return dpc_core.track_error_event(d,
"window_track_error: "+d.message),!dpc_conf.enable_js_error_alert}})}catch(exception$$1){dpc_core.track_error_event(exception$$1)}(function(){var a=document.createElement("script");a.type="text/javascript";a.async=!0;a.src=("https:"===document.location.protocol?"https://ssl":"http://www")+".google-analytics.com/ga.js";var b=document.getElementsByTagName("script")[0];b.parentNode.insertBefore(a,b)})();