/*  USTORE - 2016
*
*   DANIEL BATISTA - daniel@usto.re
*  #uMailTeam
*/

//Difines a URL from domain
var HOST_ZIMBRA_IMAP = "10.0.1.194"; // Este é inserido logo depois que a página é carregada
var HOST_ZIMBRA_MTA = "10.0.1.194"; // Se for um ambiente com toda instalação em uma máquina, coloque o ip dá máquina
var HOST_SERVICE = "https://10.0.1.194:7777/";
var HOST_SERVICE_PUBLIC = "https://10.0.80.145:7777";
var ct = 0;
function zm_usto_re_smime (){
	
	
   var oHead = document.getElementsByTagName('HEAD').item(0);

//FIle 1
   var link= document.createElement("link");
   link.rel="stylesheet";
   link.href="/service/zimlet/zm_usto_re_smime/jquery.fancybox.css";
// FIle 2 
   var oScript= document.createElement("script");
   oScript.type = "text/javascript";
   oScript.src="/service/zimlet/zm_usto_re_smime/jquery.fancybox.js";

 // Bootstrap

   var boot= document.createElement("link");
   boot.rel="stylesheet";
   boot.href="/service/zimlet/zm_usto_re_smime/bootstrap/css/bootstrap.min.css";
   
   var boot2= document.createElement("link");
   boot2.rel="stylesheet";
   boot2.href="/service/zimlet/zm_usto_re_smime/bootstrap/css/bootstrap-theme.min.css";

      var bootjs= document.createElement("script");
      bootjs.type = "text/javascript";
      bootjs.src="/service/zimlet/zm_usto_re_smime/bootstrap/js/bootstrap.min.js";

  // Bootstrap File input

   var bootf = document.createElement("script");
      bootf.type = "text/javascript";
      bootf.src="/service/zimlet/zm_usto_re_smime/bootstrap-fileinput/js/fileinput.js";
  
     var bootfcss= document.createElement("link");
   bootfcss.rel="stylesheet";
   bootfcss.href="/service/zimlet/zm_usto_re_smime/bootstrap-fileinput/css/fileinput.min.css";
    
   oHead.appendChild( link);
   oHead.appendChild( oScript);
   oHead.appendChild(boot);
   oHead.appendChild(boot2);
   oHead.appendChild(bootjs);
   oHead.appendChild(bootfcss);
   //oHead.appendChild(bootf);



}

zm_usto_re_smime.prototype = new ZmZimletBase();
zm_usto_re_smime.constructor = zm_usto_re_smime;

var SMIME = zm_usto_re_smime;
var fileBinary;
var password_cert;
var verificaStorage = false;
var msg_composer;
var IS_SMIME = true;
var modal_cert = false;
var fileName;

var pref_store_cert = false; 
//var fileCert = [];
//var labelCert = [];	


SMIME.addModalPanel = function(){

        $(document).ready(function(){
  if($(".DwtShell #modal-panel").length == 0){

  // Modal com Fancybox	
   $(".DwtShell").append('<div class="window" id="modal-panel"  style="display:none;">');
    $(".DwtShell .window").append('<h2>S/MIME Preferências</h2>');
    $(".DwtShell .window").append('<label for="btn">Cadastro de Chaves Públicas</label></br>');
    $(".DwtShell .window").append('<input class ="btn btn-default" onclick="window.open(&quot;'+HOST_SERVICE_PUBLIC+'/SMIME_CHAVE_PUBLICA&quot;);" type="submit" id="btn" value="cadastrar">');
     $(".DwtShell .window").append('<h3>Selecione seu certificado(PKCS #12)</h3>');
    $(".DwtShell .window").append('<div class="modal-container caixa">');
	$(".DwtShell .window .caixa").append("<div class='input-group'><label class='input-group-btn'><span class='btn btn-primary'>Procurar<input type='file' id='fileCert' style='display: none;'></span></label><input id='labeloffile' class='form-control' disabled type='text'></div>");
	$(".DwtShell .window .caixa").append('<div id="dforpass" class="form-group"><label for="password">Senha:<input type="password" class="form-control" id="password" placeholder="Digite a senha"></label></div>');
	$(".DwtShell .window .caixa").append('<br />');
	$(".DwtShell .window .caixa").append('<input class ="btn btn-default" type="submit" value="enviar" id="send">');
	$(".DwtShell .window .caixa").append('<input class ="btn btn-default" type="submit" value="cancelar" id="cancel">');
	$(".DwtShell .window .caixa").append('<a href="javascript:void(0);"><span class="close-modal"></span></a>');
	$(".DwtShell .window").append('</div>');

   // Modal com Bootstrap
     $(".DwtShell").append('<div class="modal fade" id="modalCert" role="dialog">');
     $(".DwtShell #modalCert").append('<div class="modal-dialog modal-sm">');
     $(".DwtShell #modalCert .modal-dialog").append('<div class="modal-content" style="height: 0px; width: 0px;">');
//$(".DwtShell #modalCert .modal-dialog .modal-content").append('<a type="button" style="margin-right: 10px; margin-top: 5px" class="close" data-dismiss="modal">&times;</a>');
	$(".DwtShell #modalCert .modal-dialog .modal-content").append('<br>');
 //$(".DwtShell #modalCert .modal-dialog .modal-content").append('<h4 class="modal-title"><i class="fa fa-exclamation-triangle fa-1x" aria-hidden="true"></i> Erro ao submeter o certificado!</h4>');
  $(".DwtShell #modalCert .modal-dialog .modal-content").append('<div class="modal-body">');
  $(".DwtShell #modalCert .modal-dialog .modal-content .modal-body").append('<span><img class="smime" src="/service/zimlet/zm_usto_re_smime/bandeira.gif"></span>');
  $(".DwtShell #modalCert .modal-dialog .modal-content").append('</div></div></div></div>');
                
}else{
	$(".DwtShell #modal-panel").remove();
	$(".DwtShell #modalCert").remove();
	SMIME.addModalPanel();
}
});

}

