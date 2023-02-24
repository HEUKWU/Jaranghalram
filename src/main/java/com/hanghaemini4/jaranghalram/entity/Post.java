package com.hanghaemini4.jaranghalram.entity;


import javax.persistence.*;

@Entity
public class Post extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String imageUrl;
    private int postLikecount;

    @ManyToOne
    private User User;

}
