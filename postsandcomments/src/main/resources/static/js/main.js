$(document).ready(function() {
	function getFormData($form){
		var unindexed_array = $form.serializeArray();
		var indexed_array = {};

		$.map(unindexed_array, function(n, i){
			indexed_array[n['name']] = n['value'];
		});

		return indexed_array;
	}
	$('#formpost').submit(function(event) {

		/* stop form from submitting normally */
		event.preventDefault();
		var userName = $('#username').val();
		/* get the action attribute from the <form action=""> element */
		var $form = $( this );
		var url = $form.attr( 'action' );

		$.ajax({
			url: url, // url where to submit the request
			type : "POST", // type of action POST || GET
			contentType: 'application/json;charset=UTF-8',
			dataType : 'json', // data type
			data :  JSON.stringify(getFormData($form)), // post data || get data
			success : function(result) {
				// you can see the result from the console
				// tab of the developer tools
				console.log(result);
				$('#div2').show();
				userPostsTable.ajax.url('post/'+userName);
				userPostsTable.ajax.reload(null,false);
				allPostsDataTable.ajax.reload(null,false);
				$("#formpost")[0].reset();
				$.toast({
					heading: 'Success',
					text: 'Post Saved.',
					showHideTransition: 'slide',
					position: 'top-right',
					icon: 'success'
				});
			},
			error: function(xhr, resp, text) {
				$.toast({
					heading: 'Error',
					text: 'Unable to save post, please try again after some time.',
					showHideTransition: 'slide',
					position: 'top-right',
					icon: 'error'
				});
			}
		})
	});

	$('#allPosts').on('submit', 'form', function(event) {

		/* stop form from submitting normally */
		event.preventDefault();
		/* get the action attribute from the <form action=""> element */
		var $form = $( this );
		var url = $form.attr( 'action' );

		$.ajax({
			url: url, // url where to submit the request
			type : "POST", // type of action POST || GET
			contentType: 'application/json;charset=UTF-8',
			dataType : 'json', // data type
			data :  JSON.stringify(getFormData($form)), // post data || get data
			success : function(result) {
				// you can see the result from the console
				// tab of the developer tools
				console.log(result);
				allPostsDataTable.ajax.reload(null,false);
				$.toast({
					heading: 'Success',
					text: 'Comment Saved.',
					showHideTransition: 'slide',
					position: 'top-right',
					icon: 'success'
				});
			},
			error: function(xhr, resp, text) {
				$.toast({
					heading: 'Error',
					text: 'Unable to save post, please try again after some time.',
					showHideTransition: 'slide',
					position: 'top-right',
					icon: 'error'
				});
			}
		})
	});
	var userPostsTable = $('#userPosts').DataTable( {
		"bLengthChange": false,
		"processing": true,
		"serverSide": true,
		"searching":false,
		"ordering":false,
		"deferLoading": 0,
		"ajax": {
			"url": "post",
			"type": "POST",
			"contentType": "application/json",           
			"data":function(data) {
				return JSON.stringify(data);
			},
			"dataType":"json",
			"error": function(jqXHR, textStatus, errorThrown){
				if(errorThrown == 'Forbidden'){	
					window.location.reload(true);
				}else if(textStatus == "error"){
					$('#alert .alert-box').removeClass("hide");
					$("#alert .alert-box").html('Server not reachable, please try after sometime or contact system administrator.');
				}
			}
		},
		"columns": [
			{ 
				"data": "post",
				"name":"post",
				"searchable":false,
				"orderable":false
			}
			]
	} );

	var allPostsDataTable = $('#allPosts').DataTable( {
		"bLengthChange": false,
		"processing": true,
		"serverSide": true,
		"searching":false,
		"ordering":false,
		"ajax": {
			"url": "allposts",
			"type": "POST",
			"contentType": "application/json",           
			"data":function(data) {
				return JSON.stringify(data);
			},
			"dataType":"json",
			"error": function(jqXHR, textStatus, errorThrown){
				if(errorThrown == 'Forbidden'){	
					window.location.reload(true);
				}else if(textStatus == "error"){
					$('#alert .alert-box').removeClass("hide");
					$("#alert .alert-box").html('Server not reachable, please try after sometime or contact system administrator.');
				}
			}
		},
		"columns": [
			{ 
				"data": "post",
				"name":"post",
				"searchable":false,
				"orderable":false,
				render : function(data, type, row) {
					console.log(data)
					var postBody = '<ul id="comments"><li class="cmmnt"><div class="cmmnt-content"><header><span class="userlink">'+data.userName+'</span> - <span class="pubdate">posted on '+new Date(data.createdDate).toJSON().slice(0, 10)+', '+data.city+' ('+data.weatherData.latitude+', '+data.weatherData.longitude+'), Weather '+data.weatherData.temperature+' &#8451;</span></header><p>'+data.post+'</p></div><ul class="replies">';
					var commentBody = '';
					if(data.comments!=null){
						for (i = 0; i < data.comments.length; i++) {
							commentBody+='<li class="cmmnt"><div class="cmmnt-content"><p>'+data.comments[i].comment+'</p></div></li>';
						}
					}
					postBody+=commentBody + '</ul></li></ul><form action="comment" class="reply"><input type="text" id="addcomment" name="comment" placeholder="Add comment.." required="required"> <input type="hidden" id="postId" name="postId" value="'+data.postId+'"> <input type="submit" value="Reply" class="reply"></form>';
					return postBody;
				}
			}
			]
	} );

	function myFunction(){
		console.log("hello");
	}
} );