SMIME.prototype.singleClicked = function(){
	   SMIME.addModalPanel();
	   $.fancybox($('#modal-panel').html());
	      SMIME.addModalCerts();
	     
}

SMIME.prototype.doubleClicked = function(){
	  SMIME.addModalPanel();
      $.fancybox($('#modal-panel').html());
	      SMIME.addModalCerts(); 
}

//var controllerClick;

SMIME.processClicks = function(controller){
   
		$("#encrypt").click(function() {
		SMIME.sendSignedAndEncryptedMail(controller);
	});

	$("#signed").click(function() {
		SMIME.sendSignedMail(controller);
	});
}


SMIME.prototype.init = function() {
  //HOST_ZIMBRA_IMAP = SMIME_REMOTE_ADDR;

};
/*
SMIME.prototype.initializeToolbar = function(app, toolbar, controller, viewId) {

	toolbar.enable(ZmOperation.DETACH_COMPOSE, false);


	if (viewId.indexOf("COMPOSE") >= 0) {
		if (toolbar.getButton('encrypt')) {
			// button already defined
			return;
		}
  
		var buttonArgs = {
			text : "Enviar Assinado e encriptado",
			tooltip : "Assinar e encriptar mensagem",
			index : 2, // position of the button
			image : "zm_usto_re_smime-panelIcon" // icon
		};
		var button = toolbar.createOp("encrypt", buttonArgs);

		var buttonArgs2 = {
			text : "Enviar Assinado",
			tooltip : "Assinar mensagem",
			index : 3, // position of the button
			image : "zm_usto_re_smime-panelIcon2" // icon
		};
		var button2 = toolbar.createOp("signed", buttonArgs2);

		$("#zb__COMPOSE-1__encrypt_left_icon").removeClass("ZLeftIcon");
		$("#zb__COMPOSE-1__encrypt_left_icon").removeClass("ZWidgetIcon");
		$("#zb__COMPOSE-1__encrypt_left_icon").addClass(
				"Imgzm_usto_re_smime-panelIcon");

		$("#zb__COMPOSE-1__signed_left_icon").removeClass("ZLeftIcon");
		$("#zb__COMPOSE-1__signed_left_icon").removeClass("ZWidgetIcon");
		$("#zb__COMPOSE-1__signed_left_icon").addClass(
				"Imgzm_usto_re_smime-panelIcon2");
	//SMIME.processClicks(controller);
	}
	// controllerClick = controller;
    //console.log("ainda passa aqui");

};
*/

SMIME.getHTML= function(who, deep){
    if(!who || !who.tagName) return '';
    var txt, ax, el= document.createElement("div");
    el.appendChild(who.cloneNode(false));
    txt= el.innerHTML;
    if(deep){
        ax= txt.indexOf('>')+1;
        txt= txt.substring(0, ax)+who.innerHTML+ txt.substring(ax);
    }
    el= null;
    return txt;
}

