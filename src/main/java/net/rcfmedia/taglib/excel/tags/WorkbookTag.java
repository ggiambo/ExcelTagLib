package net.rcfmedia.taglib.excel.tags;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

/**
 * Handles the <xls:workbook> tag
 *
 * @author ggiambo@gmail.com
 */
public class WorkbookTag extends ExcelBaseTag {

    private static final long serialVersionUID = 1L;

    private String filename;

    @Override
    public int doStartTag() throws JspException {
        createWorkbookWrapper();
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws JspException {
        HttpServletResponse res = (HttpServletResponse) pageContext.getResponse();
        // reset the actual content written by the JSP
        res.reset();
        // prepare the headers
        res.setContentType( "application/vnd.ms-excel" );
        res.setHeader( "content-disposition", "attachment;filename=" + filename );
        res.setHeader( "Pragma", "" );
        res.setHeader( "Cache-Control", "" );
        res.setHeader( "Cache-Control", "must-revalidate" );
        try {
            // write the generated excel in the response
            OutputStream out = res.getOutputStream();
            getWorkbookWrapper().writeWorkBook( out );
            out.flush();
            out.close();
            // needed to avoid IllegalStateException
            pageContext.getOut().clear();
        } catch( IOException e ) {
            throw new JspException( e );
        }
        return SKIP_PAGE;
    }

    public void setFilename( String filename ) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

}
