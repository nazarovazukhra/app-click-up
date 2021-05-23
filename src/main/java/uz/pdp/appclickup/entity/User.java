package uz.pdp.appclickup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.appclickup.entity.enums.SystemRole;
import uz.pdp.appclickup.entity.template.AbsUUIDEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sys_user")
public class User extends AbsUUIDEntity implements UserDetails {

    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String color;

    private String initialLetter;

    private String emailCode;

    @OneToOne(fetch = FetchType.LAZY)
    private Attachment avatar;

    @Enumerated(EnumType.STRING)
    private SystemRole systemRole;

    private boolean accountNonExpired=true;

    private boolean accountNonLocked=true;

    private boolean credentialsNonExpired=true;

    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(this.systemRole.name());
        return Collections.singletonList(simpleGrantedAuthority);
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public User(String fullName, String email, String password, SystemRole systemRole) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.systemRole = systemRole;
    }
}
