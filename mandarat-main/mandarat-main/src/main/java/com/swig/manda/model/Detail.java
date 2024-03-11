package com.swig.manda.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

@Entity
public class Detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    private String memo;

    @ManyToOne
    @JoinColumn(name = "main_topic_id")
    private MainTopic mainTopic;






}

