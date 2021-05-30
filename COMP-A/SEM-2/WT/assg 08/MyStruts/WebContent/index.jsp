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
<s:form action="get" method="post">
<s:textfield label="UserName" name="username"></s:textfield>
<s:password name="password" label="PassWord"></s:password>
<a href="newuser.jsp">Registar</a>
<s:submit value="submit"></s:submit>

</s:form>
</body>
</html>