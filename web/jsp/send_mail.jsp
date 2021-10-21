<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>找回密码</title>
<link rel="icon" href="/image/doubanicon.ico" type="image/x-icon">
</head>
<body>
<!-- 发送邮件找回密码 -->
	<form action="/findBackServlet">
		填写用户名<br>
		<input type= "text" name ="send_email"><br>
		<input type="submit" >
	</form>
</body>
</html>