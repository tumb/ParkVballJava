<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page import="com.delawareparkvolleyball.schedule.*" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Schedule</title>
<%@ include file="html\\TopMenu.html" %>
<script> document.getElementById("scheduleLink").style.background = "Gray" ; </script></head>
<body>

<body>
<style>
html {
    background-image: url("images\\JamieAndJon1.jpg") ;
    /* Adding center center is killing the image */
    background-attachment: fixed; 
	background-repeat: no-repeat; 
	-moz-background-size: auto 100%; 
    background-size: auto 100% ;
    background-position: center center ; 
}
</style>
<h1>Thursday Schedule</h1>

<%@ page import="com.delawareparkvolleyball.view.*" %>
<p>
<%= HtmlGeneration.htmlOfSchedule(1) %>
</p>
</body>
</html>