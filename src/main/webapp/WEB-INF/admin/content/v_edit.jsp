<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../common/meta.jsp"%>
<script type="text/javascript" charset="utf-8" src="/static/ckeditor/ckeditor.js"></script>
<title>修改</title>
</head>
<body>
<article class="page-container">
	<form id="jvForm"  action="o_update.do" method="post" class="form form-horizontal" enctype="multipart/form-data">
		<input type="hidden" id="id" name="id" value="${bean.id}">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>标题：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${bean.name}" placeholder="" id="name" name="name">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">描述：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${bean.description}" placeholder="" id="description" name="description">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>发布时间：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${bean.dateline}" placeholder="" id="dateline" name="dateline">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red"></span>内容：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<textarea id="container">${bean.content}
				</textarea>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red"></span>来源：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${bean.source}" placeholder="" id="source" name="source">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red"></span>来源地址：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${bean.sourceUrl}" placeholder="" id="sourceUrl" name="sourceUrl">
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
	
	var oEditor = CKEDITOR.replace('container');
	
	$("#jvForm").validate({
		rules:{
			name:{
				required:true,
			},
			dateline:{
				required:true,
			}
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			var content=oEditor.getData();
			var data=$(form).serializeJson();
			data.content=content;
			$.ajax({
				type: form.method,
				url: form.action,
			    dataType: "json",
			    data: data,
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