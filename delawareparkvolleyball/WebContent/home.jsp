<!DOCTYPE html>
<html>
<head><title>Summer 2015</title></head>
<%@ include file="html\\NavigationBar.html" %>
<%@ include file="html\\TopMenu.html" %>
<script src="./js/jquery-2.1.3.js"></script>
<link rel="stylesheet" type="text/css" href="./DataTables-1.10.5/media/css/jquery.dataTables.css">
<script type="text/javascript" charset="utf8" src="./DataTables-1.10.5/media/js/jquery.dataTables.js">
</script>

<script> document.getElementById("homeLink").style.background = "Gray" ; </script>

<body>
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
<h1>Teams</h1>
<script>
$(document).ready(function(){
    $("p").click(function(){
        $(this).hide();
    });
});
</script>
<script>
$(document).ready(function() {
    $('#league').DataTable();
} );
</script>

<%@ page import="com.delawareparkvolleyball.schedule.*" %>
<p>
Test of JQuery. Click on this and it should disappear.
</p>
<p>
<% League league = League.CreateExampleLeague(5) ; %>
<%= league.teamsTable() %>
</p>
</body></html>