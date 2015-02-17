package net.rcfmedia.taglib.excel.tags;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import net.rcfmedia.taglib.excel.tags.net.rcfmedia.taglib.excel.utils.Align;
import net.rcfmedia.taglib.excel.tags.net.rcfmedia.taglib.excel.utils.Borders;
import net.rcfmedia.taglib.excel.tags.net.rcfmedia.taglib.excel.utils.BorderDef;
import net.rcfmedia.taglib.excel.tags.net.rcfmedia.taglib.excel.utils.BorderType;
import net.rcfmedia.taglib.excel.tags.net.rcfmedia.taglib.excel.utils.Border;
import net.rcfmedia.taglib.excel.tags.net.rcfmedia.taglib.excel.utils.Valign;

/**
 * Wraps the POI functionality in order to be used by the Excel Tag Library
 *
 * @author ggiambo@gmail.com
 */
public class WorkbookWrapper implements Serializable {

    private static final long serialVersionUID = 1L;

    private int sheetCount;

    private HSSFWorkbook wb;
    private HSSFSheet actualSheet;
    private HSSFRow actualRow;
    private HSSFCell actualCell;
    private HSSFCellStyle defaultRowStyle;
    private Map<Integer, String> sheetColumnWidth; // Map<columnIndex, width>
    private Map<CellCacheKey, HSSFCellStyle> stylesCache;
    private Borders actualRowBorderDefinitions;
    private String actualRowHeight;

    /**
     * Protected constructor
     */
    protected WorkbookWrapper() {
        wb = new HSSFWorkbook();
        sheetCount = 0;
        sheetColumnWidth = new HashMap<Integer, String>();
        stylesCache = new HashMap<CellCacheKey, HSSFCellStyle>();
        // set the default font
        HSSFFont defaultFont = wb.createFont();
        defaultFont.setFontHeightInPoints( (short) 10 );
        defaultFont.setFontName( "Arial Unicode MS" );
        // set the default row style
        defaultRowStyle = wb.createCellStyle();
        defaultRowStyle.setFont( defaultFont );
    }

    /**
     * Writes the workbook to the specified output stream.
     *
     * @param out
     * @throws IOException
     */
    protected void writeWorkBook( OutputStream out ) throws IOException {
        wb.write( out );
    }

    /**
     * Creates a new sheet.
     *
     * @param sheetName If empty, <i>Sheet_$index</i> will be used.
     */
    protected void createSheet( String sheetName ) {
        sheetCount++;
        if( isEmpty( sheetName ) ) {
            sheetName = "Sheet_" + sheetCount;
        }
        actualSheet = wb.createSheet( sheetName );
        actualRow = null;
        sheetColumnWidth.clear();
    }

    /**
     * Creates a new row.
     *
     * @param height row height in points, may be empty
     * @param border border definition, may be empty
     */
    protected void createRow( String height, Borders border ) {
        actualRowHeight = height;
        // create the row
        int rowNum = 0;
        if( actualRow != null ) {
            rowNum = actualRow.getRowNum() + 1;
        }
        actualRow = actualSheet.createRow( rowNum );

        // set the border definitions for the whole row
        actualRowBorderDefinitions = border;

        // set the row style
        actualRow.setRowStyle( getCellStyle( actualRowHeight, null, null, actualRowBorderDefinitions ) );
        actualCell = null;
    }

    /**
     * Create a new cell with the supplied values.
     *
     * @param align   may be <i>center</i>, <i>left</i> or <i>right</i>
     * @param valign  may be <i>center</i>, <i>top</i> or <i>bottom</i>
     * @param width   width in points, may be empty
     * @param borders border definition
     */
    protected void createCell( String cellContent, Align align, Valign valign, String width, Borders borders ) {
        // create the cell
        int cellIndex = 0;
        if( actualCell != null ) {
            cellIndex = actualCell.getColumnIndex() + 1;
        }
        actualCell = actualRow.createCell( cellIndex );
        actualCell.setCellType( HSSFCell.CELL_TYPE_STRING );

        // set the cell content
        if( cellContent != null ) {
            actualCell.setCellValue( new HSSFRichTextString( cellContent.trim() ) );
        }

        // inherit the border definition from the row
        Borders cellBorders = new Borders( actualRowBorderDefinitions );
        // overwrite with actual cell borders
        if( borders != null && borders.iterator().hasNext() ) {
            for( Border b : borders ) {
                cellBorders.add( b );
            }
        }

        // set the style
        actualCell.setCellStyle( getCellStyle( actualRowHeight, align, valign, cellBorders ) );

        // width map, set them at the very end of the sheet (Expensive operation)
        sheetColumnWidth.put( cellIndex, width );
    }

    /**
     * Set the column width for the whole sheet.<br/>
     * Do this operation once at the end and not for every cell,
     * <tt>autoSizeColumn(cellIndex)</tt> is an expensive operation.
     */
    protected void postProcessSheet() {
        for( Map.Entry<Integer, String> entry : sheetColumnWidth.entrySet() ) {
            short cellIndex = entry.getKey().shortValue();
            if( "auto".equals( entry.getValue() ) ) {
                actualSheet.autoSizeColumn( cellIndex );
            } else if( entry.getValue() != null ) {
                try {
                    double cellWidth = Double.parseDouble( entry.getValue() );
                    cellWidth = cellWidth * 36.6; // empirical
                    actualSheet.setColumnWidth( cellIndex, (int) cellWidth );
                } catch( NumberFormatException e ) {
                    // ignore
                }
            }
        }
    }