SMIME.sendSignedAndEncryptedMail = function (controle){
		// IS_SMIME = !IS_SMIME;

		

		if(window.top.modal_cert == false){
		SMIME.popup("Voc&ecirc; deve selecionar um certificado primeiro!",3);
		return;
	}

	 var composeMode = appCtxt.getCurrentView().getHtmlEditor().getMode();

	 if(composeMode != 'text/plain')
   {
      SMIME.popup("Por favor, utilize texto simples(Op&ccedil;&otilde;es -> Formatar como texto simples).",3);
		return;
   }
	 
	msg_composer = controle._composeView.getMsg();
	

	  if(msg_composer.textBodyContent == null ){
	 	  SMIME.popup("Por favor, preencha o campo de conte&uacute;do da mensagem.",3);
	 	 return;
	 }else if(msg_composer.subject == null){
	 	  SMIME.popup("Por favor, preencha o campo de titulo da mensagem.",3);
	 	 return;
	 }else if (msg_composer._addrs.TO.toString() == null || msg_composer._addrs.TO._array.length != 1){
      SMIME.popup("Por favor, preencha o campo do destinat&aacute;rio da mensagem ou verifique a quantidade de destinat&aacute;rios.",3);
	 	 return;
	 }else if (window.top.appCtxt.accountList.mainAccount.getEmail() == null){
	   SMIME.popup("Ocorreu um erro ao buscar alguma informa&ccedil;&atilde;o, por favor, atualize a p&aacute;gina e tente novamente.", 1);
	 	 return;
	 }else if (msg_composer._addrs.CC.getArray.length > 0 || msg_composer._addrs.CC._array.length != 0){
	 	  SMIME.popup("Para envio de mensagens seguras, n&atilde;o &eacute; poss&iacute;vel a c&oacute;pia de mensagens. Por favor, retire o campo CC.",3);
	 	 return;
	 }else if(typeof msg_composer._addrs.TO.toString().split("<")[1] !== "undefined" && window.top.appCtxt.accountList.mainAccount.getEmail() == msg_composer._addrs.TO.toString().split("<")[1].split(">")[0]){
	 	SMIME.popup("Voc&ecirc; n&atilde;o pode enviar e-mail para a mesma conta a qual est&aacute; logado.", 3);
	 	return;
	 }else if(window.top.appCtxt.accountList.mainAccount.getEmail() == msg_composer._addrs.TO.toString()){
        SMIME.popup("Voc&ecirc; n&atilde;o pode enviar e-mail para a mesma conta a qual est&aacute; logado.", 3);
	 	return;
	 }

	 var listFiles = [];
	 //var labelAttachments = [];
	 var contFiles = false;	
     window.$(".attBubbleHolder span a").each(function(){
        //listFiles.push($(this).attr("href"));
        //labelAttachments.push($(this).html());
        contFiles = true; window.top.contFiles = true; window.contFiles = true;
        if(contFiles == true){
        	SMIME.popup("Apenas anexos do EBDrive s&atilde;o Permitidos",3);
        	return;
        }
        
     });


   $('body div div div div div div iframe').contents().find('body div').each(function(){
     	listFiles.push(SMIME.getHTML(this,true));
    });
  
     listFiles.shift();
  var encryptModel = {
		"id" : HOST_SERVICE_PUBLIC,
		"msg" : null,
		"listebdrive": null,
		"getFrom" : window.top.appCtxt.accountList.mainAccount.getEmail(),
		"content" : msg_composer.textBodyContent,
		"to" : msg_composer._addrs.TO._array[0].address,
		"subject" : msg_composer.subject,
		"file" : listFiles,
		"host" : HOST_ZIMBRA_MTA,
		"sessiondrive" : window.top.sessiondrive.value,
		"authdrive" : window.top.authdrive.value,
		"label" : window.top.fCert.name,
		"passCert" : window.top.password_cert,
		"labelAttachments": null
	};
	 
ct = 0;
       if(contFiles == false){
			$.ajax({
				type : "POST",
				crossDomain : true,
				url : HOST_SERVICE + 'restful-smime/encrypt',
				//dataType : "text/plain",
				crossDomain : true,
				data : JSON.stringify(encryptModel),
				contentType : "application/json",
				beforeSend : function() {
					     window.top.callModalSend(); 
						
				},
				success : function(data) {
					window.top.$(".modal-backdrop").trigger('click');
					if(data == "100"){
						window.top.aviso("Seu e-mail foi enviado com sucesso!");
					closeModalFromSendMessage();
				}else if(data == "101"){
					SMIME.popup("Não foi possível processar sua mensagem, verfique se você atendeu a todos os"
						+"requisitos listados na documentação do S/MIME",2);
				}else if (data == "102"){
					SMIME.popup("Este destinatário não se encontra no serviço de chaves públicas",3);
				}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
		window.top.$(".modal-backdrop").trigger('click');
		SMIME.popup("Ocorreu um erro enquanto sua mensagem era enviada, tente novamente mais tarde!",1);
					console.log(textStatus);
					console.log(XMLHttpRequest);
					console.log(errorThrown);
				}
			});
				}
};


