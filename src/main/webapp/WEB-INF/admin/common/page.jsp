<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="p" value="${pagination}"></c:set>
<c:if test="${p.totalCount gt 0}">
<div class="dataTables_wrapper no-footer">
	<div class="dataTables_paginate paging_simple_numbers">
		<a  class="paginate_button previous" onclick="gotoPage('${p.upPage}');">上一页</a>
		<c:forEach begin="1" end="${p.totalPage}" var="i">
			<c:if test="${(i <= 1)&&(p.pageNo > 5)}">
				<a onclick="gotoPage('1');" class="paginate_button  ${(i==p.pageNo)?'current':''}">1</a>
			</c:if>
			<c:if test="${((p.pageNo-i) <= 4)&&((p.pageNo-i) >= -4)}">
				<a onclick="gotoPage('${i}');" class="paginate_button ${(i==p.pageNo)?'current':''}">${i}</a>
			</c:if>
			<c:if test="${(i >= p.totalPage)&&(((p.totalPage+1)-p.pageNo) > 5)}">
				<a onclick="gotoPage('${p.totalPage}');" class="paginate_button ${(i==p.pageNo)?'current':''}">${p.totalPage}</a>
			</c:if>
		</c:forEach>
		<a class="paginate_button next" onclick="gotoPage('${p.nextPage}');">下一页</a>
	</div>
</div>
</c:if>
<script type="text/javascript">
function gotoPage(pageNo){  
    $('#pageNo').val(pageNo);
	$('#submitBtn').click();
}  
</script>