<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>蓝源Eloan-P2P平台->我要借款</title>
<#include "common/links-tpl.ftl" />
<script type="text/javascript" src="/js/plugins/jquery.twbsPagination.min.js"></script>
<script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
<script type="text/javascript" src="/js/plugins-override.js"></script>
<script type="text/javascript">
	$(function() {
		var searchForm = $("#searchForm");
		var gridBody = $(".display_table");
		searchForm.ajaxForm(function(data){
			gridBody.hide();
			gridBody.html(data);
			gridBody.show(500);
		});
		searchForm.submit();
		
		$("input[name=state]").change(function(){
			$("#currentPage").val(1);
			searchForm.submit();
		});
		$("input[name=bidRequestType]").change(function(){
			$("#currentPage").val(1);
			searchForm.submit();
		});
	});
</script>
</head>
<body>
	<!-- 网页头信息 -->
	<#include "common/head-tpl.ftl" />
	<#assign currentNav="invest" />
	<!-- 网页导航 -->
	<#include "common/navbar-tpl.ftl" />
	
	<!-- 网页内容 -->
	<div class="container" style="min-height: 500px;">
		<h4 class="page-title">投资列表</h4>
		<form action="/invest_list" id="searchForm" method="POST">
			<input type="hidden" name="currentPage" id="currentPage" value="1">
			<div style="margin: 20px 0px;">
					<span class="text-muted">标的状态</span><div style="margin-left: 30px" class="btn-group" data-toggle="buttons">
				  <label class="btn btn-default active">
				    <input type="radio" name="state" value="-1" autocomplete="off" checked />&emsp;全部&emsp;
				  </label>
				  <label class="btn btn-default">
				    <input type="radio" name="state" value="2" autocomplete="off" />&emsp;招标中&emsp;
				  </label>
				  <label class="btn btn-default">
				    <input type="radio" name="state" value="8" autocomplete="off" />&emsp;已完成&emsp;
				  </label>
				</div>
			</div>
			<div style="margin: 20px 0px;">
					<span class="text-muted">标的类型</span><div style="margin-left: 30px" class="btn-group" data-toggle="buttons">
				  <label class="btn btn-default active">
				    <input type="radio" name="bidRequestType" value="0" autocomplete="off"  />&emsp;信用标&emsp;
				  </label>
				  <label class="btn btn-default">
				    <input type="radio" name="bidRequestType" value="1" autocomplete="off" />&emsp;债权转让标&emsp;
				  </label>
				</div>
			</div>
		</form>
		<table class="table el-table table-hover display_table">
		</table>
		<div style="text-align: center;">
			<ul id="pagination" class="pagination"></ul>
		</div>
	</div>

	<!-- 网页版权 -->
	<#include "common/footer-tpl.ftl" />
</body>
</html>