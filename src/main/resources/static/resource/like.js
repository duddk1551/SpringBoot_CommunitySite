function like_func() {
	var boardId = articleId;
	
	$.get('./like',{
		articleId : articleId
	}, function(data) {
		var msg = '';
		var like_img = '';
		msg += data.msg;
		alert(msg);
		
		drawLike(data);
		
	},'json');
	
}

function drawLike(data) {
	
	if(data.like_check == 0) {
		like_img = "/images/like.png";
	} else {
		like_img = "/images/dislike.png";
	}
	
	$('#like-table').find('[class=like_img]').attr('src', like_img);
	$('#like_cnt').html(data.like_cnt);
	//$('#like_check').html(data.like_check);
}


$(function() {
	drawLike();
});