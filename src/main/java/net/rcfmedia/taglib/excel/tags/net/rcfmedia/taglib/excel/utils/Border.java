package net.rcfmedia.taglib.excel.tags.net.rcfmedia.taglib.excel.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Border implements Iterable<NVPair<BorderDef, BorderType>> {

    private static final Logger LOG = LoggerFactory.getLogger( Border.class );

    private Set<NVPair<BorderDef, BorderType>> borders;

    public Border( Border border ) {
        this.borders = new HashSet<>();
        if( null == border ) {
            return;
        }
        for( NVPair<BorderDef, BorderType> b : border ) {
            borders.add( b );
        }
    }

    public Border( String borders ) {
        if( borders != null && borders.trim().length() != 0 ) {
            this.borders = new HashSet<>();
            for( String component : borders.split( ";" ) ) {
                String[] keyVal = component.split( ":" );
                if( keyVal.length == 2 ) {
                    String borderDef = keyVal[ 0 ].trim().toUpperCase();
                    String borderType = keyVal[ 1 ].trim().toUpperCase();
                    try {
                        this.borders.add( new NVPair<>( BorderDef.valueOf( borderDef ), BorderType.valueOf( borderType ) ) );
                    } catch( IllegalArgumentException e ) {
                        LOG.warn( "Ignoring unknown value of 'borders=" + borders + "'" );
                    }
                }
            }
        }
    }

    public void add( NVPair<BorderDef, BorderType> border ) {
        borders.add( border );
    }

    @Override
    public Iterator<NVPair<BorderDef, BorderType>> iterator() {
        return borders.iterator();
    }

}
