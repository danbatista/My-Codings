
/*
//  Created by Leo Carvalho on 2012-10-02.
//  Copyright (c) 2012 __leo.carvalho__. All rights reserved.
//

 gerenciador de mensagem 
 */



$(document).ready(function(){
		
    trataBotao();
   
	$('.submit, .reset').live('mouseenter', function () {
		$(this).addClass('bthover');
	}).live('mouseleave', function () {
	  $(this).removeClass('bthover');
	});
      
    $('input[type="radio"]').addClass('iptRadio');
    
    $('input[type="checkbox"]').addClass("inputCheckBox");
   
    $("form input[inputInfo]").focus(function(){
        $(this).parent().find("div.inputInfo").css({
            left: $(this).width() + $(this).position().left + 25
        }).animate({
            opacity: "show",
            left: $(this).width() + $(this).position().left + 15
        }, "slow");
    }).blur(function(){
        $(this).parent().find("div.inputInfo").animate({
            opacity: "hide",
            left: $(this).width() + $(this).position().left + 45
        }, "fast");
    });   

});

function trataBotao() {

    $('.button').append('<span />');

}
//leo.carvalho
function atribuiFancyboxes(){
  if($.fancybox){
    $("table.oiAgenda .oiAgenda-excluir").fancybox({
			'data': $('#agenda-excluirContato').html(),
			'scrolling': 'no',
			'autoDimensions': true,
			'centerOnScroll': true,
			'titleShow': false
		});
    $("table.oiAgenda .oiAgenda-salvar").fancybox({
			'data': $('#agenda-incluirContato').html(),
			'scrolling': 'no',
			'autoDimensions': true,
			'centerOnScroll': true,
			'titleShow': false
		});
    $("p.oiAgenda a.button").fancybox({
			'scrolling': 'no',
			'padding': 15,
			'autoDimensions': true,
			'centerOnScroll': true,
			'titleShow': false
		});
		
		 $("#crieSeuLogin a.excluir").fancybox({
			'scrolling': 'no',
			'padding': 15,
			'autoDimensions': true,
			'centerOnScroll': true,
			'titleShow': false
		});
		
    $('#fancybox-content .closeFancyBox').live('click', function(){
			if($.fancybox) $.fancybox.close();
			return false;
		})
    $(".sweetForm .modals").fancybox({
			'scrolling': 'no',
			'autoDimensions': true,
			'padding': 30,
			'titleShow': false,
			'centerOnScroll': true,
			'showCloseButton': false
		});
    $(".sweetForm .submitModal").each(function(){
			if($.fancybox) $(this).fancybox({
				'scrolling': 'no',
				'autoDimensions': true,
				'padding': 30,
				'titleShow': false,
				'showCloseButton': false,
				'type': 'inline',
				'centerOnScroll': true,
				'content': $(this).attr('title')
			});
    });
  }
}

function fecharfancy() {
    if($.fancybox) $.fancybox.close()
}

