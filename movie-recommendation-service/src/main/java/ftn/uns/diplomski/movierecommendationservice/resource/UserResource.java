package ftn.uns.diplomski.movierecommendationservice.resource;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResource {

    private long userId;

    private String firstName;

    private String lastName;

    private String email;

    private Date createdAt;

    private Date updatedAt;
    
    private String username; 

    public UserResource() {

    }

    public UserResource(long userId, String firstName, String lastName, String email, Date createdAt, Date updatedAt, String username) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username; 
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

	public UserResource(long userId, String firstName, String lastName, String email, String username, Date createdAt, Date updatedAt) {
		this.userId = userId; 
		this.firstName = firstName; 
		this.lastName = lastName; 
		this.email = email; 
		this.username = username; 
		this.createdAt = createdAt; 
		this.updatedAt = updatedAt; 
	}

	public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
