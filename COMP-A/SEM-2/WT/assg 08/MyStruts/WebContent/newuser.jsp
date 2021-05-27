<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<s:form action="registar">
<s:textfield label="Username" name="username"></s:textfield>
<s:password label="Password" name="password"></s:password>
<s:submit value="Register"></s:submit>
</s:form>
</body>
</html>