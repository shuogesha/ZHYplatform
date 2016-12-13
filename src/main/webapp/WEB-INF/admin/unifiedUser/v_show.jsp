<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../common/meta.jsp"%>
<title>用户查看</title>
</head>
<body>
<div class="cl pd-20" style=" background-color:#5bacb6">
  <img class="avatar size-XL l" src="/static/h-ui/images/ucnter/avatar.png">
  <dl style="margin-left:80px; color:#fff">
    <dt><span class="f-18">${bean.username}</span> <span class="pl-10 f-12">余额：40</span></dt>
    <dd class="pt-10 f-12" style="margin-left:0">这家伙很懒，什么也没有留下</dd>
  </dl>
</div>
<div class="pd-20">
  <table class="table">
    <tbody>
      <tr>
        <th class="text-r" width="80">性别：</th>
        <td></td>
      </tr>
      <tr>
        <th class="text-r">手机：</th>
        <td>${bean.phone}</td>
      </tr>
      <tr>
        <th class="text-r">邮箱：</th>
        <td>${bean.email}</td>
      </tr>
      </tr>
    </tbody>
  </table>
</div>
<script type="text/javascript" src="/js/jquery.min.js"></script> 
<script type="text/javascript" src="/static/h-ui/js/H-ui.js"></script>
<script type="text/javascript" src="/static/h-ui.admin/js/H-ui.admin.js"></script>
</body>
</html>