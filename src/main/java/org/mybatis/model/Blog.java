package org.mybatis.model;

import java.util.List;

public class Blog {
    private Integer id;
    private String title;
    private Integer authorId;
    private Author author;

    private List<Post> posts;

    public Blog() {
	super();
    }

    public Blog(Integer id, String title, Integer authorId) {
	super();
	this.id = id;
	this.title = title;
	this.authorId = authorId;
    }

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public Integer getAuthorId() {
	return authorId;
    }

    public void setAuthorId(Integer authorId) {
	this.authorId = authorId;
    }

    public Author getAuthor() {
	return author;
    }

    public void setAuthor(Author author) {
	this.author = author;
    }

    public List<Post> getPosts() {
	return posts;
    }

    public void setPosts(List<Post> posts) {
	this.posts = posts;
    }

}
