package ftn.uns.diplomski.movierecommendationservice.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("serial")
@Entity
@Table(name="role")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Role implements GrantedAuthority {

	 @Id
	 @GeneratedValue(strategy=GenerationType.IDENTITY)
	 @Column(name = "id", nullable = false, unique = true)
	 private Long id;

	 @Column(name = "_name", nullable = false)
	 private String name;

	 @ManyToMany(mappedBy="roles", fetch = FetchType.EAGER)
	 private List<User> users;
	
	 public String getAuthority() {
		return name; 
	 }
}
