package com.mcubes.entity;

import com.mcubes.repository.BlogCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;

/**
 * Created by A.A.MAMUN on 4/11/2020.
 */
@Entity
public class Blog {

   // @Autowired
    //private BlogCommentRepository blogCommentRepository;

    @Id
    @SequenceGenerator(name = "blog_sequence", sequenceName = "blog_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blog_sequence")
    private long id;
    private String date;
    private String image;
    @Transient
    private MultipartFile imageFile;
    private String title;
    private String publisher;
    @Column(length = 3500)
    private String description;
    private long viewed;


    public Blog() {
    }

    public Blog(long id, String date, String image, String title, String publisher, String description) {
        this.id = id;
        this.date = date;
        this.image = image;
        this.title = title;
        this.publisher = publisher;
        this.description = description;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getViewed() {
        return viewed;
    }

    public void setViewed(long viewed) {
        this.viewed = viewed;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", image='" + image + '\'' +
                ", imageFile=" + imageFile +
                ", title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", description='" + description + '\'' +
                ", viewed=" + viewed +
                '}';
    }
}