SMIME.sendSignedMail = function (controller){
		// IS_SMIME = !IS_SMIME;
   var id;
   if(pref_store_cert == true){
        id = 1;
        modal_cert = true;
    }
			if(window.top.modal_cert == false){
		SMIME.popup("Voc&ecirc; deve selecionar um certificado primeiro!",3);
		return;
	}

	 var composeMode = appCtxt.getCurrentView().getHtmlEditor().getMode();

	 if(composeMode != 'text/plain')
   {
      SMIME.popup("Por favor, utilize texto simples(Op&ccedil;&otilde;es -> Formatar como texto simples).",3);
		return;
   }
	 
	 
	msg_composer =controller._composeView.getMsg();

	 if(msg_composer.textBodyContent == null ){
	 	  SMIME.popup("Por favor, preencha o campo de conte&uacute;do da mensagem.",3);
	 	 return;
	 }else if(msg_composer.subject == null){
	 	  SMIME.popup("Por favor, preencha o campo de titulo da mensagem.",3);
	 	 return;
	 }else if (msg_composer._addrs.TO.toString() == null || msg_composer._addrs.TO._array.length != 1){
      SMIME.popup("Por favor, preencha o campo do destinat&aacute;rio da mensagem ou verifique a quantidade de destinat&aacute;rios.",3);
	 	 return;
	 }else if (window.top.appCtxt.accountList.mainAccount.getEmail() == null){
	   SMIME.popup("Ocorreu um erro ao buscar alguma informa&ccedil;&atilde;o, por favor, atualize a p&aacute;gina e tente novamente.", 1);
	 	 return;
	 }else if (msg_composer._addrs.CC.getArray.length > 0 || msg_composer._addrs.CC._array.length != 0){
	 	  SMIME.popup("Para envio de mensagens seguras, n&atilde;o &eacute; poss&iacute;vel a c&oacute;pia de mensagens. Por favor, retire o campo CC.",3);
	 	 return;
	 }else if(typeof msg_composer._addrs.TO.toString().split("<")[1] !== "undefined" && window.top.appCtxt.accountList.mainAccount.getEmail() == msg_composer._addrs.TO.toString().split("<")[1].split(">")[0]){
	 	SMIME.popup("Voc&ecirc; n&atilde;o pode enviar e-mail para a mesma conta a qual est&aacute; logado.", 3);
	 	return;
	 }else if(window.top.appCtxt.accountList.mainAccount.getEmail() == msg_composer._addrs.TO.toString()){
        SMIME.popup("Voc&ecirc; n&atilde;o pode enviar e-mail para a mesma conta a qual est&aacute; logado.", 3);
	 	return;
	 }

	 	 var listFiles = [];
	 var labelAttachments = [];
	 var listebdrive = [];	
     $(".attBubbleHolder span a").each(function(){
        listFiles.push($(this).attr("href"));
        labelAttachments.push($(this).html());
     });

      $('body div div div div div div iframe').contents().find('body div').each(function(){
     	listebdrive.push(SMIME.getHTML(this,true));
    });
      listebdrive.shift();
     
 var encryptModel = {
		"id" : id,
		"msg" : null,
		"listebdrive": listebdrive,
		"getFrom" : window.top.appCtxt.accountList.mainAccount.getEmail(),
		"content" : msg_composer.textBodyContent,
		"to" : msg_composer._addrs.TO.toString(),
		"subject" : msg_composer.subject,
		"file" : listFiles,
		"host" : HOST_ZIMBRA_MTA,
		"sessiondrive" : window.top.sessiondrive.value,
		"authdrive" : window.top.authdrive.value,
		"label" : window.top.fCert.name,
		"passCert" : window.top.password_cert,
		"labelAttachments": labelAttachments
	};

		ct = 0; 
			$.ajax({
				type : "POST",
				crossDomain : true,
				url : HOST_SERVICE + 'restful-smime/sign',
				//dataType : "xml",
				crossDomain : true,
				data : JSON.stringify(encryptModel),
				contentType : "application/json",
				beforeSend : function() {
					        window.top.callModalSend(); 
							
				},
				success : function(data) {
					window.top.$(".modal-backdrop").trigger('click');
					window.top.aviso("Seu e-mail foi enviado com sucesso!");
					closeModalFromSendMessage();
                       

				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					window.top.$(".modal-backdrop").trigger('click');
					SMIME.popup("Ocorreu um erro enquanto sua mensagem era enviada, tente novamente mais tarde!",1);
					console.log(textStatus);
					console.log(XMLHttpRequest);
					console.log(errorThrown);
				}
			});
						

}

