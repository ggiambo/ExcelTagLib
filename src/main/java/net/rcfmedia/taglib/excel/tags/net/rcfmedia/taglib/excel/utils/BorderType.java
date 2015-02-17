package net.rcfmedia.taglib.excel.tags.net.rcfmedia.taglib.excel.utils;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;

public enum BorderType implements PoiWrapper {

    DEFAULT( HSSFCellStyle.BORDER_NONE ),
    NONE( HSSFCellStyle.BORDER_NONE ),
    THIN( HSSFCellStyle.BORDER_THIN ),
    MEDIUM( HSSFCellStyle.BORDER_MEDIUM ),
    THICK( HSSFCellStyle.BORDER_THICK ),
    DASH( HSSFCellStyle.BORDER_DASHED ),
    HAIR( HSSFCellStyle.BORDER_HAIR );

    private final short poiValue;

    private BorderType( short poiValue ) {
        this.poiValue = poiValue;
    }

    public Short poiValue() {
        return poiValue;
    }

}
