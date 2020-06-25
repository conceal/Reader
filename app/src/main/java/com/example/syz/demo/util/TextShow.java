package com.example.syz.demo.util;

public class TextShow {
    private String text;
    private String name;
    private String url;
    public TextShow(String text,String name,String url){
        this.text = text;
        this.name = name;
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