SMIME._noBtnListener = function(){
	 $(".fancybox-overlay .caixa .input-group input").val("");
	 $(".fancybox-close").trigger("click");
}

  SMIME.addModalCerts = function(){
  $(document).ready(function (){
  	//altera comportamento do ENTER na tela do modal
    document.querySelector('body .fancybox-overlay').addEventListener('keydown', function(event) {
    if(event.keyCode == 13){
    	 $(".fancybox-overlay #send").trigger('click');
    event.stopPropagation? event.stopPropagation() : event.cancelBubble = true;
   return false;
}
});
		document.querySelector('.fancybox-overlay #fileCert').addEventListener('change', function() {
		fileName = $(".fancybox-overlay #fileCert").val();
		// console.log(label);
		var reader = new FileReader();
		reader.onload = function() {
			fileBinary = this.result;
			console.log("Binary" + fileBinary);
			     $("#labeloffile.form-control").attr("value",fileName);
		}
		reader.readAsBinaryString(this.files[0]);


	}, false);

		$(".fancybox-overlay #send").on('click', function(){

			 //if($(".fancybox-overlay #check").attr("checked")=="checked")
               // pref_store_cert=true;

         SMIME.getData($(".fancybox-overlay .caixa #password"));
		});

		$(".fancybox-overlay #cancel").on('click', function(){
            SMIME._noBtnListener();
		});
});

};
var fCert;
SMIME.getData = function (pass){

var gArray = fileName.split('.');
var cnfm = false;
  for (u in gArray){
   if(gArray[u] == "pfx" || gArray[u] == "p12") cnfm = true;
  }	
  if(cnfm == false){
  	if($(".fancybox-overlay .modal-container.caixa p.error").length == 0)
  $(".fancybox-overlay .modal-container.caixa").prepend("<p class='error'>Você deve selecionar um aquivo no formato PKCS12 (.p12 ou .pfx)</p>");
  	 return;
  }else{
  	$(".fancybox-overlay .modal-container.caixa p.error").remove();
  }

 password_cert = $(pass).val();

   if(password_cert.length  == 0){
   	if(password_cert.length  == 0 && fileBinary == null){
   		return;
   	  }else
   $(".fancybox-overlay .modal-container.caixa").prepend("<p class='error'>Por favor, insira a senha do certificado</p>"); 
    	return;
   }

	var formData = new FormData();
	fCert = $(".fancybox-overlay #fileCert")[0].files[0];
	formData.append("file", fCert);
	formData.append("password", password_cert);
	formData.append("account" , appCtxt.accountList.mainAccount.getEmail());
	$.ajax({
		type : "POST",
		crossDomain : true,
		url : HOST_SERVICE + 'USTO_CERT_STORE/rest/file/upload',
		contentType : false,
		processData : false,
		data : formData,

		beforeSend : function() {
			console.log("init...");
		},
		success : function(data) {
			
			if(data == "C101"){
		SMIME._noBtnListener();
        SMIME.popup("Certificado inválido para essa conta. Selecione outro!",1);
      }else if(data == "C100"){
      	SMIME._noBtnListener();
        SMIME.popup("Certificado válidado com sucesso!",2);        	
	        modal_cert = true;
      }else if(data == "C102"){
      	      SMIME._noBtnListener();
			  SMIME.popup("Senha de desbloqueio incorreta!",3);
      }else{
      	SMIME._noBtnListener();
        SMIME.popup("Ocorreu um erro desconhecido, tente novamente. Causas prováveis: </br>"+
        	"- O servidor não está respondendo </br>"+
        	"- O arquivo que você selecionou não é um certificado válido(PKCS #12)",1);
         }
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			 SMIME._noBtnListener();
             SMIME.popup("Ocorreu um erro enquanto abriamos seu certificado. Causas prováveis: </br>"+
        	"- Você não adicionou a exce&ccedil;&atilde;o ao servidor S/MIME, <a target='blank' href='https://"+HOST_ZIMBRA_IMAP+":7777/restful-smime/service'>clique aqui</a> e adicione agora.</br>"+
        	"- O servidor SMIME está inacessível. Neste caso, contate o suporte!",1);
			console.log(textStatus);
			console.log(XMLHttpRequest);
			console.log(errorThrown);
		}
	});

}


SMIME.prototype.onMsgView = function(msg, oldMsg, msgView) {

var resultado="";
 if($("#content"))$("#content").remove();
ct = 0;
for (propriedade in msg._contentType) {
  resultado += propriedade + ": " + msg._contentType[propriedade] + "\n";
   console.log(resultado);
    if(resultado.trim() == "application/pkcs7-mime: true"){
    
   SMIME._renderMessageInfo(msg, msgView, 1);
}else if(resultado.trim() == "multipart/signed: true"){
	
      SMIME._renderMessageInfo(msg, msgView, 2);
  }
}

};

SMIME.prototype.onMsgExpansion = function(msg, msgView) {
	
	var resultado="";
ct = 0;


  if($(".Conv2MsgHeader ~ .attachments").find("table tbody tr td div[id^='anxs']")){ 
   $(".Conv2MsgHeader ~ .attachments").find("table tbody tr td div[id^='anxs']").each(function(){
	$(this).parent().parent().remove();
});
}
 if($("#content"))$("#content").remove();
  
for (propriedade in msg._contentType) {
  resultado += propriedade + ": " + msg._contentType[propriedade] + "\n";
   console.log(resultado);
    if(resultado.trim() == "application/pkcs7-mime: true"){
   SMIME._renderMessageInfo(msg, msgView, 1);
}else
 if(resultado.trim() == "multipart/signed: true"){
      SMIME._renderMessageInfo(msg, msgView, 2);
  }
}


};

SMIME.prototype.onConvView = function(msg, oldMsg, convView) {
var resultado="";
ct = 0;
 //if($("#content"))$("#content").remove();
console.log("FOI CHAMADO ONCONVVIEW");
if($(".attachments table tbody tr"))

$(".attachments table tbody tr").each(function(){$(this).remove();});

 if($("#content"))$("#content").remove();
 

for (propriedade in msg._contentType) {
  resultado += propriedade + ": " + msg._contentType[propriedade] + "\n";
   console.log(resultado);
    if(resultado.trim() == "application/pkcs7-mime: true"){
   SMIME._renderMessageInfo(msg, msgView, 1);
}else
 if(resultado.trim() == "multipart/signed: true"){
      SMIME._renderMessageInfo(msg, msgView, 2);
  }
}


};

