package ftn.uns.diplomski.movierecommendationservice.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity()
@Table(name = "users")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", unique = true, nullable = false)
    private long userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email_address")
    private String email;
    
    @Column(name = "username")
    private String username; 
    
    @Column(name="password", unique = true)
    private String password; 

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;
   
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserMovieRating> userMovieRates;
    
    @JsonIgnore
	@OneToOne(mappedBy = "user")
	private Watchlist watchlist; 
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE})
	private List<Movie> movies; 
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE})
	private List<CustomList> customLists;
	
	 @ManyToMany(fetch = FetchType.EAGER)
	 @JoinTable(
	            name="role_has_user"
	            , joinColumns={
	            @JoinColumn(name="user_id")
	    }
	            , inverseJoinColumns={
	            @JoinColumn(name="role_id")
	    }
	    )
	 private List<Role> roles;

    public User() {
    }
    
    public User(String username, String email, String password, String firstName, String lastName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
      }
      
    
    public User(String firstName, String lastName, String email, Date createdAt, Date updatedAt, Set<UserMovieRating> userMovieRates) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userMovieRates = userMovieRates;
    }
}