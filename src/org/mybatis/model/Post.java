package org.mybatis.model;

public class Post {
    private Integer id;
    private Integer blogId;
    private Integer authorId;
    private String createdOn;
    private String section;
    private String subject;
    private String body;

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public Integer getBlogId() {
	return blogId;
    }

    public void setBlogId(Integer blogId) {
	this.blogId = blogId;
    }

    public Integer getAuthorId() {
	return authorId;
    }

    public void setAuthorId(Integer authorId) {
	this.authorId = authorId;
    }

    public String getCreatedOn() {
	return createdOn;
    }

    public void setCreatedOn(String createdOn) {
	this.createdOn = createdOn;
    }

    public String getSection() {
	return section;
    }

    public void setSection(String section) {
	this.section = section;
    }

    public String getSubject() {
	return subject;
    }

    public void setSubject(String subject) {
	this.subject = subject;
    }

    public String getBody() {
	return body;
    }

    public void setBody(String body) {
	this.body = body;
    }

}
