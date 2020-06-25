package com.example.syz.demo.util;

public class Gif {
    private String text;
    private String type;
    private String username;
    private String header;
    private int comment;
    private String top_commentsContent;
    private String top_commentsHeader;
    private String top_commentsName;
    private int up;
    private int down;
    private int forward;
    private String gifImage;
    private String thumbnail;

    public String getText() {
        return text;
    }

    public int getComment() {
        return comment;
    }

    public int getDown() {
        return down;
    }

    public int getUp() {
        return up;
    }

    public String getHeader() {
        return header;
    }

    public String getTop_commentsContent() {
        return top_commentsContent;
    }

    public String getTop_commentsHeader() {
        return top_commentsHeader;
    }

    public String getTop_commentsName() {
        return top_commentsName;
    }

    public String getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public int getForward() {
        return forward;
    }

    public String getGifImage() {
        return gifImage;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setGifImage(String gifImage) {
        this.gifImage = gifImage;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setForward(int forward) {
        this.forward = forward;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTop_commentsContent(String top_commentsContent) {
        this.top_commentsContent = top_commentsContent;
    }

    public void setTop_commentsHeader(String top_commentsHeader) {
        this.top_commentsHeader = top_commentsHeader;
    }

    public void setTop_commentsName(String top_commentsName) {
        this.top_commentsName = top_commentsName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
