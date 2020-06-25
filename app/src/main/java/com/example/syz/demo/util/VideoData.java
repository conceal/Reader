package com.example.syz.demo.util;

public class VideoData {
    private String title;
    private String description;
    private int collectionCount;
    private int shareCount;
    private int replyCount;
    private String authorname;
    private String authorImg;
    private String playUrl;
    private String cover;

    public String getTitle() {
        return title;
    }

    public int getCollectionCount() {
        return collectionCount;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public String getAuthorImg() {
        return authorImg;
    }

    public String getAuthorname() {
        return authorname;
    }

    public String getCover() {
        return cover;
    }

    public String getDescription() {
        return description;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthorImg(String authorImg) {
        this.authorImg = authorImg;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public void setCollectionCount(int collectionCount) {
        this.collectionCount = collectionCount;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }
}
