package com.organization.crm.modules.Users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.Set;

@Document(collection="users")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter
public class Users {
    @Id
    private String id;

    @Field(name="email")
    @Indexed(unique=true)
    private String email;

    @Field(name="password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Field(name="role")
    private Set<String> roles;

    @Field(name="createdAt")
    @CreatedDate
    private Instant createdAt;

    @Field(name="updatedAt")
    @LastModifiedDate
    private Instant updatedAt;
}
