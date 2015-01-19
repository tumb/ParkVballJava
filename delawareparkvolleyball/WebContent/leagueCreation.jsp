<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page import="com.delawareparkvolleyball.schedule.*" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>League Creation</title>
<%@ include file="html\\TopMenu.html" %>
<script> document.getElementById("leagueCreationLink").style.background = "Gray" ; </script></head>
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
<h1>Add a League</h1>
<body>
<form action="LeagueEditor" method="POST">
<table border="0">
<tr><td>Day of Week </td><td><input type="text" name="day_of_week" value="Thursday"></td>
</tr>
<tr><td>Year </td><td><input type="text" name="year" value="2015"></td>
</tr>
<tr><td>Divisions </td><td><input type="text" name="division_count" value="1" /></td>
</tr>
</table>
<br/>
<table border="1">
<tr><td colspan="2">Man's Name</td><td colspan="2">Woman's Name</td><td>Team Name</td></tr>
<tr><td>First</td><td>Last</td><td>First</td><td>Last</td><td>Name</td></tr>
<tr>
	<td><input type="text" name="man_first_1"></td>
	<td><input type="text" name="man_last_1"></td>
	<td><input type="text" name="woman_first_1"></td>
	<td><input type="text" name="woman_last_1"></td>
	<td><input type="text" name="team_name_1"></td>
</tr>
<tr>
	<td><input type="text" name="man_first_2"></td>
	<td><input type="text" name="man_last_2"></td>
	<td><input type="text" name="woman_first_2"></td>
	<td><input type="text" name="woman_last_2"></td>
	<td><input type="text" name="team_name_2"></td>
</tr>
<tr>
	<td><input type="text" name="man_first_3"></td>
	<td><input type="text" name="man_last_3"></td>
	<td><input type="text" name="woman_first_3"></td>
	<td><input type="text" name="woman_last_3"></td>
	<td><input type="text" name="team_name_3"></td>
</tr>
<tr>
	<td><input type="text" name="man_first_4"></td>
	<td><input type="text" name="man_last_4"></td>
	<td><input type="text" name="woman_first_4"></td>
	<td><input type="text" name="woman_last_4"></td>
	<td><input type="text" name="team_name_4"></td>
</tr>
<tr>
	<td><input type="text" name="man_first_5"></td>
	<td><input type="text" name="man_last_5"></td>
	<td><input type="text" name="woman_first_5"></td>
	<td><input type="text" name="woman_last_5"></td>
	<td><input type="text" name="team_name_5"></td>
</tr>
<tr>
	<td><input type="text" name="man_first_6"></td>
	<td><input type="text" name="man_last_6"></td>
	<td><input type="text" name="woman_first_6"></td>
	<td><input type="text" name="woman_last_6"></td>
	<td><input type="text" name="team_name_6"></td>
</tr>
<tr>
	<td><input type="text" name="man_first_7"></td>
	<td><input type="text" name="man_last_7"></td>
	<td><input type="text" name="woman_first_7"></td>
	<td><input type="text" name="woman_last_7"></td>
	<td><input type="text" name="team_name_7"></td>
</tr>
<tr>
	<td><input type="text" name="man_first_8"></td>
	<td><input type="text" name="man_last_8"></td>
	<td><input type="text" name="woman_first_8"></td>
	<td><input type="text" name="woman_last_8"></td>
	<td><input type="text" name="team_name_8"></td>
</tr>
<tr>
	<td><input type="text" name="man_first_9"></td>
	<td><input type="text" name="man_last_9"></td>
	<td><input type="text" name="woman_first_9"></td>
	<td><input type="text" name="woman_last_9"></td>
	<td><input type="text" name="team_name_9"></td>
</tr>
<tr>
	<td><input type="text" name="man_first_10"></td>
	<td><input type="text" name="man_last_10"></td>
	<td><input type="text" name="woman_first_10"></td>
	<td><input type="text" name="woman_last_10"></td>
	<td><input type="text" name="team_name_10"></td>
</tr>
<tr>
	<td><input type="text" name="man_first_11"></td>
	<td><input type="text" name="man_last_11"></td>
	<td><input type="text" name="woman_first_11"></td>
	<td><input type="text" name="woman_last_11"></td>
	<td><input type="text" name="team_name_11"></td>
</tr>
<tr>
	<td><input type="text" name="man_first_12"></td>
	<td><input type="text" name="man_last_12"></td>
	<td><input type="text" name="woman_first_12"></td>
	<td><input type="text" name="woman_last_12"></td>
	<td><input type="text" name="team_name_12"></td>
</tr>
<tr>
	<td><input type="text" name="man_first_13"></td>
	<td><input type="text" name="man_last_13"></td>
	<td><input type="text" name="woman_first_13"></td>
	<td><input type="text" name="woman_last_13"></td>
	<td><input type="text" name="team_name_13"></td>
