package ir.hajj.virtualhajj_app.Models;

public class Match {
    int matchID;
    String question;
    String ch1;
    String ch2;
    String ch3;
    String ch4;
    int correct;
    int select;

    public Match() {
    }

    public int getMatchID() {
        return matchID;
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCh1() {
        return ch1;
    }

    public void setCh1(String ch1) {
        this.ch1 = ch1;
    }

    public String getCh2() {
        return ch2;
    }

    public void setCh2(String ch2) {
        this.ch2 = ch2;
    }

    public String getCh3() {
        return ch3;
    }

    public void setCh3(String ch3) {
        this.ch3 = ch3;
    }

    public String getCh4() {
        return ch4;
    }

    public void setCh4(String ch4) {
        this.ch4 = ch4;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getSelect() {
        return select;
    }

    public void setSelect(int select) {
        this.select = select;
    }
}
