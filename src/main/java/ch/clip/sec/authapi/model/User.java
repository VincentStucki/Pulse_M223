package ch.clip.sec.authapi.model;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;

@Builder
@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    @Column(unique = true, length = 100, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String token;
    private long expiresIn;
    private String profileImage;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Media> mediaList;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Reaction> reactions;

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL)
    private List<Follow> following;

    @OneToMany(mappedBy = "followed", cascade = CascadeType.ALL)
    private List<Follow> followers;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.email;
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