</tr>
<tr>
	<td><input type="text" name="man_first_14"></td>
	<td><input type="text" name="man_last_14"></td>
	<td><input type="text" name="woman_first_14"></td>
	<td><input type="text" name="woman_last_14"></td>
	<td><input type="text" name="team_name_14"></td>
</tr>
<tr>
	<td><input type="text" name="man_first_15"></td>
	<td><input type="text" name="man_last_15"></td>
	<td><input type="text" name="woman_first_15"></td>
	<td><input type="text" name="woman_last_15"></td>
	<td><input type="text" name="team_name_15"></td>
</tr>
<tr>
	<td><input type="text" name="man_first_16"></td>
	<td><input type="text" name="man_last_16"></td>
	<td><input type="text" name="woman_first_16"></td>
	<td><input type="text" name="woman_last_16"></td>
	<td><input type="text" name="team_name_16"></td>
</tr>
<tr>
	<td><input type="text" name="man_first_17"></td>
	<td><input type="text" name="man_last_17"></td>
	<td><input type="text" name="woman_first_17"></td>
	<td><input type="text" name="woman_last_17"></td>
	<td><input type="text" name="team_name_17"></td>
</tr>
<tr>
	<td><input type="text" name="man_first_18"></td>
	<td><input type="text" name="man_last_18"></td>
	<td><input type="text" name="woman_first_18"></td>
	<td><input type="text" name="woman_last_18"></td>
	<td><input type="text" name="team_name_18"></td>
</tr>
<tr>
	<td><input type="text" name="man_first_19"></td>
	<td><input type="text" name="man_last_19"></td>
	<td><input type="text" name="woman_first_19"></td>
	<td><input type="text" name="woman_last_19"></td>
	<td><input type="text" name="team_name_19"></td>
</tr>
<tr>
	<td><input type="text" name="man_first_20"></td>
	<td><input type="text" name="man_last_20"></td>
	<td><input type="text" name="woman_first_20"></td>
	<td><input type="text" name="woman_last_20"></td>
	<td><input type="text" name="team_name_20"></td>
</tr>
<tr>
	<td><input type="text" name="man_first_21"></td>
	<td><input type="text" name="man_last_21"></td>
	<td><input type="text" name="woman_first_21"></td>
	<td><input type="text" name="woman_last_21"></td>
	<td><input type="text" name="team_name_21"></td>
</tr>
<tr>
	<td><input type="text" name="man_first_22"></td>
	<td><input type="text" name="man_last_22"></td>
	<td><input type="text" name="woman_first_22"></td>
	<td><input type="text" name="woman_last_22"></td>
	<td><input type="text" name="team_name_22"></td>
</tr>
<tr>
	<td><input type="text" name="man_first_23"></td>
	<td><input type="text" name="man_last_23"></td>
	<td><input type="text" name="woman_first_23"></td>
	<td><input type="text" name="woman_last_23"></td>
	<td><input type="text" name="team_name_23"></td>
</tr>
<tr>
	<td><input type="text" name="man_first_24"></td>
	<td><input type="text" name="man_last_24"></td>
	<td><input type="text" name="woman_first_24"></td>
	<td><input type="text" name="woman_last_24"></td>
	<td><input type="text" name="team_name_24"></td>
</tr>
<tr>
	<td><input type="text" name="man_first_25"></td>
	<td><input type="text" name="man_last_25"></td>
	<td><input type="text" name="woman_first_25"></td>
	<td><input type="text" name="woman_last_25"></td>
	<td><input type="text" name="team_name_25"></td>
</tr>
<tr>
	<td><input type="text" name="man_first_26"></td>
	<td><input type="text" name="man_last_26"></td>
	<td><input type="text" name="woman_first_26"></td>
	<td><input type="text" name="woman_last_26"></td>
	<td><input type="text" name="team_name_26"></td>
</tr>
<tr>
	<td><input type="text" name="man_first_27"></td>
	<td><input type="text" name="man_last_27"></td>
	<td><input type="text" name="woman_first_27"></td>
	<td><input type="text" name="woman_last_27"></td>
	<td><input type="text" name="team_name_27"></td>
</tr>
<tr>
	<td><input type="text" name="man_first_28"></td>
	<td><input type="text" name="man_last_28"></td>
	<td><input type="text" name="woman_first_28"></td>
	<td><input type="text" name="woman_last_28"></td>
	<td><input type="text" name="team_name_28"></td>
</tr>
<tr>
	<td><input type="text" name="man_first_29"></td>
	<td><input type="text" name="man_last_29"></td>
	<td><input type="text" name="woman_first_29"></td>
	<td><input type="text" name="woman_last_29"></td>
	<td><input type="text" name="team_name_29"></td>
</tr>
<tr>
	<td><input type="text" name="man_first_30"></td>
	<td><input type="text" name="man_last_30"></td>
	<td><input type="text" name="woman_first_30"></td>
	<td><input type="text" name="woman_last_30"></td>
	<td><input type="text" name="team_name_30"></td>
</tr>
</table>

<input type="submit" value="Submit" />
</form>
</body>
</html>