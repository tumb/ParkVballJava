-- test league data
insert into dpva.league (day_of_week, year) values ('Thursday', 2014) ; 
-- verify league insertion
select * from dpva.league order by year ; 

-- test team data
insert into dpva.team (league_id, man_id, woman_id, team_name) values (1, (select id from dpva.person where first_name = 'Thom'), (select id from dpva.person where first_name = 'Karen'), 'Karen') ; 
insert into dpva.team (league_id, man_id, woman_id, team_name) values (1, (select id from dpva.person where first_name = 'Dan'), (select id from dpva.person where first_name = 'Lauren'), 'Lauren') ; 
insert into dpva.team (league_id, man_id, woman_id, team_name) values (1, (select id from dpva.person where first_name = 'Brian'), (select id from dpva.person where first_name = 'Katy'), 'Katy') ; 
insert into dpva.team (league_id, man_id, woman_id, team_name) values (1, (select id from dpva.person where first_name = 'Kai'), (select id from dpva.person where first_name = 'Alyssa'), 'Kai') ; 
insert into dpva.team (league_id, man_id, woman_id, team_name) values (1, (select id from dpva.person where first_name = 'Richard'), (select id from dpva.person where last_name = 'Fontana'), 'Rick') ; 
insert into dpva.team (league_id, man_id, woman_id, team_name) values (1, (select id from dpva.person where first_name = 'Scott'), (select id from dpva.person where last_name = 'McClure'), 'Scott') ; 
insert into dpva.team (league_id, man_id, woman_id, team_name) values (1, (select id from dpva.person where first_name = 'Xu'), (select id from dpva.person where first_name = 'Barb'), 'Barb') ; 
insert into dpva.team (league_id, man_id, woman_id, team_name) values (1, (select id from dpva.person where first_name = 'Hector'), (select id from dpva.person where first_name = 'Lindsey'), 'Lindsey') ; 
insert into dpva.team (league_id, man_id, woman_id, team_name) values (1, (select id from dpva.person where first_name = 'Aidan'), (select id from dpva.person where first_name = 'Kristie'), 'Aidan') ; 
-- verify teams
select t.id, team_name, pf.first_name, pm.first_name, l.day_of_week, l.year 
from team t 
join person pf on t.woman_id = pf.id
join person pm on t.man_id = pm.id
join league l on t.league_id = l.id
; 

-- Don't need the teams in league table. Skip

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
insert into match_result (play_date, team_A_id, team_B_id, league_id, team_A_wins, team_B_wins) 
values ('2014-07-31', 1, 2, 1, 2, 0) ; 
insert into match_result (play_date, team_A_id, team_B_id, league_id, team_A_wins, team_B_wins) 
values ('2014-07-31', 1, 3, 1, 2, 0) ; 
insert into match_result (play_date, team_A_id, team_B_id, league_id, team_A_wins, team_B_wins) 
values ('2014-07-31', 2, 4, 1, 2, 0) ; 
insert into match_result (play_date, team_A_id, team_B_id, league_id, team_A_wins, team_B_wins) 
values ('2014-07-31', 3, 7, 1, 2, 0) ; 
insert into match_result (play_date, team_A_id, team_B_id, league_id, team_A_wins, team_B_wins) 
values ('2014-07-31', 5, 6, 1, 1, 1) ; 
insert into match_result (play_date, team_A_id, team_B_id, league_id, team_A_wins, team_B_wins) 
values ('2014-07-31', 8, 6, 1, 0, 2) ; 
insert into match_result (play_date, team_A_id, team_B_id, league_id, team_A_wins, team_B_wins) 
values ('2014-07-31', 8, 9, 1, 0, 2) ; 
insert into match_result (play_date, team_A_id, team_B_id, league_id, team_A_wins, team_B_wins) 
values ('2014-07-31', 9, 4, 1, 0, 2) ; 
insert into match_result (play_date, team_A_id, team_B_id, league_id, team_A_wins, team_B_wins) 
values ('2014-07-31', 5, 7, 1, 2, 0) ; 
-- verify match result data
select pAf.first_name, pBf.first_name, mr.team_A_wins, mr.team_B_wins, mr.play_date, l.day_of_week, l.year from match_result mr
join team tA on mr.team_A_id = tA.id
join team tB on mr.team_B_id = tB.id
join person pAf on tA.woman_id = pAf.id
join person pBf on tB.woman_id = pBf.id
join league l on mr.league_id = l.id
 ; 
 -- test schedule data - same week before results happened
insert into schedule (play_date, team_A_id, team_B_id, league_id) 
values ('2014-07-31', 1, 2, 1) ; 
insert into schedule (play_date, team_A_id, team_B_id, league_id) 
values ('2014-07-31', 1, 3, 1) ; 
insert into schedule (play_date, team_A_id, team_B_id, league_id) 
values ('2014-07-31', 2, 4, 1) ; 
insert into schedule (play_date, team_A_id, team_B_id, league_id) 
values ('2014-07-31', 3, 7, 1) ; 
insert into schedule (play_date, team_A_id, team_B_id, league_id) 
values ('2014-07-31', 5, 6, 1) ; 
insert into schedule (play_date, team_A_id, team_B_id, league_id) 
values ('2014-07-31', 8, 6, 1) ; 
insert into schedule (play_date, team_A_id, team_B_id, league_id) 
values ('2014-07-31', 8, 9, 1) ; 
insert into schedule (play_date, team_A_id, team_B_id, league_id) 
values ('2014-07-31', 9, 4, 1) ; 
insert into schedule (play_date, team_A_id, team_B_id, league_id) 
values ('2014-07-31', 5, 7, 1) ; 
--verify schedule data
select tA.team_name, tb.team_name, s.play_date, l.day_of_week, l.year 
from schedule s
join team tA on s.team_A_id = tA.id
join team tB on s.team_B_id = tB.id
join league l on s.league_id = l.id
 ; 
