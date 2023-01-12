package com.server.wiiigglelunch.domain.Photos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.server.wiiigglelunch.domain.BaseEntity;
import com.server.wiiigglelunch.domain.Posts.Posts;
import com.server.wiiigglelunch.domain.Users.Users;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@EntityListeners(value = EntityListeners.class)
@Entity
@ToString(callSuper = true,exclude = {"usersId","postsId"})
@EqualsAndHashCode(callSuper = true)
public class Photos extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String src;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="users_id")
    @JsonIgnore
    private Users usersId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="posts_id")
    @JsonIgnore
    private Posts postsId;
}
