package com.hendisantika.postgres.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String name;

    @Enumerated(value = EnumType.STRING)
    @Column
    private Gender gender;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column
    private String email;

    @Column
    private String address;

    @Column
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column
    private Position position;

    @Column
    private String comments;

    @OneToOne
    private User agent;

    @ManyToMany
    @JoinTable(
            name = "agent_skill",
            joinColumns = @JoinColumn(name = "agent_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private List<Skill> skills = Collections.emptyList();

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    private Image image;

    @OneToMany
    private List<House> houses;
}