package com.organization.crm.modules.Event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.organization.crm.modules.Users.Users;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Document(collection = "events")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter
public class Event {
    @Id
    private String id;

    @Indexed(unique = true)
    @Field(name="name")
    private String name;

    @Field(name="location")
    private String location;

    @Field(name="createdBy")
    @DBRef
    private Users createdBy;

    @CreatedDate
    @Field(name = "createdAt")
    private Instant createdAt;

    @LastModifiedDate
    @Field(name = "updatedAt")
    private Instant updatedAt;
}
