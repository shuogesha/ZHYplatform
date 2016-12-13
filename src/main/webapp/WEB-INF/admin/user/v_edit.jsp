<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib  uri="http://www.shuogesha.net/shuogesha/core" prefix="dictionary" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../common/meta.jsp"%>
<title>修改管理员</title>
</head>
<body>
<article class="page-container">
	<form id="jvForm"  action="o_update.do" method="post" class="form form-horizontal" enctype="multipart/form-data">
	<input type="hidden" id="id" value="${bean.id}">
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red"></span>管理员：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="${bean.username}" readonly="readonly" id="username" name="username">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red"></span>初始密码：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="password" class="input-text" autocomplete="off" value="" placeholder="密码" id="password" name="password">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red"></span>确认密码：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="password" class="input-text" autocomplete="off"  placeholder="确认新密码" id="password2" name="password2">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red"></span>性别：</label>
		<div class="formControls col-xs-8 col-sm-9 skin-minimal">
			<div class="radio-box">
				<input name="sex" type="radio" id="sex-1" checked>
				<label for="sex-1">男</label>
			</div>
			<div class="radio-box">
				<input type="radio" id="sex-2" name="sex">
				<label for="sex-2">女</label>
			</div>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red"></span>手机：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="${bean.phone}" placeholder="" id="phone" name="phone" >
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red"></span>邮箱：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" placeholder="@" name="email" id="email" value="${bean.email}">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">角色：</label>
		<div class="formControls col-xs-8 col-sm-9"> 
			<span class="check-box" style="width:150px;">
				<c:forEach items="${roleList}" var="role">
					<label><input value="${role.id}" type="checkbox" name="roleIds"/>${role.name}</label><br/>
				</c:forEach>
			</span>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">备注：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<textarea name="remark" cols="" rows="" class="textarea"  placeholder="说点什么...100个字符以内" dragonfly="true" onKeyUp="textarealength(this,100)">${bean.remark}</textarea>
			<p class="textarea-numberbar"><em class="textarea-length">0</em>/100</p>
		</div>
	</div>
	<div class="row cl">
		<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
			<input class="btn btn-primary radius" type="submit" onclick="save()" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
		</div>
	</div>
	</form>
</article>

<!--_footer 作为公共模版分离出去--> 
<%@ include file="../common/footer.jsp"%>
<!--/_footer /作为公共模版分离出去--> 

<!--请在下方写此页面业务相关的脚本--> 
<script type="text/javascript">
function save(){
	window.parent.location.reload();
	window.parent.location.replace(window.parent.location.href+"?v="+new date());
}
$(function(){
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	var roleIds = [<c:forEach items="${bean.roles}" var="i">"${i.id}",</c:forEach>];
	$("input[name=roleIds]").each(function() {
		var roleId = $(this).val();
		for(var i=0,len=roleIds.length;i<len;i++) {
			if(roleIds[i]==roleId) {
				$(this).attr("checked",true);
				break;
			}
		}
	});
	$("#jvForm").validate({
		rules:{
			password:{
			},
			password2:{
				equalTo: "#password"
			},
			sex:{
				required:true,
			},
			phone:{
				isPhone:true,
			},
			email:{
				email:true,
			},
			adminRole:{
				required:true,
			},
			messages: { 
				username: {
					remote: "用户名已经存在",
					required: "请输入用户名",
					minlength: "格式错误"
				}
			}
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
			    	layer.msg('保存成功！',{icon:1,time:1000});
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