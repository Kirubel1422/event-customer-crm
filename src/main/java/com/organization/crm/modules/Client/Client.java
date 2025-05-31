package com.organization.crm.modules.Client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.organization.crm.modules.Event.Event;
import lombok.Builder;
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

@Document(collection="clients")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter
@Builder
public class Client {
    @Id
    private String id;

    @Field(name="first_name")
    private String firstName;

    @Field(name="last_name")
    private String lastName;

    @Field(name="email")
    @Indexed
    private String email;

    @Field(name="phone_number")
    private String phoneNumber;

    @Field(name="plan")
    private String plan;

    @Field(name="event")
    @DBRef
    private Event event;

    @CreatedDate
    @Field(name="createdAt")
    private Instant createdAt;

    @LastModifiedDate
    @Field(name="updatedAt")
    private Instant updatedAt;
}
