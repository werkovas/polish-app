package com.studentapp.studentApp.model;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "student")
public class Student implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, name = "username", unique = true)
    private String username;

    @Size(min = 8, max = 32)
    @Column(nullable = false,name = "password")
    private String password;

    @Column(name = "points")
    private Integer points;


    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "idStudent")
    List<Score> scoreList;

    private Boolean enabled = false;



    public Student(String username, String password, Integer points) {
        this.username = username;
        this.password = password;
        this.points = points;
    }
    public Student(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Student(Long id, String username, String password, Integer points, List<Score> scoreList, Boolean enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.points = points;
        this.scoreList = scoreList;
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
