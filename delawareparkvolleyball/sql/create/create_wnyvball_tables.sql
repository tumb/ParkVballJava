-- Creating tables for WNYVBALL web site that Kai made. 

-- This Person table isn't immediately needed.
create table person
(
id int not null auto_increment ,
first_name varchar(50) not null ,
last_name varchar(50) ,
gender varchar(1) not null , -- M or F only
primary key (id)
) ; 

-- This is needed. 
-- It is used for both played and not yet played matches
create table match
(
id int not null auto_increment ,
play_date date not null , 
team_A_id int not null ,
team_B_id int not null ,
league_id int not null ,
week_number int , 
team_A_wins int ,
team_B_wins int ,
primary key (id)
) ; 

-- Shouldn't need this since Kai created tblLeague
create table dpva.league
(
id int not null auto_increment ,
day_of_week varchar(10) not null ,
year int not null ,
primary key (id)
) ; 

-- TEAM
-- Not sure if Kai has this already but don't think so. 
-- Other fields like - paid etc would be a good idea. 
drop table team ; 
create table team
(
id int not null auto_increment ,
league_id int not null ,
team_captain int, 
team_name varchar(20),
primary key (id)
) ; 

create table match1 -- 'match us a key word not allowed by mySql
(
id int not null auto_increment ,
week_number int , 
team_A_wins int ,
team_B_wins int ,
play_date date not null , 
league_id int not null ,
team_A_id int not null , 
team_B_id int not null ,
primary key (id)
) ; 


-- for finding out who's on the team.
create table team_roster
(
id int not null auto_increment ,
league_id int not null ,
team_id int not null,
person_id int not null,
primary key (id)
) ;

-- the match result table might cover this with some flag that says - haven't play yet
-- Or even just having 0 for all wins.
create table schedule
(
id int not null auto_increment ,
play_date date not null , 
team_A_id int not null ,
team_B_id int not null ,
league_id int not null ,
primary key (id)
) ;

-- standings can be computed from the data in the match table
-- schedule should be easily displayed from the match table