package ru.kpfu.itis.dto;

public class UserDto {

    private String name;
    private Integer score;
    private String login;

    public UserDto(String name, Integer score, String login) {
        this.name = name;
        this.score = score;
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public Integer getScore() {
        return score;
    }

    public String getLogin() {
        return login;
    }
}
