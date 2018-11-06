package com.songshu.jucai.vo;

import java.io.Serializable;

/**
 * "update": "0",//是否更新     1更新 0不更新
 * "force": "0",//是否强制更新   1强制  0不强制
 * "update_url": "xxxxxxxxxx",//更新链接
 * "content": "1:优化整体的流畅度<br>2:修复了已知BUG<br>3:添加了新功能",//内容（html   br换行）
 * "new_version": "2.3.0",//更新的版本号
 * "md5": "aaaaaaaaaaaaaaaaaa",//更新包的MD5值
 * "marked_words": ""//更新提示语言（实例 您有新版本需要更新！）
 */
public class UpdateVo implements Serializable {


    private String update;

    private String force;

    private String update_url;


    private String content;


    private String new_version;


    private String md5;


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

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getMarked_words() {
        return marked_words;
    }

    public void setMarked_words(String marked_words) {
        this.marked_words = marked_words;
    }
}
