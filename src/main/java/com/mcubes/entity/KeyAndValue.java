package com.mcubes.entity;

import javax.persistence.*;

/**
 * Created by A.A.MAMUN on 4/5/2020.
 */
@Entity
public class KeyAndValue {

    @Id
    @Column(length = 50)
    private String keyId;
    @Column(length = 1500)
    private String value;

    public KeyAndValue() {
    }

    public KeyAndValue(String keyId, String value) {
        this.keyId = keyId;
        this.value = value;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "KeyAndValue{" +
                "key='" + keyId + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
