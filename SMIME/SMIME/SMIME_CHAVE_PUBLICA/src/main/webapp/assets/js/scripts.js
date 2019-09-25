var myVar;

function myFunction() {
    myVar = setTimeout(showPage, 700);
}

function showPage() {
  document.getElementById("loader").style.display = "none";
  document.getElementById("myDiv").style.display = "block";
}

jQuery(document).ready(function() {
  //imagem de fundo
  $.backstretch("assets/img/backgrounds/1.jpg");

  $('#top-navbar-1').on('shown.bs.collapse', function(){
    $.backstretch("resize");
  });
  $('#top-navbar-1').on('hidden.bs.collapse', function(){
    $.backstretch("resize");
  });


  //melhoria na exibição do nome do arquivo após o usuário inserir o arquivo errado
  $(document).on('change', function(){
	  if($('div[class="kv-fileinput-error file-error-message"]').is(':visible')){
      var texto_div = $('div[class="kv-fileinput-error file-error-message"]').text();
      if(texto_div.length > 40){
        var aux = texto_div;
        var text = aux.split('"');
        var arquivo = text[1];

        arquivo = text[0].substring(1, text[0].length - 1) + ' ' +
                  ' "' + arquivo.substring(0, 10) + '...' +
                  arquivo.substring(arquivo.length - 11, arquivo.length) + '" ' +
                  ' ' + text[2] + ' "' + text[3] + '" ' + text[4];

        $('div[class="kv-fileinput-error file-error-message"]').text(arquivo);
      }
	  }
  });
});

$('#continuar').on('click', function(){

  var certificado = $('#input_certificado').val();
  var senha = $('#input_senha').val();

  if(certificado && senha){
	enviar();
  }else{
	  $('#erro').removeClass('apagado');
	  $('#div_certificado').addClass("has-error has-feedback");
	  $('#div_senha').addClass("has-error has-feedback");
  }
});

$('#div_certificado').on('click', function(){
	$('#div_certificado').removeClass("has-error has-feedback");
});

$('#input_senha').on('click', function(){
	$('#div_senha').removeClass("has-error has-feedback");
});

function enviar(){
	var formdata = new FormData();

	formdata.append('password', $('#input_senha').val());
	formdata.append('file', $('#input_certificado')[0].files[0]);

    $.ajax({
		type : "POST",
		crossDomain : false,
	    url : 'http://localhost:8080/SMIME_CHAVE_PUBLICA/rest_pub/service/upload',
		//url : 'http://localhost:8081/SMIME_CHAVE_PUBLICA/rest/service/upload',		
		data : formdata,
		contentType: false,
		processData: false,
		beforeSend : function() {
			$("#wait").modal('show');
		},
		success : function(data) {
			$('.close').trigger('click');
			if(data === '100'){
				$('#construcao').addClass('animated bounceOutLeft');
				$('#construcao').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
					$('#construcao').removeClass('animated bounceOutLeft');
					$('#construcao').addClass('apagado');
					$('#fim').removeClass('apagado');
					$('#fim').addClass('animated bounceInRight');
					$('#fim').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
						$('#fim').removeClass('animated bounceInRight');
					});
				});
			}else if(data == '101'){
				$('#erro101').modal('show');
			}else if(data == '102'){
				$('#erro102').modal('show');
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$('.close').trigger('click');
			$('div.modal.fade#erro').modal('show');
			console.log(textStatus);
			console.log(XMLHttpRequest);
			console.log(errorThrown);
		}
	});
};
