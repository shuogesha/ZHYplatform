﻿<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../common/meta.jsp"%>
<title>管理员列表</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 管理员管理 <a class="btn btn-success  btn-refresh radius r" style="line-height:1.6em;margin-top:3px" href="javascript:void(0);" onclick="location.replace(location.href)" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<form id="tableForm" action="v_list.do">
	<div class="text-c"> 
		<input type="text" class="input-text" style="width:250px" placeholder="用户名" name="name" value="${name}">
		<button type="submit" class="btn btn-success" id="submitBtn" name=""><i class="Hui-iconfont">&#xe665;</i> 搜</button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l"><a href="javascript:;" onclick="optDel()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a> <a href="javascript:;" onclick="optAdd('添加','v_add.do','800','500')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加</a></span> <span class="r">共有数据：<strong>${pagination.totalCount}</strong> 条</span> </div>
	<div class="mt-20">
	<input type="hidden" name="ids" id="ids" value=""/>
	<input type="hidden" id="pageNo" name="pageNo" value="${pagination.pageNo}"/>
	<table class="table table-border table-bordered table-bg">
		<thead>
			<tr>
				<th scope="col" colspan="9">管理员列表</th>
			</tr>
			<tr class="text-c">
				<th width="25"><input type="checkbox" name="" value=""></th>
				<th width="150">登录名</th>
				<th width="90">手机</th>
				<th width="150">邮箱</th>
				<th>角色</th>
				<th width="130">IP</th>
				<th width="100">是否已启用</th>
				<th width="100">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pagination.datas}" var="item">
			<tr class="text-c">
				<td><input type="checkbox" value="${item.id}" name="ids"></td>
				<td>${item.username}</td>
				<td>${item.phone}</td>
				<td>${item.email}</td>
				<td></td>
				<td>${item.unifiedUser.lastLoginIp}</td>
				<td class="td-status">
					<c:if test="${item.unifiedUser.disabled}">
						<span class="label label-default radius">已禁用</span>
					</c:if>
					<c:if test="${!item.unifiedUser.disabled}">
						<span class="label label-success radius">已启用</span>
					</c:if>
				</td>
				<td class="td-manage">
					<c:if test="${item.unifiedUser.disabled}">
						<a style="text-decoration:none" onClick="admin_start(this,'${item.unifiedUser.id}')" href="javascript:;" title="启用"><i class="Hui-iconfont">&#xe6e1;</i></a>
					</c:if>
					<c:if test="${!item.unifiedUser.disabled}">
						<a style="text-decoration:none" onClick="admin_stop(this,'${item.unifiedUser.id}')" href="javascript:;" title="停用"><i class="Hui-iconfont">&#xe631;</i></a>
					</c:if>	
				<a title="编辑" href="javascript:;" onclick="optEdit('管理员编辑','v_edit.do?id=${item.id}','${item.id}','800','500')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a> <a title="删除" href="javascript:;" onclick="admin_del(this,'1')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<%@include file="../common/page.jsp"%>
	</div>
	</form>
</div>
<%@ include file="../common/footer.jsp"%>
<script type="text/javascript">
function optAdd(title,url,w,h){
	layer_show(title,url,w,h);
}
function optEdit(title,url,id,w,h){
	layer_show(title,url,w,h);
}
function getTableForm() {
	return document.getElementById('tableForm');
}
function optDel(obj,id){
	layer.confirm('确认要删除吗？',function(index){
		var f = getTableForm();
		$("#ids").val(id);
		f.action="o_delete.do";
		f.submit();
		layer.msg('已删除!',{icon:1,time:1000});
	});
}
function datadel(){
	layer.confirm('确认要删除吗？',function(index){
		var f = getTableForm();
		$("#ids").val('');
		f.action="o_delete.do";
		f.submit();
		layer.msg('已删除!',{icon:1,time:1000});
	});
}
function admin_stop(obj,id){
	layer.confirm('确认要停用吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		$.ajax({
			type: "post",
			url: "../unifiedUser/o_user_disable.do",
		    dataType: "json",
		    data: {id:id},
		    success: function(result){
				$(obj).parents("tr").find(".td-manage").prepend('<a onClick="admin_start(this,id)" href="javascript:;" title="启用" style="text-decoration:none"><i class="Hui-iconfont">&#xe615;</i></a>');
				$(obj).parents("tr").find(".td-status").html('<span class="label label-default radius">已禁用</span>');
				$(obj).remove();
				layer.msg('已停用!',{icon: 5,time:1000});
		    }
		});
	});
}

/*管理员-启用*/
function admin_start(obj,id){
	layer.confirm('确认要启用吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		$.ajax({
			type: "post",
			url: "../unifiedUser/o_user_enable.do",
		    dataType: "json",
		    data: {id:id},
		    success: function(result){
				$(obj).parents("tr").find(".td-manage").prepend('<a onClick="admin_stop(this,id)" href="javascript:;" title="停用" style="text-decoration:none"><i class="Hui-iconfont">&#xe631;</i></a>');
				$(obj).parents("tr").find(".td-status").html('<span class="label label-success radius">已启用</span>');
				$(obj).remove();
				layer.msg('已启用!', {icon: 6,time:1000});
		    }
		});
	});
}
</script>
</body>
</html>