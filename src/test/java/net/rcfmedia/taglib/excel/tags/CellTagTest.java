package net.rcfmedia.taglib.excel.tags;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import net.rcfmedia.taglib.excel.tags.net.rcfmedia.taglib.excel.utils.Align;
import net.rcfmedia.taglib.excel.tags.net.rcfmedia.taglib.excel.utils.Border;
import net.rcfmedia.taglib.excel.tags.net.rcfmedia.taglib.excel.utils.BorderDef;
import net.rcfmedia.taglib.excel.tags.net.rcfmedia.taglib.excel.utils.BorderType;
import net.rcfmedia.taglib.excel.tags.net.rcfmedia.taglib.excel.utils.Borders;
import net.rcfmedia.taglib.excel.tags.net.rcfmedia.taglib.excel.utils.Valign;

public class CellTagTest {

    @Test
    public void testSetAlign() throws Exception {

        CellTag cellTag;

        cellTag = new CellTag();
        cellTag.setAlign( "center" );
        Assert.assertEquals( Align.CENTER, getFieldValue( cellTag, "align" ) );

        cellTag = new CellTag();
        cellTag.setAlign( "left" );
        Assert.assertEquals( Align.LEFT, getFieldValue( cellTag, "align" ) );

        cellTag = new CellTag();
        cellTag.setAlign( "right" );
        Assert.assertEquals( Align.RIGHT, getFieldValue( cellTag, "align" ) );

        cellTag = new CellTag();
        cellTag.setAlign( "XXX" );
        Assert.assertNull( getFieldValue( cellTag, "align" ) );
    }

    @Test
    public void testSetValign() throws Exception {

        CellTag cellTag;

        cellTag = new CellTag();
        cellTag.setValign( "center" );
        Assert.assertEquals( Valign.CENTER, getFieldValue( cellTag, "valign" ) );

        cellTag = new CellTag();
        cellTag.setValign( "top" );
        Assert.assertEquals( Valign.TOP, getFieldValue( cellTag, "valign" ) );

        cellTag = new CellTag();
        cellTag.setValign( "bottom" );
        Assert.assertEquals( Valign.BOTTOM, getFieldValue( cellTag, "valign" ) );

        cellTag = new CellTag();
        cellTag.setValign( "XXX" );
        Assert.assertNull( getFieldValue( cellTag, "valign" ) );
    }


    @Test
    public void TestSetBorders() throws Exception {

        CellTag cellTag;
        Borders borders;

        cellTag = new CellTag();
        cellTag.setBorder( "top:none;left:medium; bottom : hair;right: thick ; all :default" );
        borders = (Borders) getFieldValue( cellTag, "border" );
        List<Border> borderList = new ArrayList<>();
        for( Border border : borders ) {
            borderList.add( border );
        }
        Assert.assertEquals( 5, borderList.size() );

        Assert.assertTrue( borderList.contains( new Border( BorderDef.TOP, BorderType.NONE ) ) );
        Assert.assertTrue( borderList.contains( new Border( BorderDef.LEFT, BorderType.MEDIUM ) ) );
        Assert.assertTrue( borderList.contains( new Border( BorderDef.BOTTOM, BorderType.HAIR ) ) );
        Assert.assertTrue( borderList.contains( new Border( BorderDef.RIGHT, BorderType.THICK ) ) );
        Assert.assertTrue( borderList.contains( new Border( BorderDef.ALL, BorderType.DEFAULT ) ) );

        cellTag = new CellTag();
        cellTag.setBorder( "XXX" );
        borders = (Borders) getFieldValue( cellTag, "border" );
        Assert.assertFalse( borders.iterator().hasNext() );
    }

    private Object getFieldValue( CellTag cellTag, String fieldName ) throws Exception {
        Field field = CellTag.class.getDeclaredField( fieldName );
        field.setAccessible( true );
        return field.get( cellTag );
    }

}
