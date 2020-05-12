package com.mcubes.entity;

import javax.persistence.*;

/**
 * Created by A.A.MAMUN on 4/11/2020.
 */
@Entity
public class BlogComment {

    @Id
    @SequenceGenerator(name = "blog_comment_sequence", sequenceName = "blog_comment_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blog_comment_sequence")
    private long id;
    private long blogId;
    private String userEmail;
    private String userName;
    private String date;
    @Column(length = 1500)
    private String comment;

    public BlogComment() {
    }

    public BlogComment(long blogId, String userEmail, String userName, String date, String comment) {
        this.blogId = blogId;
        this.userEmail = userEmail;
        this.userName = userName;
        this.date = date;
        this.comment = comment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBlogId() {
        return blogId;
    }

    public void setBlogId(long blogId) {
        this.blogId = blogId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
