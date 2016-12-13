<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<dl class="permission-list perm-container">
<input type="hidden" name="perms" value="/index.do,/top.do"/>
<dt>
	<label>
		<input type="checkbox" value="/admin_config.do" name="perms" id="perms">
		管理员管理</label>
</dt>
<dd>
	<dl class="cl permission-list2 perm-layout-1">
		<dt>
			<label class="">
				<input type="checkbox" value="/role/v_list.do" name="perms">
				角色管理</label>
		</dt>
		<dd class="perm-layout-2">
			<label class="">
				<input type="checkbox" value="/role/v_add.do" name="perms"/>
				添加</label>
			<label class="">
				<input type="checkbox" value="/role/v_edit.do" name="perms"/>
				修改</label>
			<label class="">
				<input type="checkbox" value="/role/o_delete.do" name="perms"/>
				删除</label>
		</dd>
	</dl>
</dd>
</dl>
