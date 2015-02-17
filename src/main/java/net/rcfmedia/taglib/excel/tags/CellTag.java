package net.rcfmedia.taglib.excel.tags;

import javax.servlet.jsp.JspException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.rcfmedia.taglib.excel.tags.net.rcfmedia.taglib.excel.utils.Align;
import net.rcfmedia.taglib.excel.tags.net.rcfmedia.taglib.excel.utils.Borders;
import net.rcfmedia.taglib.excel.tags.net.rcfmedia.taglib.excel.utils.Valign;

/**
 * Handles a <xls:cell> tag
 *
 * @author ggiambo@gmail.com
 */
public class CellTag extends ExcelBaseTag {

    private static final Logger LOG = LoggerFactory.getLogger( ExcelBaseTag.class );
    private static final long serialVersionUID = 1L;

    private Align align;
    private Valign valign;
    private String width;
    private Borders border;

    @Override
    public int doStartTag() throws JspException {
        if( findAncestorWithClass( this, RowTag.class ) == null ) {
            throw new JspException( "No parent <xls:row> tag found" );
        }
        return EVAL_BODY_BUFFERED;
    }

    @Override
    public int doEndTag() throws JspException {
        // create the cell and set the content
        String cellContent = getBodyContent() == null ? "" : getBodyContent().getString();
        getWorkbookWrapper().createCell( cellContent, align, valign, width, border );
        return EVAL_PAGE;
    }

    public void setAlign( String align ) {
        try {
            this.align = Align.valueOf( align.toUpperCase() );
        } catch( IllegalArgumentException e ) {
            LOG.warn( "Ignoring unknown value of 'align=" + align + "'" );
        }
    }

    public void setValign( String valign ) {
        try {
            this.valign = Valign.valueOf( valign.toUpperCase() );
        } catch( IllegalArgumentException e ) {
            LOG.warn( "Ignoring unknown value of 'valign=" + valign + "'" );
        }
    }

    public void setWidth( String width ) {
        this.width = width;
    }

    public void setBorder( String borders ) {
        border = new Borders( borders );
    }

}
