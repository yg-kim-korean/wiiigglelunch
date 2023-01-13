package com.server.wiiigglelunch.domain.Users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.server.wiiigglelunch.domain.BaseEntity;
import com.server.wiiigglelunch.domain.Photos.Photos;
import com.server.wiiigglelunch.domain.Posts.Posts;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
@EntityListeners(value = EntityListeners.class)
@ToString(callSuper = true,exclude = {"posts"})
@EqualsAndHashCode(callSuper = true)
public class Users extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    @Column(unique = true)
    private String email;

    private String password;

    private int salt;

    @OneToMany(mappedBy = "usersId",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Photos> photos = new ArrayList<>();

    @ManyToMany(targetEntity =  Posts.class,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "cate_inter")
    @JsonIgnore
    private List<Posts> posts = new ArrayList<>();
}
