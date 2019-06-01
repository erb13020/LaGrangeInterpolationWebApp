$(document).ready(function(){
		$('#ComputeLagrange').submit(function(){		
			$.ajax({
				url: 'compute',
				type: 'GET',
				dataType: 'json',
				data: $('#ComputeLagrange').serialize(),
				success: function(data){
					if(data.isValid){
						$('#polynomial').html('$$' + data.points_list + '$$');
						$('#polynomial').slideDown(500);
					}else{
						alert('Please enter valid points');
					}
				}
			});
			alert.log("javascript here v1");
			return false;
		});
		alert.log("javascript here v2");
	});