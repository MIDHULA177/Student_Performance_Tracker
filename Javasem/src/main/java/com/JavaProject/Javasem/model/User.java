package com.JavaProject.Javasem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    // roles: ADMIN, FACULTY, STUDENT
    private String role;

    public String getGrade() {
        return "";
    }

    public int getAttendance() {
        return 0;
    }


    public CharSequence getStrengths() {
        return null;
    }

    public CharSequence getAreasForImprovement() {
        return null;
    }
}

