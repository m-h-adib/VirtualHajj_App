package ir.hajj.virtualhajj_app.Models;

public class Question {
    int questionID;
    String ques;
    String dateD;
    String dateP;
    String reply;
    String username;
    int groupID;

    public Question() {
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        questionID = questionID;
    }

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        ques = ques;
    }

    public String getDateD() {
        return dateD;
    }

    public void setDateD(String dateD) {
        dateD = dateD;
    }

    public String getDateP() {
        return dateP;
    }

    public void setDateP(String dateP) {
        dateP = dateP;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        reply = reply;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        username = username;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        groupID = groupID;
    }
}
