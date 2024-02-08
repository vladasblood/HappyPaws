package com.example.it193_group8_finalproject.model;

import java.util.Objects;

public class User {
    private int user_id;

    private String username;
    private String password;
    private String role;

    public User() {
    }

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.user_id);
        hash = 79 * hash + Objects.hashCode(this.username);
        hash = 79 * hash + + Objects.hashCode(this.password);
        hash = 79 * hash + + Objects.hashCode(this.role);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final User other = (User) obj;
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }

        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        return Objects.equals(this.user_id, other.user_id);
    }

//    @Override
//    public String toString() {
//        final StringBuilder sb = new StringBuilder("User{");
//        sb.append("user_id=").append(user_id);
//        sb.append(", username='").append(username).append('\'');
//        sb.append(", password=").append(password);
//        sb.append(", role='").append(role).append('\'');
//        sb.append('}');
//        return sb.toString();
//    }

    @Override
    public String toString(){
        return getUsername();
    }

}
