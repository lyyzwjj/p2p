<ul id="menu" class="list-group">
	<li class="list-group-item">
		<a href="#" data-toggle="collapse" data-target="#usermanage_detail"><span>用户管理</span></a>
		<ul class="in" id="usermanage_detail">
			<li class=""><a href="/real_auth_list">平台用户管理</a></li>
			<li class=""><a href="/recharge_offline_list">员工管理</a></li>
		</ul>
	</li>
	<li class="list-group-item">
		<a href="#" data-toggle="collapse" data-target="#permissionmanage_detail"><span>安全管理</span></a>
		<ul class="in" id="permissionmanage_detail">
			<li class="systemDictionary"><a href="/systemDictionary_list"><span>系统数据字典目录</span></a></li>
			<li class="systemDictionaryItem"><a href="/systemDictionaryItem_list"><span>系统数据字典明细</span></a></li>
			<li><a href="/permission_list"><span>权限管理</span></a></li>
			<li><a href="#"><span>角色管理</span></a></li>
			<li><a href="#"><span>菜单管理</span></a></li>
			<li class="ipLog"><a href="/ipLog"><span>登录历史</span></a></li>
		</ul>
	</li>
	<li class="list-group-item">
		<a href="#" data-toggle="collapse" data-target="#auditmanage_detail">
			<span>审核项目</span>
		</a>
		<ul class="in" id="auditmanage_detail">
			<li class="realAuth"><a href="/realAuth">实名认证审核</a></li>
			<li class="videoAuth"><a href="/videoAuth">视频认证审核</a></li>
			<li class="userFileAuth"><a href="/userFileAuth">认证材料审核</a></li>
			<li class="bidrequest_publishaudit_list"><a href="/bidrequest_publishaudit_list">发标前审核</a></li>
			<li class="bidrequest_audit1_list"><a href="/bidrequest_audit1_list">满标一审</a></li>
			<li class="bidrequest_audit2_list"><a href="/bidrequest_audit2_list">满标二审</a></li>
			<li class="rechargeOffline"><a href="/rechargeOffline">线下充值审核</a></li>
			<li class="moneyWithdraw"><a href="/moneyWithdraw">提现审核</a></li>
		</ul>
	</li>
	<li class="list-group-item">
		<a href="#" data-toggle="collapse" data-target="#systemmanage_detail">
			<span>平台管理</span>
		</a>
		<ul class="in" id="systemmanage_detail">
			<li class="companyBank"><a href="/companyBank_list">平台账号管理</a></li>
			<li><a href="/real_auth_list">系统账户流水</a></li>
			<li><a href="/bid_request_list"> <span>系统设置</span></a></li>
			<li><a href="/bid_request_list"> <span>企业资讯</span></a></li>
			<li><span><a href="#">友情链接</a></span></li>
			<li><span><a href="#">广告设置</a></span></li>
		</ul>
	</li>
</ul>

<#if currentMenu??>
<script type="text/javascript">
	$(".in li.${currentMenu}").addClass("active");
</script>
</#if>