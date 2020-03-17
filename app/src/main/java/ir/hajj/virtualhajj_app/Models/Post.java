package ir.hajj.virtualhajj_app.Models;

public class Post {
    int itemID;
    int groupID;
    String groupName;
    String title;
    String text;
    String pic;
    String audio;
    String video;
    double lastModify;

    public Post() {
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        if(text==null)
            return "";
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getAudio() {
        if(audio==null)
            return "";
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getVideo() {
        if(video==null)
            return "";
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public double getLastModify() {
        return lastModify;
    }

    public void setLastModify(double lastModify) {
        this.lastModify = lastModify;
    }
}
