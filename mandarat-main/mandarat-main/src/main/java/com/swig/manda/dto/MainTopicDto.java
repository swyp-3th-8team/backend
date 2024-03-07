package com.swig.manda.dto;

import java.util.List;


public class MainTopicDto {
    private Long id;
    private String title;

    private Long memberId;
    private List<DetailDto> details;

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setMemberId(Long memberid) {
        this.memberId = memberid;
    }
    public Long getMemberId()
    {return memberId;}


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public List<DetailDto> getDetails() {
        return details;
    }


    public void setDetails(List<DetailDto> details) {
        this.details = details;
    }
}
