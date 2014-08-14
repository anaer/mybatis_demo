package org.mybatis.model;

public interface BlogMapper {
    //<select id="selectBlog_by_id">
    public Blog selectBlog_by_id(int id);

    //<select id="selectBlog_by_bean">
    public Blog selectBlog_by_bean(Blog blog);

    //<update id="updateBlog_use_bean">
    public void updateBlog_use_bean(Blog blog);

    //<insert id="insertBlog_user_bean">
    public void insertBlog_user_bean(Blog blog);

    //<insert id="insertBlog_user_autokey">
    public void insertBlog_user_autokey(Blog blog);

    //<insert id="insertBlog_user_autokey_null">
    public void insertBlog_user_autokey_null(Blog blog);
}