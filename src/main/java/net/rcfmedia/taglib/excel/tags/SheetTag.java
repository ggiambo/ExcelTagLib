package net.rcfmedia.taglib.excel.tags;

import javax.servlet.jsp.JspException;

/**
 * Handles the <xls:sheet> tag
 *
 * @author ggiambo@gmail.com
 */
public class SheetTag extends ExcelBaseTag {

    private static final long serialVersionUID = 1L;

    private String name;

    @Override
    public int doStartTag() throws JspException {
        if( findAncestorWithClass( this, WorkbookTag.class ) == null ) {
            throw new JspException( "No parent <xls:workbook> tag found" );
        }
        // create the sheet
        getWorkbookWrapper().createSheet( name );
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws JspException {
        getWorkbookWrapper().postProcessSheet();
        return super.doEndTag();
    }

    public void setName( String name ) {
        this.name = name;
    }

}
