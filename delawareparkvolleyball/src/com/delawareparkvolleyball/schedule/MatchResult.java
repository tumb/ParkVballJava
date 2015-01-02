package com.delawareparkvolleyball.schedule;

/**
 * This should be one row in the database - with team and league being links to the 
 * original objects.
 * @author thom
 *
 */
public class MatchResult {
 private Team teamA ; 
 private Team teamB ; 
 private League league ; 
 private int teamAwins ; // Implied - every team A win is a team B loss.
 private int teamBwins ; 
 
}
