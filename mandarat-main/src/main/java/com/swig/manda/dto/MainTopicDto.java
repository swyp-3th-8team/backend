package com.swig.manda.dto;

import jakarta.validation.constraints.Pattern;

import java.util.List;


public class MainTopicDto {
    private Long id;

    @Pattern(regexp = ".{0,15}", message = "목표는 15자 이하로 입력해주세요.")
    private String goal ;

    private List<DetailDto> details;


    private String userId;

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId()
    {return userId;}


    public String getGoal() {
        return goal;
    }


    public void setGoal(String goal) {
        this.goal = goal;
    }


    public List<DetailDto> getDetails() {
        return details;
    }


    public void setDetails(List<DetailDto> details) {
        this.details = details;
    }
}
