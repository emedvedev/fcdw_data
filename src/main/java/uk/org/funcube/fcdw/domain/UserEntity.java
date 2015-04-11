// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

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
            final String siteId, final boolean enabled, final boolean admin, final boolean expired,
            final boolean locked,
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (admin ? 1231 : 1237);
        result = prime * result + ((authKey == null) ? 0 : authKey.hashCode());
        result = prime * result + (credentialsExpired ? 1231 : 1237);
        result = prime * result + (enabled ? 1231 : 1237);
        result = prime * result + (expired ? 1231 : 1237);
        result = prime * result
                + ((hexFrames == null) ? 0 : hexFrames.hashCode());
        result = prime * result
                + ((latitude == null) ? 0 : latitude.hashCode());
        result = prime * result + (locked ? 1231 : 1237);
        result = prime * result
                + ((longitude == null) ? 0 : longitude.hashCode());
        result = prime * result
                + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((siteId == null) ? 0 : siteId.hashCode());
        result = prime * result
                + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof UserEntity)) {
            return false;
        }
        UserEntity other = (UserEntity)obj;
        if (admin != other.admin) {
            return false;
        }
        if (authKey == null) {
            if (other.authKey != null) {
                return false;
            }
        }
        else if (!authKey.equals(other.authKey)) {
            return false;
        }
        if (credentialsExpired != other.credentialsExpired) {
            return false;
        }
        if (enabled != other.enabled) {
            return false;
        }
        if (expired != other.expired) {
            return false;
        }
        if (hexFrames == null) {
            if (other.hexFrames != null) {
                return false;
            }
        }
        else if (!hexFrames.equals(other.hexFrames)) {
            return false;
        }
        if (latitude == null) {
            if (other.latitude != null) {
                return false;
            }
        }
        else if (!latitude.equals(other.latitude)) {
            return false;
        }
        if (locked != other.locked) {
            return false;
        }
        if (longitude == null) {
            if (other.longitude != null) {
                return false;
            }
        }
        else if (!longitude.equals(other.longitude)) {
            return false;
        }
        if (password == null) {
            if (other.password != null) {
                return false;
            }
        }
        else if (!password.equals(other.password)) {
            return false;
        }
        if (siteId == null) {
            if (other.siteId != null) {
                return false;
            }
        }
        else if (!siteId.equals(other.siteId)) {
            return false;
        }
        if (username == null) {
            if (other.username != null) {
                return false;
            }
        }
        else if (!username.equals(other.username)) {
            return false;
        }
        return true;
    }

}
