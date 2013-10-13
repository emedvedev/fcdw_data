package uk.org.funcube.fcdw.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class UserEntity implements UserDetails, User {

	private static final long serialVersionUID = -3954009339416898275L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;
	private String password;
	private String latitude;
	private String longitude;
	private String siteId;
	private boolean enabled;
	private boolean admin;
	private boolean expired;
	private boolean locked;
	private boolean credentialsExpired;
	private String authKey;

	@ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
	private List<HexFrameEntity> hexFrames = new ArrayList<HexFrameEntity>();

	public UserEntity() {
	}

	public UserEntity(final String username, final String password, final String latitude, final String longitude,
			final String siteId, final boolean enabled, final boolean admin, final boolean expired, final boolean locked,
			final boolean credentialsExpired, final String authKey) {
		this.username = username;
		this.password = password;
		this.latitude = latitude;
		this.longitude = longitude;
		this.siteId = siteId;
		this.enabled = enabled;
		this.admin = admin;
		this.expired = expired;
		this.locked = locked;
		this.credentialsExpired = credentialsExpired;
		this.authKey = authKey;
	}

	public UserEntity(String username2, String password2, List<GrantedAuthority> perms) {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public final String getUsername() {
		return username;
	}

	public final String getPassword() {
		return password;
	}

	public final String getLatitude() {
		return latitude;
	}

	public final String getLongitude() {
		return longitude;
	}

	public final String getSiteId() {
		return siteId;
	}

	public final boolean isEnabled() {
		return enabled;
	}

	public final boolean isAdmin() {
		return admin;
	}

	public final boolean isExpired() {
		return expired;
	}

	public final boolean isLocked() {
		return locked;
	}

	public final boolean isCredentialsExpired() {
		return credentialsExpired;
	}

	public final String getAuthKey() {
		return authKey;
	}

	@Override
	public void setPassword(final String hashPassword) {
		this.password = hashPassword;
	}

	@Override
	public List<GrantedAuthority> getAuthorities() {
		final List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);
		authList.add(new GrantedAuthorityImpl("ROLE_USER"));
		if (isAdmin()) {
			authList.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
		}
		return authList;

	}

	@Override
	public boolean isAccountNonExpired() {
		return !expired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !credentialsExpired;
	}

	@Override
	public void setEnabled(final boolean state) {
		this.enabled = state;
	}

	public final void setUsername(String username) {
		this.username = username;
	}

	public final void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public final void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public final void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public final void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public final void setExpired(boolean expired) {
		this.expired = expired;
	}

	public final void setLocked(boolean locked) {
		this.locked = locked;
	}

	public final void setCredentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	public final void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	public void addFrame(final HexFrameEntity hexFrame) {
		if (!getFrames().contains(hexFrame)) {
			getFrames().add(hexFrame);
		}
		if (!hexFrame.getUsers().contains(this)) {
			hexFrame.getUsers().add(this);
		}
	}

	public List<HexFrameEntity> getFrames() {
		return hexFrames;
	}

	public void setFrames(final List<HexFrameEntity> frames) {
		this.hexFrames = frames;
	}

}
