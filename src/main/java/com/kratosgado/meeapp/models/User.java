package com.kratosgado.meeapp.models;

import com.kratosgado.meeapp.dtos.RegisterUserDto;
import jakarta.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false, unique = true, length = 50)
	private String email;

	@Column(nullable = false, length = 100)
	private String password;

	@Column(name = "profile_picture")
	private String profilePicture;

	@ElementCollection
	@CollectionTable(name = "user_interests", joinColumns = @JoinColumn(name = "user_id"))
	@Column
	private Set<String> interests = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Post> posts = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Message> messages = new HashSet<>();

	@ManyToMany(mappedBy = "users")
	private Set<Group> groups = new HashSet<>();

	@OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Event> createdEvents = new HashSet<>();

	@ManyToMany(mappedBy = "participants")
	private Set<Event> participatingEvents = new HashSet<>();

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	protected Date createdAt;

	public User(RegisterUserDto input) {
		this.name = input.getName();
		this.email = input.getEmail();
		this.password = input.getPassword();
	}

	@UpdateTimestamp
	protected Date updatedAt;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("USER"));
	}

	@Override
	public String getPassword() {
		return password;
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
		return true;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public String toString() {
		return id;
	}
}
