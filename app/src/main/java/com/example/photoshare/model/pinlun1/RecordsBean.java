package com.example.photoshare.model.pinlun1;

public class RecordsBean {
    private String id;
    private String appKey;
    private String pUserId;
    private String userName;
    private String shareId;
    private Object parentCommentId;
    private Object parentCommentUserId;
    private Object replyCommentId;
    private Object replyCommentUserId;
    private Integer commentLevel;
    private String content;
    private Integer status;
    private Integer praiseNum;
    private Integer topStatus;
    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getPUserId() {
        return pUserId;
    }

    public void setPUserId(String pUserId) {
        this.pUserId = pUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    public Object getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Object parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public Object getParentCommentUserId() {
        return parentCommentUserId;
    }

    public void setParentCommentUserId(Object parentCommentUserId) {
        this.parentCommentUserId = parentCommentUserId;
    }

    public Object getReplyCommentId() {
        return replyCommentId;
    }

    public void setReplyCommentId(Object replyCommentId) {
        this.replyCommentId = replyCommentId;
    }

    public Object getReplyCommentUserId() {
        return replyCommentUserId;
    }

    public void setReplyCommentUserId(Object replyCommentUserId) {
        this.replyCommentUserId = replyCommentUserId;
    }

    public Integer getCommentLevel() {
        return commentLevel;
    }

    public void setCommentLevel(Integer commentLevel) {
        this.commentLevel = commentLevel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(Integer praiseNum) {
        this.praiseNum = praiseNum;
    }

    public Integer getTopStatus() {
        return topStatus;
    }

    public void setTopStatus(Integer topStatus) {
        this.topStatus = topStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
