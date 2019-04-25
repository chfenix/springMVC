//为空的判断辅助
function removeFormClass(operation) {
	var operationSmall = "#small_"+operation;
	var operationDiv = "#div_"+operation;
	var operationI = "#i_"+operation;
	$(operationSmall).css("display","none");
	if($(operationDiv).hasClass("has-error")){
		$(operationDiv).removeClass("has-error");
	}
	if($(operationI).hasClass("glyphicon-remove")){
		$(operationI).removeClass("glyphicon glyphicon-remove");
	}
	if($(operationI).hasClass("glyphicon-ok")){
		$(operationI).removeClass("glyphicon glyphicon-ok");
	}
}

//企业名称，编码和状态失去焦点事件
$(function(){
	//返回
	$("#fanhui").mousedown(function() {
		$("#enterprise_form").attr("action","queryEnterprisePage.do");
		$("#enterprise_form").attr("onsubmit","return true;");
		$("#enterprise_form").submit();
	})
	//名称
	$("#enterpriseName").blur(function(){
  		  var enterpriseName = $("#enterpriseName").val().trim();
      	  if(enterpriseName==""){
      		  removeFormClass("name");
      		  $("#small_name").html("");
			  $("#small_name").html("企业名称不能为空");
      		  $('#small_name').css('display','block');
      		  $("#div_name").addClass("has-feedback has-error");
      		  $("#i_name").addClass("glyphicon glyphicon-remove");
      	  }else{
      		  removeFormClass("name");
      		  $("#div_name").addClass("has-feedback has-success");
      		  $("#i_name").addClass("glyphicon glyphicon-ok");
      	  }
     });
  	
	 //编码
  	 $("#enterpriseCode").blur(function(){
    	  var enterpriseCode = $("#enterpriseCode").val().trim();
    	  if(enterpriseCode==""){
    		  removeFormClass("code");
    		  $("#small_code").html("");
			  $("#small_code").html("企业编码不能为空");
    		  $('#small_code').css('display','block');
    		  $("#div_code").addClass("has-feedback has-error");
    		  $("#i_code").addClass("glyphicon glyphicon-remove");
    	  }else{
    		  removeFormClass("code");
    		  $("#div_code").addClass("has-feedback has-success");
    		  $("#i_code").addClass("glyphicon glyphicon-ok");		        		
    	  }
     });
})