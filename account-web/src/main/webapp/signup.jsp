<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="com.dragon.mvnbook.account.service.*"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<title>SignUp Page</title>

<style type="text/css">
.text-field {
	position: absolute;
	left: 40%;
	background-color: rgb(255, 230, 220);
}

label {
	display: inline-table;
	width: 90px;
	margin: 0px 0px 10px 20px;
}

input {
	display: inline-table;
	width: 150px;
	margin: 0px 20px 10px 0px;
}

img {
	width: 150px;
	margin: 0px 20px 10px 110px;
}

h2 {
	margin: 20px 20px 20px 40px;
}

button {
	margin: 20px 20px 10px 110px
}
</style>
</head>

<body>
	<%
		//引入Spring的ApplicationContext类
		ApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(getServletContext());
		//加载后台的AccountService对象
		AccountService accountService = (AccountService) context
				.getBean("accountService");
		//使用该对象生成一个验证码的key
		String captchaKey = accountService.generateCaptchaKey();
	%>
	<div class="text-field">

		<h2>注册新账户</h2>
		<form name="signup" action="signup" method="post">
			<label>账户ID：</label>
			<input type="text" name="id"/><br/> 
			<label>Email：</label>
			<input type="text" name="email"/><br/> 
			<label>显示名称：</label>
			<input type="text" name="name"/><br/> 
			<label>密码：</label>
			<input type="password" name="password"/><br/> 
			<label>确认密码：</label>
			<input type="password" name="confirm_password"/><br/> 
			<label>验证码：</label>
			<input type="text" name="captcha_value"/><br/>
			<input type="hidden" name="captcha_key" value="<%=captchaKey%>"/> 
			<img src="<%=request.getContextPath()%>/captchaImage?key=<%=captchaKey%>"/><br/>
			<button>确认并提交</button>
		</form>
	</div>
</body>
</html>
