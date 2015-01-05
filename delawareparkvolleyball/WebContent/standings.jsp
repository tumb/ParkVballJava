<!DOCTYPE html>
<html>
<head><title>Summer 2015</title></head>
<%@ include file="html\\TopMenu.html" %>
<script> document.getElementById("standingsLink").style.background = "Gray" ; </script>

<body>
<style>
html {
    background-image: url("images\\JeffAndMolly2.jpg") ;
    /* Adding center center is killing the image */
    background-attachment: fixed; 
	background-repeat: no-repeat; 
	-moz-background-size: auto 100%; 
    background-size: auto 100% ;
    background-position: center center ; 
}
</style>
<h1>Results</h1>
<%@ page import="com.delawareparkvolleyball.schedule.*" %>
<%@ page import="com.delawareparkvolleyball.database.*" %>
<p>
<%= MySqlReadWriteUpdate.htmlOfStandings(1) %>
</p>
</body></html>