var tt, dd; 
SMIME._renderMessageInfo = function(msg, view, controle) {

 
			if(modal_cert == false){
		SMIME.popup("Voc&ecirc; deve selecionar um certificado primeiro!",3);
		return;
	}
   
     tt = view;
     dd = msg.id;

		var out = [];

		var encryptModel = {"id" : dd, 
		                    "msg" : null, 
		                    "host" :  window.top.HOST_ZIMBRA_IMAP, 
		                    "passCert": password_cert, 
		                    "label" : fCert.name,
		                    "account":  appCtxt.accountList.mainAccount.getEmail(),
		                    "passwd" :SMIME.getItemStorage("pz",0)
		                 };
	
	 
						$.ajax({
						type : "POST",
						crossDomain: true,
						url : HOST_SERVICE+'restful-smime/read',
						dataType : "json",
						crossDomain : true,
						data : JSON.stringify(encryptModel),
						contentType : "application/json",
										beforeSend : function() {
											console.log("init...");
								callModalSend();
								
			                          
										},
						success : function(data) {
						$(".modal-backdrop").trigger('click');
						SMIME._noBtnListener();
							// var obj = JSON.parse(data);
						console.log("Mensagem decriptada");
						if(controle == 1)SMIME._renderSMIMEInfo(data, view);
						if(controle == 2)SMIME._renderSignedInfo(data, view);
    				},
						error : function(XMLHttpRequest, textStatus, errorThrown) {
						$(".modal-backdrop").trigger('click');
						SMIME._noBtnListener();
							  SMIME.popup("Ocorreu um erro enquanto sua mensagem era lida, atualize a página!",1);
							console.log(textStatus);
							console.log(XMLHttpRequest);
							console.log(errorThrown);
						}
					});

		
	
};
var operation, html;

SMIME._renderSignedInfo = function(data, view) {
	           var hdrtable = Dwt.byId(view._hdrTableId);
	              operation = hdrtable;
             html = data;
               if(data.signer != null && data.validSigner == true){
	             $(operation).parent().append("<tr><td><span>"+
                 "<img class='SMIMEImage' src='/img/valid.png' /> " +
	             "</span>"+"Mensagem assinada digitalmente por "+data.signer+"</td></tr>");   
          }else{
          	 $(operation).parent().append("<tr><td><span>"+
             "<img class='SMIMEImage' src='/img/error.png' /></span> Ocorreu um erro na hora de resgatar as informações</td></tr>");
          }

          /*
$(operation).parent().parent().parent().find(".MsgBody").empty();
 if(data.content != null){
      if($("#content"))
      	$("#content").remove();
   $(operation).parent().parent().parent().find(".MsgBody").append("<div id='content' hidden>"+data.content+"</div>");
 // PROCESSA OS ANEXOS DA MENSAGEM SE TIVER 
*/
var valida_screen = 0;
var screen_active = $(operation).parent().parent().parent().find(".MsgBody");
if(typeof screen_active.length !== "undefined" && screen_active.length > 0){
$(operation).parent().parent().parent().find(".MsgBody").empty();
valida_screen = 1;
}else{
valida_screen = 2;
$(".DwtShell .ZmMailMsgView .MsgBody iframe").contents().find("html body div").empty();
}

if(data.content != null){
      if($("#content"))
        $("#content").remove();
  if(valida_screen == 1){
   $(operation).parent().parent().parent().find(".MsgBody").append("<div id='content' hidden>"+data.content+"</div>");
}else if (valida_screen == 2){
  $(".DwtShell .ZmMailMsgView .MsgBody iframe").contents().find("html body").append("<div id='content' hidden>"+data.content+"</div>");
}


 $("#content").remove();
 var contentDec = data.content;

var array = contentDec.split("Arquivo do EBDrive");
var array2= contentDec.split("Link de acesso:");

var aux = [];
var aux2 = []; 
  for(i in array){
  if(i != 0){
   var valueLabels  = array[i].split("Link de acesso:")[0];
  aux2.push(valueLabels.split(': ')[1]);
  aux.push(array2[i].split("Arquivo")[0]);
   }else{
       var valueLabels  = array[i].split("Link de acesso:")[0];
     contentDec = array[i];   

}
}

var splCont = contentDec.split("-----");
var contentFormated = "";
var contentAdded = "";
  for(t in splCont){
    if(t == 0){
     contentFormated = splCont[0];
}else 
   contentAdded = contentAdded+ " "+ splCont[t];

  }

   if(valida_screen == 1){
   $(operation).parent().parent().parent().find('.MsgBody').append("<div id='content' ><p class='c1'>"+contentFormated+"</p><br><p class='c2'>"+contentAdded+"</p></div>");
   //	$(operation).parent().parent().parent().append(bkpANX);
   }else  if(valida_screen == 2){
       $(".DwtShell .ZmMailMsgView .MsgBody iframe").contents().find("html body").
       append("<div id='content' ><p class='c1'>"+contentFormated+"</p><br><p class='c2'>"+contentAdded+"</p></div>");
   }
/*
var aux = [];
var aux2 = []; 
var labelAnexos = [];
   $(bkpANX).find('img ~ a').each(function(){
   aux.push($(this).attr("href"));
   aux2.push($(this).text());
});
*/
urlEB = aux;
labelEB = aux2;
var outer = [];

for(an in aux){
	//console.log("ANEXO:"+an+aux2[an]+anexos[an]);
 outer.push("<div id='"+'anxs'+an+"'><img class='SMIMEImage' src='/service/zimlet/zm_usto_re_smime/cadeado.png' alt='Anexo Seguro'>"+
 "<span><a target='blank' class='linkEBDrive' href='"+aux[an]+"'>"+aux2[an]+"</a>"+
  "<a class = 'linkEBDrive sendEBdrive' href=\"javascript:saveAttachment("+an+");\">Enviar para o EBDrive</a></span></div>");
}
   if($("#content .att #ebdriveAtts"))
   	   $(this).remove();
   $("#content").append("<div class='att' ><table id='ebdriveAtts'><tbody></tbody></table></div>");
//console.log(outer);
	for(o in outer){
	//console.log(outer[o]);
	$("#content .att #ebdriveAtts").append("<tr><td>"+outer[o]+"</td></tr>");
	if($("#content .att h2.titleSMIME"))
	$("#content .att h2.titleSMIME").remove();
	$("#content .att").prepend("<h2 class='titleSMIME'> Links do EBDrive </h2>");
	}
/*if(typeof screen_active.length !== "undefined"){
$(operation).parent().parent().parent().find(".MsgBody").empty();
}else{
	$(".DwtShell .ZmMailMsgView .MsgBody iframe").contents().find("html body div").empty();
}*/
}
	};
