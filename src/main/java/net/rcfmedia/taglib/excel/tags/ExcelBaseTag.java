package net.rcfmedia.taglib.excel.tags;

import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Base abstract class for the <xls:...> tags
 *
 * @author ggiambo@gmail.com
 */
public abstract class ExcelBaseTag extends BodyTagSupport {

    private static final long serialVersionUID = 1L;

    private static final String EXCELWORKBOOK = "excelWorkbookPageContextAttribute";

    /**
     * Creates a new workbook and put it in the pageContext
     */
    protected void createWorkbookWrapper() {
        pageContext.setAttribute( EXCELWORKBOOK, new WorkbookWrapper() );
    }

    /**
     * Get the actual workbook present in the pageContext
     *
     * @return
     */
    protected WorkbookWrapper getWorkbookWrapper() {
        return (WorkbookWrapper) pageContext.getAttribute( EXCELWORKBOOK );
    }

}
