package com.wanted.preonboarding.user;

import lombok.Getter;
import lombok.Setter;

public class UserResponse {

    @Getter @Setter
    public static class FindById{
        private int id;
        private String email;

        public FindById(User user) {
            this.id = user.getId();
            this.email = user.getEmail();
        }
    }
}
