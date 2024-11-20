package com.sporty.backend.user.domain;

import com.sporty.backend.global.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private com.sporty.backend.user.domain.SocialType socialType;

    private String socialId;

    @Builder
    public User(Long id, String email, String password, com.sporty.backend.user.domain.SocialType socialType, String socialId) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.socialType = socialType;
        this.socialId = socialId;
    }
}
