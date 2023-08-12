package com.wanted.preonboarding.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="user_tb")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 100, nullable = false, unique = true)
    private String email;
    @Column(length = 256, nullable = false)
//    @Min(value = 8, message = "8자 이상의 비밀번호가 저장되어야 합니다.")
    private String password;

    @Column(length = 30)
    private String roles;

    @Builder
    public User(int id, String email, String password, String roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

}
