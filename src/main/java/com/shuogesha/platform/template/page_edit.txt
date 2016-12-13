<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../common/meta.jsp"%>
<title>修改</title>
</head>
<body>
<article class="page-container">
	<form id="jvForm"  action="o_update.do" method="post" class="form form-horizontal" enctype="multipart/form-data">
		<input type="hidden" id="id" name="id" value="${bean.id}">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${bean.name}" placeholder="" id="name" name="name">
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
			</div>
		</div>
	</form>
</article>

<!--_footer 作为公共模版分离出去--> 
<%@ include file="../common/footer.jsp"%>
<!--/_footer /作为公共模版分离出去--> 

<!--请在下方写此页面业务相关的脚本--> 
<script type="text/javascript">
$(function(){
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	
	$("#jvForm").validate({
		rules:{
			name:{
				required:true,
			},
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			$.ajax({
				type: form.method,
				url: form.action,
			    dataType: "json",
			    data: $(form).serialize(),
			    success: function(result){
			    	var index = parent.layer.getFrameIndex(window.name);
			    	parent.$('.btn-refresh').click();
					parent.layer.close(index);
			     }
			});
			return false;
		}
	});
});
</script> 
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>