var urlEB = [];
var labelEB = [];
SMIME._renderSMIMEInfo = function(data, view) {
	           var hdrtable = Dwt.byId(view._hdrTableId);
	              operation = hdrtable;
          // Process Signatures ////////////////////////////////
               if(data.signer != null && data.validSigner == true){
	             $(operation).parent().append("<tr><td><span>"+ 
                 "<img class='SMIMEImage' src='/img/valid.png' /> " +
	             "</span>"+"Mensagem assinada digitalmente por "+data.signer+"</td></tr>");   
          }else{
          	 $(operation).parent().append("<tr><td><span>"+
                 "<img class='SMIMEImage' src='/img/error.png' /></span> Ocorreu um erro na hora de resgatar as informações</td></tr>");
          }
         // ////////////////////////////////
//$(document).ready(function(){
// =============================================================================================\\\\\\
var valida_screen = 0;
var screen_active = $(operation).parent().parent().parent().find(".MsgBody");
if($(operation).parent().parent().parent().length > 0 && $(".Conv2MsgHeader ~ .attachments").find("table tbody tr td table").length > 0){
valida_screen = 1;
}else{
valida_screen = 2;
}

    if(data.content != null){
   $(".Conv2MsgHeader ~ .attachments").find("table tbody tr td table").remove();
      if($("#content"))
      	$("#content").remove();
  if(valida_screen == 1){
  $(operation).parent().parent().parent().append("<div id='content' hidden>"+data.content+"</div>");
}else if (valida_screen == 2){
	$(".MessageHeaderAttachments").remove();
   $("div[id$='_infoBar']").append("<div id='content' hidden>"+data.content+"</div>");
   }
 // PROCESSA OS ANEXOS DA MENSAGEM SE TIVER 



 $("#content").remove();
 var contentDec = data.content;

var array = contentDec.split("Arquivo do EBDrive");
var array2= contentDec.split("Link de acesso:");

var aux = [];
var aux2 = []; 
  for(i in array){
  if(i != 0){
   var valueLabels  = array[i].split("Link de acesso:")[0];
  aux2.push(valueLabels.split(': ')[1]);
  aux.push(array2[i].split("Arquivo")[0]);
   }else{
       var valueLabels  = array[i].split("Link de acesso:")[0];
    // contentDec = valueLabels.split(': ')[0];    
        contentDec = array[i];    
}
}


var splCont = contentDec.split("-----");
var contentFormated = "";
var contentAdded = "";
  for(t in splCont){
    if(t == 0){
     contentFormated = splCont[0];
}else 
   contentAdded = contentAdded+ " "+ splCont[t];

  }

   
   
   if(valida_screen == 1){
    $(".DwtShell .Conv2Messages .ZmMailMsgCapsuleView").append("<div id='content' ><p class='c1'>"+contentFormated+"</p><br><p class='c2'>"+contentAdded+"</p></div>");
    
}else if (valida_screen == 2){
	if( $("div[id$='_infoBar']").length != 0){
   $("div[id$='_infoBar']").append("<div id='content' ><p class='c1'>"+contentFormated+"</p><br><p class='c2'>"+contentAdded+"</p></div>");
}else{
	 $(".DwtShell .Conv2Messages .ZmMailMsgCapsuleView").append("<div id='content' ><p class='c1'>"+contentFormated+"</p><br><p class='c2'>"+contentAdded+"</p></div>");
}
   }
  
 
   //	$(operation).parent().parent().parent().append(bkpANX);

/*
var aux = [];
var aux2 = []; 
var labelAnexos = [];
   $(bkpANX).find('img ~ a').each(function(){
   aux.push($(this).attr("href"));
   aux2.push($(this).text());
});
*/
urlEB = aux;
labelEB = aux2;
var outer = [];

for(an in aux){
	//console.log("ANEXO:"+an+aux2[an]+anexos[an]);
 outer.push("<div id='"+'anxs'+an+"'><img class='SMIMEImage' src='/service/zimlet/zm_usto_re_smime/cadeado.png' alt='Anexo Seguro'>"+
 "<span><a target='blank' class='linkEBDrive' href='"+aux[an]+"'>"+aux2[an]+"</a>"+
  "<a class = 'linkEBDrive sendEBdrive' href=\"javascript:saveAttachment("+an+");\">Enviar para o EBDrive</a></span></div>");
}


//console.log(outer);
	for(o in outer){
	//console.log(outer[o]);
	$(".Conv2MsgHeader ~ .attachments").find("table tbody").append("<tr><td>"+outer[o]+"</td></tr>");
	if($(".attachments h2.titleSMIME"))
	$(".attachments h2.titleSMIME").remove();
	$(".attachments").prepend("<h2 class='titleSMIME'>Links do EBDrive</h2>");
	}


}

	};


 SMIME.aviso = function(msg){
	var appController = appCtxt.getAppController();
	appController.setStatusMsg(msg, ZmStatusView.LEVEL_INFO);
}

