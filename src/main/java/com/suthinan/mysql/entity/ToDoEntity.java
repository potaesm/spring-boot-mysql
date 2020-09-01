package com.suthinan.mysql.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
@Table(name = "simple_table")
@NoArgsConstructor
@AllArgsConstructor
public class ToDoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="title")
    private String title;
    @Column(name="detail")
    private String detail;
    @Column(name="name", unique=true)
    private String name;
    @Column(name="date", columnDefinition="DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Date date;
}
