package com.mcubes.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * Created by A.A.MAMUN on 2/4/2020.
 */
@Entity
public class AppUser {

    @Id
    //@SequenceGenerator(name = "appuser_sequence", sequenceName = "appuser_sequence", allocationSize = 1)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appuser_sequence")
    private String email;
    private String first_name;
    private String last_name;
    @Transient
    private String name;
    @Column(length = 15)
    private String phone;
    private String dob;
    private String gender;
    private String address;
    private String company;
    private String city;
    private String postal_code;
    private String password;
    private String role;
    private String facebookProfileLink;
    private String twitterProfileLink;
    private String instagramProfileLink;

    @Transient
    private int profileComplete;

    public AppUser() {
    }

    public AppUser(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public AppUser(String first_name, String last_name) {
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        this.name = first_name+" "+last_name;
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFacebookProfileLink() {
        return facebookProfileLink;
    }

    public void setFacebookProfileLink(String facebookProfileLink) {
        this.facebookProfileLink = facebookProfileLink;
    }

    public String getTwitterProfileLink() {
        return twitterProfileLink;
    }

    public void setTwitterProfileLink(String twitterProfileLink) {
        this.twitterProfileLink = twitterProfileLink;
    }

    public String getInstagramProfileLink() {
        return instagramProfileLink;
    }

    public void setInstagramProfileLink(String instagramProfileLink) {
        this.instagramProfileLink = instagramProfileLink;
    }


    public int getProfileComplete() {

        int not_fill = 0;

        if(dob==null)
            not_fill++;
        if(company==null || company.trim().length()==0)
            not_fill++;
        if(city==null || city.trim().length()==0)
            not_fill++;
        if(postal_code==null || postal_code.trim().length()==0)
            not_fill++;
        if(facebookProfileLink==null || facebookProfileLink.trim().length()==0)
            not_fill++;
        if(twitterProfileLink==null || twitterProfileLink.trim().length()==0)
            not_fill++;
        if(instagramProfileLink==null || instagramProfileLink.trim().length()==0)
            not_fill++;

        float percent = (float) ((15.0 - not_fill)/15)*100;
        profileComplete = (int) percent;
        return profileComplete;
    }

    public void setProfileComplete(int profileComplete) {
        this.profileComplete = profileComplete;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "email='" + email + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", company='" + company + '\'' +
                ", city='" + city + '\'' +
                ", postal_code='" + postal_code + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", facebookProfileLink='" + facebookProfileLink + '\'' +
                ", twitterProfileLink='" + twitterProfileLink + '\'' +
                ", instagramProfileLink='" + instagramProfileLink + '\'' +
                ", profileComplete=" + profileComplete +
                '}';
    }
}
