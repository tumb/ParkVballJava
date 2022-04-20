insert into person (first_name, last_name, gender) values ('Thom', 'Burnett', 'M') ; 
insert into person (first_name, last_name, gender) values ('Kai', 'Burnett', 'M') ; 
insert into person (first_name, last_name, gender) values ('Sam', 'Steinmetz', 'M') ; 
insert into person (first_name, last_name, gender) values ('Mike', 'Hauser', 'M') ; 

insert into match1 (league_id, week_number, play_date, team_A_id, team_B_id, team_A_wins, team_B_wins) values
(8, 4, '2015-06-03', 2, 3, 1, 2) ; 

-- test league data
-- Need to replace this with the fields in Kai's tblLeague
insert into tblLeague (day_of_week, year) values ('Thursday', 2014) ; 
-- verify league insertion
select * from tblLeague order by year ; 

-- test team data
insert into team (league_id, team_captain, team_name) values (8, (select id from person where first_name = 'Kai'), 'Kai Burnett') ; 
insert into team (league_id, team_captain, team_name) values (8, (select id from person where first_name = 'Sam'), 'Sam Steinmetz') ; 
insert into team (league_id, team_captain, team_name) values (8, (select id from person where first_name = 'Mike'), 'Mike Hauser') ; 
-- verify teams
select * from team ; 

insert into team_roster (league_id, team_id, person_id) values (8, 2, (select id from person where first_name = 'Kai')) ; 
insert into team_roster (league_id, team_id, person_id) values (8, 2, (select id from person where first_name = 'Thom')) ; 

-- Match results - these are from Thursday, 2014 July 31
--July 31		Result
		
--1 - 2	Karen - Lauren	Karen
--1 - 3	Karen - Katy	Karen
--2 - 4	Lauren - Kai	Lauren
--3 - 7	Barb - Katy	Katy
--5 - 6	Rick - Scott	split
--8 - 6	Lindsey - Scott	Scott
--8 - 9	Lindsey - Kristie	Kristie
--9 - 4	Kristie - Kai	Kai
--5 - 7	Rick - Barb	Rick
-- test match_result data
insert into match (play_date, team_A_id, team_B_id, league_id, week_number, team_A_wins, team_B_wins) 
values ('2014-07-31', 1, 2, 1, 4, 2, 0) ; 

-- verify match result data
select tA.team_name, tB.team_name, mr.team_A_wins, mr.team_B_wins, mr.play_date, l.day_of_week, l.year from match mr
join team tA on mr.team_A_id = tA.id
join team tB on mr.team_B_id = tB.id
join league l on mr.league_id = l.id
; 
