package com.example.it193_group8_finalproject.model;

import java.util.Objects;

public class Dog {

    private int dog_id;

    private String dog_name;
    private String dog_img;
    private String dog_sex;
    private int dog_age;

    private String dog_href;

    public Dog() {
    }

    public Dog(String dog_name, String dog_img, String dog_sex, int dog_age, String dog_href) {
        this.dog_name = dog_name;
        this.dog_img = dog_img;
        this.dog_sex = dog_sex;
        this.dog_age = dog_age;
        this.dog_href = dog_href;
    }

    public String getDog_href() {
        return dog_href;
    }

    public void setDog_href(String dog_href) {
        this.dog_href = dog_href;
    }

    public int getDog_id() {
        return dog_id;
    }

    public void setDog_id(int dog_id) {
        this.dog_id = dog_id;
    }

    public String getDog_name() {
        return dog_name;
    }

    public void setDog_name(String dog_name) {
        this.dog_name = dog_name;
    }

    public String getDog_img() {
        return dog_img;
    }

    public void setDog_img(String dog_img) {
        this.dog_img = dog_img;
    }

    public String getDog_sex() {
        return dog_sex;
    }

    public void setDog_sex(String dog_sex) {
        this.dog_sex = dog_sex;
    }

    public int getDog_age() {
        return dog_age;
    }

    public void setDog_age(int dog_age) {
        this.dog_age = dog_age;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.dog_id);
        hash = 79 * hash + Objects.hashCode(this.dog_name);
        hash = 79 * hash + + Objects.hashCode(this.dog_img);
        hash = 79 * hash + + Objects.hashCode(this.dog_sex);
        hash = 79 * hash + + Objects.hashCode(this.dog_age);
        hash = 79 * hash + + Objects.hashCode(this.dog_href);
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

        final Dog other = (Dog) obj;
        if (!Objects.equals(this.dog_name, other.dog_name)) {
            return false;
        }

        if (!Objects.equals(this.dog_img, other.dog_img)) {
            return false;
        }

        if (!Objects.equals(this.dog_age, other.dog_age)) {
            return false;
        }
        if (!Objects.equals(this.dog_href, other.dog_href)) {
            return false;
        }

        return Objects.equals(this.dog_id, other.dog_id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("dog_id=").append(dog_id);
        sb.append(", dog_name='").append(dog_name).append('\'');
        sb.append(", dog_img=").append(dog_img).append('\'');
        sb.append(", dog_sex='").append(dog_sex).append('\'');
        sb.append(", dog_href='").append(dog_age).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
