package com.example.reimbursementonlinebackend.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User extends DomainBase implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @Column(name = "username", length = 254, unique = true)
    private String username;

    @Column(name = "email", length = 254, unique = true)
    private String email;

    @Column(name = "password", length = 200)
    private String password;

    @Column(name = "account_locked")
    private boolean accountNonLocked;

    @Column(name = "account_expired")
    private boolean accountNonExpired;

    @Column(name = "credentials_expired")
    private boolean credentialsNonExpired;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = "role_user", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

    @Override
    public boolean isAccountNonExpired() {
        return !accountNonExpired;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountNonLocked;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            String name = role.getName().toUpperCase();
            if (!name.startsWith("ROLE_")) {
                name = "ROLE_" + name;
            }
            authorities.add(new SimpleGrantedAuthority(name));
        }
        return authorities;
    }

    @Override
    @PrePersist
    public void prePersist() {
        super.prePersist();
        this.isEnabled = true;
        this.accountNonExpired = false;
        this.accountNonLocked = false;
        this.credentialsNonExpired = false;
    }

}