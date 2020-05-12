package com.mcubes.entity;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

/**
 * Created by A.A.MAMUN on 4/8/2020.
 */
@Entity
public class OurMember {

    @Id
    @SequenceGenerator(name = "our_member_sequence", sequenceName = "our_member_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "our_member_sequence")
    private long id;
    @Transient
    private MultipartFile imageFile;
    private String image;
    private String name;
    private String designation;
    private String facebookProfile;
    private String twitterProfile;
    private String googlePlusProfile;
    private String instagramProfile;
    private String linkedinProfile;

    public OurMember() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getFacebookProfile() {
        return facebookProfile;
    }

    public void setFacebookProfile(String facebookProfile) {
        this.facebookProfile = facebookProfile;
    }

    public String getTwitterProfile() {
        return twitterProfile;
    }

    public void setTwitterProfile(String twitterProfile) {
        this.twitterProfile = twitterProfile;
    }

    public String getGooglePlusProfile() {
        return googlePlusProfile;
    }

    public void setGooglePlusProfile(String googlePlusProfile) {
        this.googlePlusProfile = googlePlusProfile;
    }

    public String getInstagramProfile() {
        return instagramProfile;
    }

    public void setInstagramProfile(String instagramProfile) {
        this.instagramProfile = instagramProfile;
    }

    public String getLinkedinProfile() {
        return linkedinProfile;
    }

    public void setLinkedinProfile(String linkedinProfile) {
        this.linkedinProfile = linkedinProfile;
    }

    @Override
    public String toString() {
        return "OurMember{" +
                "id=" + id +
                ", imageFile=" + imageFile +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", designation='" + designation + '\'' +
                ", facebookProfile='" + facebookProfile + '\'' +
                ", twitterProfile='" + twitterProfile + '\'' +
                ", googlePlusProfile='" + googlePlusProfile + '\'' +
                ", instagramProfile='" + instagramProfile + '\'' +
                ", linkedinProfile='" + linkedinProfile + '\'' +
                '}';
    }
}
