package com.kndoni.event.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDate;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="events")
public class Event implements Serializable {
    @Id @GeneratedValue(strategy = AUTO)
    @Column(name="id")
    private Long id;
    @Column(name="name", unique = true)
    @NotEmpty(message = "Item name can not be empty or null")
    private String name;
    @Column(name = "category")
    private String  category;
    @Column(name = "description")
    private String description;
    @Column(name = "publish_date")
    private LocalDate publishDate;

}
