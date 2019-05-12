package com.upgrad.quora.service.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

@Entity
@Table(name = "question")

        @NamedQueries({

                @NamedQuery(name = "questionByUuid", query = "select q from QuestionEntity q where q.uuid = :uuid "),
                @NamedQuery(name = "getAllQuestions", query = "select q from QuestionEntity q")

        })

public class QuestionEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @Size(max = 200)
    private String uuid;

    @Column(name = "content")
    @Size(max = 500)
    @NotNull
    private String content;

    @Column(name = "date")
    @NotNull
    private ZonedDateTime date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UsersEntity user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }
}
=======

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;


    @Entity
    @Table(name = "answer", schema = "public")
/*@NamedQueries({
        @NamedQuery(name = "roleByuuid" , query = "select r from RoleEntity r where r.uuid =:uuid")
})*/
    public class QuestionEntity implements Serializable {

        @Id
        @Column(name = "ID")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(name = "UUID")
        @NotNull
        @Size(max = 200)
        private int uuid;

        @Column(name = "content")
        @NotNull
        @Size(max = 255)
        private String content;

        @Column(name = "date")
        @Size(max = 6)
        private Timestamp date;

        @ManyToOne
        @JoinColumn(name = "USER_ID")
        private UserEntity user;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public int getUuid() {
            return uuid;
        }

        public void setUuid(int uuid) {
            this.uuid = uuid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Timestamp getDate() {
            return date;
        }

        public void setDate(Timestamp date) {
            this.date = date;
        }

        public UserEntity getUser() {
            return user;
        }

        public void setUser(UserEntity user) {
            this.user = user;
        }

/* public RoleEntity(@NotNull int uuid) {
        this.uuid = uuid;
    }*/

        @Override
        public boolean equals(Object obj) {
            return new EqualsBuilder().append(this, obj).isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(this).hashCode();
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
        }

    }

