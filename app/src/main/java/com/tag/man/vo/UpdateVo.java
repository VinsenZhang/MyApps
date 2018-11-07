package com.tag.man.vo;

import java.io.Serializable;


public class UpdateVo implements Serializable {


    private String update;

    private String force;

    private String update_url;


    private String content;


    private String new_version;


    private String marked_words;


    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getForce() {
        return force;
    }

    public void setForce(String force) {
        this.force = force;
    }

    public String getUpdate_url() {
        return update_url;
    }

    public void setUpdate_url(String update_url) {
        this.update_url = update_url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNew_version() {
        return new_version;
    }

    public void setNew_version(String new_version) {
        this.new_version = new_version;
    }


    public String getMarked_words() {
        return marked_words;
    }

    public void setMarked_words(String marked_words) {
        this.marked_words = marked_words;
    }
}
