<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../common/meta.jsp"%>
<title>文章管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 文章管理 <a class="btn btn-success btn-refresh radius r" style="line-height:1.6em;margin-top:3px" href="javascript:void(0);" onclick="location.replace(location.href)" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<form id="tableForm" action="v_list.do">
	<div class="text-c"> 
		<input type="text" class="input-text" style="width:250px" placeholder="名称" value="${name}" name="name">
		<button type="submit" class="btn btn-success radius" id="submitBtn"><i class="Hui-iconfont">&#xe665;</i> 搜</button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l"><a href="javascript:;" onclick="datadel()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a> <a href="javascript:;" onclick="optAdd('添加','v_add.do?channelId=${channelId}','','510')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加</a></span> <span class="r">共有数据：<strong>${pagination.totalCount}</strong> 条</span> </div>
	<div class="mt-20">
	<input type="hidden" name="ids" id="ids" value=""/>
	<input type="hidden" id="pageNo" name="pageNo" value="${pagination.pageNo}"/>
	<input type="hidden" id="channelId" name="channelId" value="${channelId}"/>
	<input type="hidden" id="child" name="child" value="${child}"/>
	<table class="table table-border table-bordered table-hover table-bg table-sort">
		<thead>
			<tr class="text-c">
				<th width="25"><input type="checkbox" name="" value=""></th>
				<th width="100">标题</th>
				<th width="100">所属栏目</th>
				<th width="100">发布时间</th>
				<th width="100">浏览量</th>
				<th width="100">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pagination.datas}" var="item">
			<tr class="text-c">
				<td><input type="checkbox" value="${item.id}" name="ids"></td>
				<td>${item.name}</td>
				<td>${item.channel.name}</td>
				<td>${item.dateline}</td>
				<td>${item.count.views}</td>
				<td class="td-manage"><a title="编辑" href="javascript:;" onclick="optEdit('编辑','v_edit.do?id=${item.id}','','510')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a> <a title="删除" href="javascript:;" onclick="optDel(this,'${item.id}')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a></td>
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
	if($("#child").val()>0){
		parent.layer.alert("请选择子节点");
		return;
	}
	parent.layer_show(title,url,w,h);
}
function optEdit(title,url,id,w,h){
	parent.layer_show(title,url,w,h);
}
function optShow(title,url,id,w,h){
	parent.layer_show(title,url,w,h);
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
</script> 
</body>
</html>