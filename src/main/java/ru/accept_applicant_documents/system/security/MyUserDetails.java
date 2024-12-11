package ru.accept_applicant_documents.system.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.accept_applicant_documents.system.model.Applicant;

import java.util.Collection;

@Getter
public class MyUserDetails implements UserDetails {

    private final Applicant applicant;

    public MyUserDetails(Applicant applicant) {
        this.applicant = applicant;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return applicant.getPassword();
    }

    @Override
    public String getUsername() {
        return applicant.getEmail();
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
