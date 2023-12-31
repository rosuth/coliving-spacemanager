package com.colivingspacemanager.app.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "users")
@Data
public class User {
    @Id
    private String id;
    private String code;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private String role; // "ADMIN" or "USER" or "SPACE_MANAGER"
    private Date created;
    private Date lastUpdated;

}