SMIME.popup = function(msg, type){
	if (type == 1)
		var style = DwtMessageDialog.CRITICAL_STYLE;
	if (type == 3)
		var style = DwtMessageDialog.WARNING_STYLE;
	var dialog = appCtxt.getMsgDialog();
	dialog.setButtonListener(DwtDialog.OK_BUTTON, $('#ZmMsgDialog_button2')
			.click(function() {
				appCtxt.getMsgDialog().popdown()
			}));
	dialog.reset();
	dialog.setMessage(msg, style);
	dialog.popup();
}


SMIME.b64DecodeUnicode=function(str) {
    return decodeURIComponent(Array.prototype.map.call(atob(str), function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
}

SMIME.getItemStorage=function(name){
     var value = localStorage[name];   
    return SMIME.b64DecodeUnicode(value);

};

SMIME.b64EncodeUnicode = function(str) {
    return btoa(encodeURIComponent(str).replace(/%([0-9A-F]{2})/g, function(match, p1) {
        return String.fromCharCode('0x' + p1);
    }));
}

SMIME.createItemStorage = function(cert, value) {

    localStorage[cert] = SMIME.b64EncodeUnicode(value);
}



function callModalSend(){

	$("#modalCert").modal('show'); 	
}


function saveAttachment(i){

 var url = urlEB[i];
 var label = labelEB[i];
 console.log(url + label);
 var URL_EBDRIVE = window.top.udrive_index_url;
 var URL_EBDRIVE_UPLOAD = "/rest/file/backup/udrive";
 var CALLBACK_URL = URL_EBDRIVE + URL_EBDRIVE_UPLOAD;

 var formData = {
   "zimbra": window.umail_base_url,
   "url" : url,
   "login" : appCtxt.accountList.mainAccount.name,
   "csrfdrive" : window.top.csrfToken,
   "sessiondrive" : window.top.sessiondrive.value,
   "authCode" : window.top.authToken,
   "authdrive" : window.top.authdrive.value ,
   "label" : label
 };

  if (window.top.errorLogin == false){
 var texto;
 $.ajax({
  type : "POST",
  url : CALLBACK_URL,
  dataType : "json",
  data : JSON.stringify(formData),
  contentType: "application/json",

    beforeSend: function () {
     
    },
  success : function(data) {
    console.log("Enviado com sucesso: " + data.status);
    console.log(data);
    if (data == 'ERRO_AUTENTICACAO'){
   texto = "Não foi possivel se autenticar!";
   SMIME.popup(texto,3);
    }else if (data == 'ERRO_REQUISICAO'){	
	 texto = "Ocorreu algum erro enquanto seu arquivo era enviado para o EBDrive!";
    SMIME.popup(texto,3);
    }else if (data == 'SUCESSO'){
       texto = "Seu arquivo foi enviado com sucesso!";
     SMIME.popup(texto,2);
    }

  },

  error : function(XMLHttpRequest, textStatus, errorThrown) {
    console.log("Error no ajax");
    console.log(XMLHttpRequest);
    console.log(errorThrown);
    texto  = "Ocorreu algum erro enquanto seu arquivo era enviado para o EBDrive!";
    SMIME.popup(texto,1);
  }
});
}else
 SMIME.popup('Você n&atilde;o est&aacute; logado no EBDrive ainda!',3);
 
}
