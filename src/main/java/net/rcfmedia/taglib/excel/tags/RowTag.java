package net.rcfmedia.taglib.excel.tags;

import javax.servlet.jsp.JspException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.rcfmedia.taglib.excel.tags.net.rcfmedia.taglib.excel.utils.Borders;

/**
 * Handles the <xls:row> tag
 *
 * @author ggiambo@gmail.com
 */
public class RowTag extends ExcelBaseTag {

    private static final Logger LOG = LoggerFactory.getLogger( RowTag.class );
    private static final long serialVersionUID = 1L;

    private String height;
    private Borders border;

    @Override
    public int doStartTag() throws JspException {
        if( findAncestorWithClass( this, SheetTag.class ) == null ) {
            throw new JspException( "No parent <xls:sheet> tag found" );
        }
        // create a new row
        getWorkbookWrapper().createRow( height, border );
        return EVAL_BODY_INCLUDE;
    }

    public void setHeight( String height ) {
        this.height = height;
    }

    public void setBorder( String borders ) {
        border = new Borders( borders );
    }

}
