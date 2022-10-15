package com.example.photoshare.model.minelike;

import java.io.Serializable;
import java.util.List;

public class RecordsBean implements Serializable {
    private String id;
    private String pUserId;
    private String imageCode;
    private String title;
    private String content;
    private String createTime;
    private List<String> imageUrlList;
    private Object likeId;
    private Object likeNum;
    private Boolean hasLike;
    private Object collectId;
    private Object collectNum;
    private Boolean hasCollect;
    private Boolean hasFocus;
    private String username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPUserId() {
        return pUserId;
    }

    public void setPUserId(String pUserId) {
        this.pUserId = pUserId;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<String> getImageUrlList() {
        return imageUrlList;
    }

    public void setImageUrlList(List<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }

    public Object getLikeId() {
        return likeId;
    }

    public void setLikeId(Object likeId) {
        this.likeId = likeId;
    }

    public Object getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Object likeNum) {
        this.likeNum = likeNum;
    }

    public Boolean getHasLike() {
        return hasLike;
    }

    public void setHasLike(Boolean hasLike) {
        this.hasLike = hasLike;
    }

    public Object getCollectId() {
        return collectId;
    }

    public void setCollectId(Object collectId) {
        this.collectId = collectId;
    }

    public Object getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Object collectNum) {
        this.collectNum = collectNum;
    }

    public Boolean getHasCollect() {
        return hasCollect;
    }

    public void setHasCollect(Boolean hasCollect) {
        this.hasCollect = hasCollect;
    }

    public Boolean getHasFocus() {
        return hasFocus;
    }

    public void setHasFocus(Boolean hasFocus) {
        this.hasFocus = hasFocus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
