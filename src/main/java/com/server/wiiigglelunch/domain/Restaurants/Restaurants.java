package com.server.wiiigglelunch.domain.Restaurants;

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
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Restaurants extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private String longitude;

    private String latitude;

    @OneToMany(mappedBy = "restaurantsId",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Posts> posts = new ArrayList<>();
}
