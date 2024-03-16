package com.swig.manda.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity

public class MainTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String goal;
    // 기본 생성자
    public MainTopic() {
    }

    // 매개변수를 받는 생성자
    public MainTopic(Long id, String goal) {
        this.id = id;
        this.goal =goal;
    }


    @OneToMany(mappedBy = "mainTopic", cascade = CascadeType.ALL)
    private List<Detail> details;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getGoal() {
        return goal;
    }


    public void setGoal(String title) {
        this.goal = goal;
    }


    public List<Detail> getDetails() {
        if (this.details == null) {
            return new ArrayList<>();
        }
        else{
            return details;
        }
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }


}


