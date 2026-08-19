package com.example.api_gateway.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final Map<String, UserData> users = new HashMap<>();

    public UserService(PasswordEncoder passwordEncoder) {

        users.put(
                "employee",
                new UserData(
                        passwordEncoder.encode("employee123"),
                        "EMPLOYEE"
                )
        );

        users.put(
                "manager",
                new UserData(
                        passwordEncoder.encode("manager123"),
                        "MANAGER"
                )
        );
    }

    public UserData findUser(String username) {

        return users.get(username);
    }

    public static class UserData {

        private final String password;
        private final String role;

        public UserData(String password, String role) {
            this.password = password;
            this.role = role;
        }

        public String getPassword() {
            return password;
        }

        public String getRole() {
            return role;
        }
    }
}