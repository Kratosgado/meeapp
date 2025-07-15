package com.kratosgado.meeapp.models;

import com.kratosgado.meeapp.dtos.RegisterUserDto;
import jakarta.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.List;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false, unique = true, length = 50)
	private String email;

	@Column(nullable = false, length = 100)
	private String password;

	@Column(name = "profile_picture")
	private String profilePicture;

	@Column
	private List<String> interests;

	// queries
	// getProfile
	// get posts
	// get posts by interest
	// get groups
	//

	@Enumerated(EnumType.STRING)
	private Role role;

	@CreationTimestamp
	@Column(updatable = false)
	protected Date createdAt;

	public User(RegisterUserDto input) {
		this.name = input.getName();
		this.email = input.getEmail();
		this.password = input.getPassword();
		this.role = Role.USER;
	}

	@UpdateTimestamp
	protected Date updatedAt;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
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
}

enum Role {
	USER,
	ADMIN
}
