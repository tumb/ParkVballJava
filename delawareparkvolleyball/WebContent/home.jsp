<!DOCTYPE html>
<html>
<head><title>Summer 2015</title></head>
<body>
<h1>Teams</h1>
<%@ page import="com.delawareparkvolleyball.schedule.*" %>
<p>
<% League league = League.CreateExampleLeague(5) ; %>
<%= league.teamsTable() %>
</p>
</body></html>