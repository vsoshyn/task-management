package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.userdetails.UserDetails;
import view.Views;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "[user]", uniqueConstraints = @UniqueConstraint(name = "username_uk", columnNames = "username"))
@org.hibernate.annotations.DynamicUpdate

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@NoArgsConstructor
@Getter @Setter
@ToString(doNotUseGetters = true, of = {"id", "username", "state"})
public class User extends Persistable implements UserDetails {
    public static final User SYSTEM = new User("system", null, null, null, false, null, List.of(Authority.SYSTEM));

    @Id
    @GeneratedValue(generator = "idGenerator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "idGenerator", allocationSize = 10)
    private Long id;
    @JsonView(Views.UserView.Name.class)
    @Column
    private String username;
    @JsonView(Views.UserView.UI.class)
    @Column
    private String firstName;
    @JsonView(Views.UserView.UI.class)
    @Column
    private String lastName;
    @JsonIgnore
    @Column
    private String email;
    @Column
    @org.hibernate.annotations.Type(type = "yes_no")
    private boolean photo;
    @JsonIgnore
    @Column
    private String password;
    @JsonView(Views.UserView.UI.class)
    @Column
    @Enumerated(EnumType.STRING)
    private UserState state;
    @JsonView(Views.UserView.UI.class)
    @Column
    @JoinTable(name = "user_authorities", foreignKey = @ForeignKey(name = "user_authorities_fk"))
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Collection<Authority> authorities = new HashSet<>();
    @JsonView(Views.AuditView.AuditDate.CreatedDate.class)
    @CreatedDate
    private LocalDate createdDate = LocalDate.now();
    @JsonView(Views.AuditView.AuditDate.LastModifiedDate.class)
    @LastModifiedDate
    private LocalDate lastModifiedDate = LocalDate.now();

    public User(String username, String firstName, String lastName, String email, boolean hasPhoto, String password, List<Authority> authorities) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.photo = hasPhoto;
        this.authorities.addAll(authorities);
        this.state = UserState.DRAFT;
        this.password = password;
    }

    @Override
    public Collection<Authority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return state == UserState.ACTIVE;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return state == UserState.ACTIVE;
    }

}
