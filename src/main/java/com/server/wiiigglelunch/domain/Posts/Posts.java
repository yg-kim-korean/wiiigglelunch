package com.server.wiiigglelunch.domain.Posts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.server.wiiigglelunch.domain.BaseEntity;
import com.server.wiiigglelunch.domain.Photos.Photos;
import com.server.wiiigglelunch.domain.Restaurants.Restaurants;
import com.server.wiiigglelunch.domain.Users.Users;
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
@ToString(callSuper = true,exclude = {"restaurantsId"})
@EqualsAndHashCode(callSuper = true)
public class Posts extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tmi;

    private int score;

    private String menu;

    @OneToMany(mappedBy = "postsId",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Photos> photos = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="restaurants_id")
    @JsonIgnore
    private Restaurants restaurantsId;
}
