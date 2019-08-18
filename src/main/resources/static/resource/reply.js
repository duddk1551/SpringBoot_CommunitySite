	function drawReply(reply) {
		var 번호 = reply.id;
		var 등록날짜 = reply.regDate;
		var 회원번호 = reply.memberId;
		var 작성자 = reply.extra.writer;
		
		var 내용 = `
		<div>
			<div class="edit-mode-visible">
				<form onsubmit="modifyReply(this); return false;">
					<input type="hidden" name="id" value="${번호}">
					<input type="text" name="body">
					<input type="submit" value="수정">
					<input type="reset" value="취소" onclick="disableReplyEditMode(this);">
				</form>
			</div>
			
			<div class="read-mode-visible body-view">
				${reply.body}
			</div>
		</div>
		`;
		
		var $내용 = $(내용);
		$내용.find('[name=body]').attr('value', reply.body);
		
		var 내용 = $내용.html();
		
		var 비고 = `
		<div class="editable-item text-align-center">
			<a class="read-mode-visible" href="javascript:;" onclick='enableReplyEditMode(this);'">수정</a>
			<a href="javascript:;" onclick='deleteReply(this)'">삭제</a>
		</div>
		`;
		
		var editableClass = '';
		
		if(회원번호 == loginedMemberId) {
			editableClass = 'editable';
		}
		
		var html = `
		<tr data-id="${번호}" data-member-id="${회원번호}" class="${editableClass}">
			<td>${번호}</td>
			<td>${등록날짜}</td>
			<td>${작성자}</td>
			<td>${내용}</td>
			<td class="edit-menu">${비고}</td>
		</tr>
		`;
		
		$('.reply-table .reply-body').prepend(html);
	}
	
	var lastReceivedReplyId = 0;
	
	function loadNewReplies() {
		
		$.get('./getAllReplies',{
			articleId : articleId,
			from : lastReceivedReplyId + 1
		}, function(data) {
			
			for(var i = 0; i < data.length; i++) {
				var reply = data[i];
				lastReceivedReplyId = reply.id;
				drawReply(reply);
			}
			//setTimeout(loadNewReplies, 1000);
		},'json'
		);
	}

	function deleteReply(el){
		
		if(confirm('댓글을 삭제하시겠습니까?') == false) {
			return;
		}
		
		var $tr = $(el).closest('tr');
		var id = parseInt($tr.attr('data-id'));
		
		$tr.addClass('add-animation-item-now-deleting')
		$tr.find('.body-view').empty().append('삭제중...');
		
		$.post("./deleteReply",
			{
				id : id
			},
			function(data){	
				$tr.remove();			
			},
			"json"
		);
	}

	function addReply(){
		
		var 댓글 = $('.table-reply .add-reply #content').val();
		var 게시물번호 = $('.table-reply .add-reply #articleId').val();

		댓글 = 댓글.trim();
		
		if (댓글 == '') {
			$('.add-reply input[name="body"]').val('');
			$('.add-reply input[name="body"]').focus();
			return false;
		}
		
		//form['submit-btn'].value = "작성중...";
		//form['submit-btn'].disabled = true;
		
		$.post("./addReply",
			{
				articleId : 게시물번호, 
				body : 댓글
			},
			function (data){
				loadNewReplies();
			},
			"json"
		);
		
		$('.table-reply .add-reply #content').val('');
		$('.table-reply .add-reply #content').focus();
		
	}
	
	function enableReplyEditMode(el) {
		var $tr = $(el).closest('tr');
		$tr.addClass('edit-mode');
	}
	
	function modifyReply(form) {
		form.body.value = form.body.value.trim();
		var body = form.body.value;
		
		if(body.length == 0) {
			alert('댓글 내용을 입력해주세요.');
			form.body.focus();
			
			return false;
		}
		
		var $tr = $(form).closest('tr');
		var id = parseInt($tr.attr('data-id'));
		
		$.post("./modifyReply", {
			id : id,
			body : body
		},function(data) {
			$tr.find('.body-view').text(body);
		},"json");
		
		disableReplyEditMode(form);
		$tr.find('.body-view').text('수정중...');
	}
	
	function disableReplyEditMode(el) {
		var $tr = $(el).closest('tr');
		$tr.removeClass('edit-mode');
	}

	$(function() {
		
		//$('.table-reply input[type="submit"]').click(addReply);
		
		loadNewReplies();
		
	});
	