package com.example.syz.demo.screenPage.communityScreen.community;

public class Community {
    private String communityItemUrl;
    private String communityItemTitle;
    private String communityItemText;

    public Community(String communityItemUrl, String communityItemTitle, String communityItemText) {
        this.communityItemUrl = communityItemUrl;
        this.communityItemTitle = communityItemTitle;
        this.communityItemText = communityItemText;
    }

    public String getCommunityItemUrl() {
        return communityItemUrl;
    }

    public String getCommunityItemTitle() {
        return communityItemTitle;
    }

    public String getCommunityItemText() {
        return communityItemText;
    }
}
