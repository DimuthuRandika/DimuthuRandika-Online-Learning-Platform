package com.twd.SpringSecurityJWT.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String content;
    private double price;

    @ManyToMany(mappedBy = "courses")
    private Set<OurUsers> enrolledStudents;
}
