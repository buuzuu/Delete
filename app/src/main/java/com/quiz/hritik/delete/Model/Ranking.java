package com.quiz.hritik.delete.Model;

public class Ranking {

    private String userName;
    private long score;
    private String firstname;

    public Ranking() {
    }

    public Ranking(String userName, long score, String firstname) {
        this.userName = userName;
        this.score = score;
        this.firstname = firstname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
}
