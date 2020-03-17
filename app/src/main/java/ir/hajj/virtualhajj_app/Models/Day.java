package ir.hajj.virtualhajj_app.Models;

public class Day {
    int dailyID;
    String title;
    String time;
    String content;
    String type;

    public Day() {
    }

    public int getDailyID() {
        return dailyID;
    }

    public void setDailyID(int dailyID) {
        this.dailyID = dailyID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
