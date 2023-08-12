package com.wanted.preonboarding.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


public class UserRequest {
    @Getter
    @Setter
    public static class JoinDTO {

        @NotEmpty
        @Pattern(regexp = "^.*@.*$", message = "@를 포함시켜 주세요")
        private String email;

        @NotEmpty
        @Size(min = 8, message = "8자 이상이어야 합니다.")
        private String password;

        public User toEntity() {
            return User.builder()
                    .email(email)
                    .password(password)
                    .roles("ROLE_USER")
                    .build();
        }
    }

    @Getter
    @Setter
    public static class LoginDTO {
        @NotEmpty
        @Pattern(regexp = "^.*@.*$", message = "@를 포함시켜 주세요")
        private String email;

        @NotEmpty
        @Size(min = 8, message = "8자 이상이어야 합니다.")
        private String password;
    }

    @Getter
    @Setter
    public static class EmailCheckDTO {
        @NotEmpty
        @Pattern(regexp = "^.*@.*$", message = "@를 포함시켜 주세요")
        private String email;
    }

    @Getter
    @Setter
    public static class UpdatePasswordDTO {
        @NotEmpty
        @Size(min = 8, message = "8자 이상이어야 합니다.")
        private String password;
    }
}
