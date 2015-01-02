create table dpva.person
(
id int not null auto_increment ,
first_name varchar(50) not null ,
last_name varchar(50) ,
gender varchar(1) not null , -- M or F only
primary key (id)
) ; 

create table dpva.match_result
(
id int not null auto_increment ,
team_A_id int not null ,
team_B_id int not null ,
league_id int not null ,
division_id int ,
team_A_wins int ,
time_B_wins int ,
primary key (id)
) ; 

create table dpva.league
(
id int not null auto_increment ,
day_of_week varchar(10) not null ,
year int not null ,
primary key (id)
) ; 

create table dpva.team
(
id int not null auto_increment ,
league_id int not null ,
man_id int not null,
woman_id int not null, 
primary key (id)
) ; 

create table dpva.teams_in_league
(
id int not null auto_increment ,
league_id int not null ,
team_id int not null,
primary key (id)
) ;