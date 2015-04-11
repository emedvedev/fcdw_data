// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.service.rest.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import uk.org.funcube.fcdw.domain.UserRankingEntity;
import uk.org.funcube.fcdw.server.shared.NameValuePair;

/**
 * @author g4dpz
 *
 */
public class QslData {

    private NameValuePair qsl;

    /**
     * @param findBySatelliteIdAndSiteId
     */
    public QslData(final List<UserRankingEntity> userRankings) {
        
        final SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz");
        
        if (!userRankings.isEmpty()) {
            final UserRankingEntity userRankingEntity = userRankings.get(0);
            qsl = new NameValuePair(userRankingEntity.getSiteId(), 
                    sdtf.format(new Date(userRankingEntity.getFirstUploadDate().getTime())));
            
        }
    }

    public final NameValuePair getQsl() {
        return qsl;
    }

}