    /**
     * Get a (Cached) cell style if exists, otherwise create a new one.<br/>
     * This is needed in order to avoid the "Too Many Styles" warning message on
     * big Excel workbooks.
     *
     * @param height
     * @param align
     * @param valign
     * @param borders
     * @return
     */
    private HSSFCellStyle getCellStyle( String height, Align align, Valign valign, Borders borders ) {
        CellCacheKey cacheKey = new CellCacheKey( height, align, valign, borders );
        HSSFCellStyle cellStyle = stylesCache.get( cacheKey );

        // create a new cell
        if( cellStyle == null ) {
            // height
            if( height != null ) {
                try {
                    float rowHeight = Float.parseFloat( height );
                    if( rowHeight == 0 ) {
                        actualRow.setZeroHeight( true ); // special case
                    } else {
                        rowHeight = rowHeight * 0.75f; // empirical
                        actualRow.setHeightInPoints( rowHeight );
                    }
                } catch( NumberFormatException e ) {
                    // just ignore
                }
            }

            // create a new cellStyle only if supplied values are valid
            if( isValidCellStyle( align, valign, borders ) ) {
                cellStyle = wb.createCellStyle();

                if( null != align ) {
                    // horizontal alignment
                    Short cellAlign = align.poiValue();
                    if( cellAlign != null ) {
                        cellStyle.setAlignment( cellAlign );
                    }
                }

                if( null != valign ) {
                    // vertical alignment
                    Short cellValign = valign.poiValue();
                    if( cellValign != null ) {
                        cellStyle.setVerticalAlignment( cellValign );
                    }
                }

                if( null != borders ) {
                    // border
                    for( Border border : borders ) {
                        BorderDef borderDef = border.getBorderDef();
                        BorderType borderType = border.getBorderType();
                        if( BorderType.NONE == borderType ) {
                            // trick to eliminate also the "invisible" border
                            cellStyle.setFillForegroundColor( new HSSFColor.WHITE().getIndex() );
                            cellStyle.setFillPattern( HSSFCellStyle.SOLID_FOREGROUND );
                        }
                        if( BorderDef.TOP == borderDef ) {
                            cellStyle.setBorderTop( borderType.poiValue() );
                        } else if( BorderDef.BOTTOM == borderDef ) {
                            cellStyle.setBorderBottom( borderType.poiValue() );
                        } else if( BorderDef.RIGHT == borderDef ) {
                            cellStyle.setBorderRight( borderType.poiValue() );
                        } else if( BorderDef.LEFT == borderDef ) {
                            cellStyle.setBorderLeft( borderType.poiValue() );
                        } else if( BorderDef.ALL == borderDef ) {
                            cellStyle.setBorderTop( borderType.poiValue() );
                            cellStyle.setBorderBottom( borderType.poiValue() );
                            cellStyle.setBorderRight( borderType.poiValue() );
                            cellStyle.setBorderLeft( borderType.poiValue() );
                        }
                    }
                }

                stylesCache.put( cacheKey, cellStyle );
            }
        }
        return cellStyle == null ? defaultRowStyle : cellStyle;
    }

    /**
     * Returns true if the supplied cell style is a valid one (At least one definition is ok)
     *
     * @param align
     * @param valign
     * @param border
     * @return
     */
    private boolean isValidCellStyle( Align align, Valign valign, Borders border ) {
        if( align == null && valign == null && ( border == null || !border.iterator().hasNext() ) ) {
            return false;
        }
        return true;
    }

    /**
     * Check for an empty string
     *
     * @param string
     * @return
     */
    private boolean isEmpty( String string ) {
        return string == null || string.trim().length() == 0;
    }

    /**
     * Key for the cell style cache
     *
     * @author ggiambo@gmail.com
     */
    private class CellCacheKey {

        private String height;
        private Align align;
        private Valign valign;
        private Borders border;

        public CellCacheKey( String height, Align align, Valign valign, Borders border ) {
            this.height = height;
            this.align = align;
            this.valign = valign;
            this.border = border;
        }

        @Override
        public boolean equals( Object obj ) {
            if( obj == this ) {
                return true;
            }
            if( obj == null || !( obj instanceof CellCacheKey ) ) {
                return false;
            }

            CellCacheKey o = (CellCacheKey) obj;
            return
                ( height == o.height || height != null && height.equals( o.height ) ) &&
                    ( align == o.align || align != null && align.equals( o.align ) ) &&
                    ( valign == o.valign || valign != null && valign.equals( o.valign ) ) &&
                    ( border == o.border || border != null && border.equals( o.border ) );
        }

        @Override
        public int hashCode() {
            int hashcode = 0;
            hashcode *= 31;
            if( height != null ) {
                hashcode += height.hashCode();
            }
            hashcode *= 31;
            if( align != null ) {
                hashcode += align.hashCode();
            }
            hashcode *= 31;
            if( valign != null ) {
                hashcode += valign.hashCode();
            }
            hashcode *= 31;
            if( border != null ) {
                hashcode += border.hashCode();
            }
            hashcode *= 31;
            return hashcode;
        }
    }
}
