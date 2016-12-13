<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../common/meta.jsp"%>
<link rel="stylesheet" href="/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<title>栏目树管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 文章管理 <a class="btn btn-success btn-refresh radius r" style="line-height:1.6em;margin-top:3px" href="javascript:void(0);" onclick="location.replace(location.href)" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<table class="table">
	<tr>
		<td width="200" class="va-t"><ul id="treeDemo" class="ztree"></ul></td>
		<td class="va-t"><IFRAME ID="testIframe" Name="testIframe" FRAMEBORDER=0 SCROLLING=AUTO width=100%  height=700px SRC="v_list.do"></IFRAME></td>
	</tr>
</table>
<%@ include file="../common/footer.jsp"%>
<script type="text/javascript" src="/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script> 
<script type="text/javascript">
var setting = {
		view: {
			selectedMulti: true
		},
		async: {
			enable: true,
			url:"../channel/getNodes.do",
			autoParam:["id", "name=n", "level=level", "type=type"],
			otherParam:{"otherParam":"zTreeAsyncTest"},
			dataFilter: filter
		},
		data: {
			simpleData: {
				enable:true,
				idKey: "id",
				pIdKey: "pId",
				rootPId: ""
			}
		},
		callback: {
			beforeClick: beforeClick
		}
};	

function filter(treeId, parentNode, childNodes) {
	if (!childNodes) return null;
	for (var i=0, l=childNodes.length; i<l; i++) {
		childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
	}
	return childNodes;
}

function beforeClick(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("tree");
	var child="1";
	if(!treeNode.isParent){
		child = "";
	}
	$("#testIframe").attr("src","v_list.do?channelId="+treeNode.id+"&child="+child);
	if (treeNode.isParent) {
		zTree.expandNode(treeNode);
		return false;
	} else {
		return true;
	}
}

function refreshNode(e) {
	var zTree = $.fn.zTree.getZTreeObj("tree"),
	type = e.data.type,
	silent = e.data.silent,
	nodes = zTree.getSelectedNodes();
	if (nodes.length == 0) {
		alert("请先选择一个父节点");
	}
	for (var i=0, l=nodes.length; i<l; i++) {
		zTree.reAsyncChildNodes(nodes[i], type, silent);
		if (!silent) zTree.selectNode(nodes[i]);
	}
}

//var zNodes = [{ id:0, pId:-1, name:"根节点",type:"", open:true}];
		
var code;
function showCode(str) {
	if (!code) code = $("#code");
	code.empty();
	code.append("<li>"+str+"</li>");
}
		
$(document).ready(function(){
	$.fn.zTree.init($("#treeDemo"), setting);
});
</script>
</body>
</html>