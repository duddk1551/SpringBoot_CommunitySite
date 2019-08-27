var chatWriter = '${writer.writer}';

function Chat_sendMessages() {
	var message = $('#chat-room .input-box #text-input').val();
	
	message = message.trim();
	
	if(message == '') {
		$('#chat-room .input-box #text-input').val('');
		$('#chat-room .input-box #text-input').focus();
		return false;
	}
	
	$.post('./addMessage',{
		writer : chatWriter,
		body : message
	},function() {
		
	},'json');
	
	$('#chat-room .input-box #text-input').val('');
	$('#chat-room .input-box #text-input').focus();
	
}

var Chat_lastreceiveId = - 1;

function Chat_loadNewMessages(){
	$.get('./getMessages',{
		from : Chat_lastreceiveId + 1
	},function(data){
		for(var i = 0; i < data.length; i++) {
			var message = data[i];
			
			Chat_lastreceiveId = message.id;
			Chat_drawNewMessage(message);
		}
		//setTimeout(Chat_loadNewMessages,1000);
	},'json');
}

function Chat_drawNewMessage(message) {
	var chatMessage = message.body;
	var writer = message.writer;
    
	var whoClassName = 'mine';
	
	if ( writer != chatWriter ) {
		whoClassName = 'other';
	}
	
    var html = ``;
    html += `<div class="chat-message ${whoClassName}">
    	<section><i class="fa fa-user"></i></section>
        <span>${writer}</span>
        <div>${chatMessage}</div>
    </div>
    `;
    
    $('#chat-room .message-group:last-child').append(html);
    $('#chat-room .message-box').scrollTop(99999999999);
}

$(function() {
	$('#chat-room .input-box .btn-submit').click(Chat_sendMessages);
	
	$('#chat-room .input-box #text-input').keydown(function(e) {
		if(e.keyCode == 13) {
			Chat_sendMessages();
		}
	});
	Chat_loadNewMessages();
});