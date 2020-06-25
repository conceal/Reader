package com.example.syz.demo.util;

public class Page1Data {
    private String type;
    private String headerId;
    private String username;
    private String text;
    private String gif;
    private String image;
    private int up;
    private int comment;
    private int forward;

    public Page1Data (String type, String headerId, String username, String text, String gif,
                      String image, int up, int comment, int forward) {
        this.type = type;
        this.headerId = headerId;
        this.username = username;
        this.text = text;
        this.gif = gif;
        this.image = image;
        this.up = up;
        this.comment = comment;
        this.forward = forward;
    }

    public String getType() {
        return type;
    }

    public String getHeaderId() {
        return headerId;
    }

    public String getUsername() {
        return username;
    }

    public String getText() {
        return text;
    }

    public String getGif() {
        return gif;
    }

    public String getImage() {
        return image;
    }

    public int getUp() {
        return up;
    }

    public int getComment() {
        return comment;
    }

    public int getForward() {
        return forward;
    }
}
