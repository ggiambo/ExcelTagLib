package net.rcfmedia.taglib.excel.tags.net.rcfmedia.taglib.excel.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Borders implements Iterable<Border> {

    private static final Logger LOG = LoggerFactory.getLogger( Borders.class );

    private Set<Border> borders;

    public Borders( Borders borders ) {
        this.borders = new HashSet<>();
        if( null == borders ) {
            return;
        }
        for( Border b : borders ) {
            this.borders.add( b );
        }
    }

    public Borders( String borders ) {
        if( borders != null && borders.trim().length() != 0 ) {
            this.borders = new HashSet<>();
            for( String component : borders.split( ";" ) ) {
                String[] keyVal = component.split( ":" );
                if( keyVal.length == 2 ) {
                    String borderDef = keyVal[ 0 ].trim().toUpperCase();
                    String borderType = keyVal[ 1 ].trim().toUpperCase();
                    try {
                        this.borders.add( new Border( BorderDef.valueOf( borderDef ), BorderType.valueOf( borderType ) ) );
                    } catch( IllegalArgumentException e ) {
                        LOG.warn( "Ignoring unknown value of 'borders=" + borders + "'" );
                    }
                }
            }
        }
    }

    public void add( Border border ) {
        borders.add( border );
    }

    @Override
    public Iterator<Border> iterator() {
        return borders.iterator();
    }

}
