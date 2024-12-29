package ru.accept_applicant_documents.system.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.accept_applicant_documents.system.model.Admin;
import ru.accept_applicant_documents.system.model.Applicant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
public class MyUserDetails implements UserDetails {

    private final Object user; // Объект может быть как Applicant, так и Admin

    public MyUserDetails(Applicant applicant) {
        this.user = applicant;
    }

    public MyUserDetails(Admin admin) {
        this.user = admin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        if (user instanceof Applicant)
        {
            authorities.add(new SimpleGrantedAuthority(((Applicant) user).getRole().name()));
        }
        else if (user instanceof Admin) {
            authorities.add(new SimpleGrantedAuthority(((Admin) user).getRole().name()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        if (user instanceof Applicant) {
            return ((Applicant) user).getPassword();
        } else if (user instanceof Admin) {
            return ((Admin) user).getPassword();
        }
        return null;
    }

    @Override
    public String getUsername() {
        if (user instanceof Applicant) {
            return ((Applicant) user).getEmail();
        } else if (user instanceof Admin) {
            return ((Admin) user).getEmail();
        }
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
