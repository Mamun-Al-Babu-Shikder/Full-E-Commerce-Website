package com.mcubes.entity;

import javax.persistence.*;

/**
 * Created by A.A.MAMUN on 4/5/2020.
 */
@Entity
public class ContactMessage {

    @Transient
    public static final String SEEN = "SEEN", UNSEEN = "UNSEEN", REPLIED = "REPLIED";

    @Id
    @SequenceGenerator(name = "contact_message_sequence", sequenceName = "contact_message_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_message_sequence")
    private long id;
    private String date;
    private String name;
    private String email;
    private String phone;
    @Column(length = 1500)
    private String message;
    @Column(length = 10)
    private String status;

    public ContactMessage() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ContactMessage{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
