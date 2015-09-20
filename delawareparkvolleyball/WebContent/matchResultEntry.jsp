<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page import="com.delawareparkvolleyball.view.*" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Enter Match Results</title>
<%@ include file="html\\TopMenu.html" %>
<script> document.getElementById("matchResultEntry").style.background = "Gray" ; </script></head>
<style>
html {
    background-image: url("images\\BobAndSarah1.jpg") ;
    /* Adding center center is killing the image */
    background-attachment: fixed; 
	background-repeat: no-repeat; 
	-moz-background-size: auto 100%; 
    background-size: auto 100% ;
    background-position: center center ; 
}
</style>
<h1>Select League</h1>
<body>
<form action="ResultEditor" method="GET">
<p>
<%=HtmlGeneration.makeLeagueSelectionTable(request.getSession())%>
</p>
<input type="submit" name="selectLeague" value="Select League" />
</form>
<form action="ResultEditor" method="POST">
<h1>
<%= HtmlGeneration.getSessionLeagueName(request.getSession()) %>
</h1>
<table>
<%= HtmlGeneration.matchResultEditTable(request.getSession()) %>
</table>
<input type="submit" name="saveResults" value="Save Results" />
</form>
</body>
</html>