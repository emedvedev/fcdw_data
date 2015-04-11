// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UserRanking2")
public class UserRankingEntity implements Serializable {

    private static final long serialVersionUID = -1336911362484026855L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long satelliteId;
    private String siteId;
    private Long number;
    private Timestamp latestUploadDate;
    private Timestamp firstUploadDate;
    private String siteAlias;

    public UserRankingEntity(final Long satelliteId, final String siteId, 
            final Long number, final Timestamp latestUploadDate, final Timestamp firstUploadDate) {
        this.satelliteId = satelliteId;
        this.siteId = siteId;
        this.number = number;
        this.latestUploadDate = latestUploadDate;
        this.siteAlias = siteId;
        this.firstUploadDate = firstUploadDate;
    }

    public UserRankingEntity() {
    }

    public final Long getId() {
        return id;
    }

    public final void setId(Long id) {
        this.id = id;
    }

    public final Long getSatelliteId() {
        return satelliteId;
    }

    public final void setSatelliteId(Long satlliteId) {
        this.satelliteId = satlliteId;
    }

    public final String getSiteId() {
        return siteId;
    }

    public final void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public final Long getNumber() {
        return number;
    }

    public final void setNumber(Long number) {
        this.number = number;
    }

    public final Timestamp getLatestUploadDate() {
        return latestUploadDate;
    }

    public final void setLatestUploadDate(Timestamp latestUploadDate) {
        this.latestUploadDate = latestUploadDate;
    }

    public final String getSiteAlias() {
        return siteAlias;
    }

    public final void setSiteAlias(String siteAlias) {
        this.siteAlias = siteAlias;
    }

    public final Timestamp getFirstUploadDate() {
        return firstUploadDate;
    }

    public final void setFirstUploadDate(Timestamp firstUploadDate) {
        this.firstUploadDate = firstUploadDate;
    }

}
