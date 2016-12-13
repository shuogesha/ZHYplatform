<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../common/meta.jsp"%>
<title>用户管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 用户中心 <span class="c-gray en">&gt;</span> 用户管理 <a class="btn btn-success  btn-refresh radius r" style="line-height:1.6em;margin-top:3px"  href="javascript:void(0);" onclick="location.replace(location.href)"  title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<form id="tableForm" action="v_list.do">
	<div class="text-c"> 
		<input type="text" class="input-text" style="width:250px" placeholder="用户名" id="" name="name" value="${name}">
		<button type="submit" class="btn btn-success radius" id="submitBtn" name=""><i class="Hui-iconfont">&#xe665;</i> 搜用户</button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l"><a href="javascript:;" onclick="datadel()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a> <a href="javascript:;" onclick="optAdd('添加用户','v_add.do','','510')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加用户</a></span> <span class="r">共有数据：<strong>${pagination.totalCount}</strong> 条</span> </div>
	<div class="mt-20">
	<input type="hidden" name="ids" id="ids" value=""/>
	<input type="hidden" id="pageNo" name="pageNo" value="${pagination.pageNo}"/>
	<table class="table table-border table-bordered table-hover table-bg table-sort">
		<thead>
			<tr class="text-c">
				<th width="25"><input type="checkbox" name="" value=""></th>
				<th width="100">用户名</th>
				<th width="40">性别</th>
				<th width="90">手机</th>
				<th width="150">邮箱</th>
				<th width="">IP</th>
				<th width="130">加入时间</th>
				<th width="70">状态</th>
				<th width="100">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pagination.datas}" var="item">
			<tr class="text-c">
				<td><input type="checkbox" value="${item.id}" name="ids"></td>
				<td><u style="cursor:pointer" class="text-primary" onclick="optShow('张三','v_show.do','10001','360','400')">${item.username}</u></td>
				<td>男</td>
				<td>${item.phone}</td>
				<td>${item.email}</td>
				<td class="text-l">${item.registerIp}</td>
				<td>${item.registerTime}</td>
				<td class="td-status">
					<c:if test="${item.disabled}">
						<span class="label label-default radius">已禁用</span>
					</c:if>
					<c:if test="${!item.disabled}">
						<span class="label label-success radius">已启用</span>
					</c:if>
				<td class="td-manage">
					<c:if test="${item.disabled}">
						<a style="text-decoration:none" onClick="member_start(this,'${item.id}')" href="javascript:;" title="启用"><i class="Hui-iconfont">&#xe6e1;</i></a> 
					</c:if>
					<c:if test="${!item.disabled}">
						<a style="text-decoration:none" onClick="member_stop(this,'${item.id}')" href="javascript:;" title="停用"><i class="Hui-iconfont">&#xe631;</i></a> 
					</c:if>
				<a title="编辑" href="javascript:;" onclick="optEdit('编辑','v_edit.do?id=${item.id}','${item.id}','','510')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a> <a title="删除" href="javascript:;" onclick="optDel(this,'${item.id}')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a></td>
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
function optShow(title,url,id,w,h){
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
/*用户-停用*/
function member_stop(obj,id){
	layer.confirm('确认要停用吗？',function(index){
		$.ajax({
			type: "post",
			url: "o_user_disable.do",
		    dataType: "json",
		    data: {id:id},
		    success: function(result){
		    	$(obj).parents("tr").find(".td-manage").prepend('<a style="text-decoration:none" onClick="member_start(this,id)" href="javascript:;" title="启用"><i class="Hui-iconfont">&#xe6e1;</i></a>');
				$(obj).parents("tr").find(".td-status").html('<span class="label label-defaunt radius">已停用</span>');
				$(obj).remove();
				layer.msg('已停用!',{icon: 5,time:1000});
		     }
		});
	});
}

/*用户-启用*/
function member_start(obj,id){
	layer.confirm('确认要启用吗？',function(index){
		$.ajax({
			type: "post",
			url: "o_user_enable.do",
		    dataType: "json",
		    data: {id:id},
		    success: function(result){
		    	$(obj).parents("tr").find(".td-manage").prepend('<a style="text-decoration:none" onClick="member_stop(this,id)" href="javascript:;" title="停用"><i class="Hui-iconfont">&#xe631;</i></a>');
				$(obj).parents("tr").find(".td-status").html('<span class="label label-success radius">已启用</span>');
				$(obj).remove();
				layer.msg('已启用!',{icon: 6,time:1000});
		     }
		});
	});
}
</script> 
</body>
</html>