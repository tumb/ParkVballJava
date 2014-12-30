<!DOCTYPE html>
<html>
<head><title>Summer 2015</title></head>
<%@ include file="html\\TopMenu.html" %>
<script> document.getElementById("homeLink").style.background = "Gray" ; </script>

<body>
<style>
html {
    background-image: url("images\\BobAndSarah1.jpg") ;
    /* Adding center center is killing the image */
    width: 50% ; 
    height: 50% ; 
}
</style>
<h1>Teams</h1>
<%@ page import="com.delawareparkvolleyball.schedule.*" %>
<p>
<% League league = League.CreateExampleLeague(5) ; %>
<%= league.teamsTable() %>
</p>
</body></html>