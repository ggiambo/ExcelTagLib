package net.rcfmedia.taglib.excel.tags.net.rcfmedia.taglib.excel.utils;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;

public enum Valign implements PoiWrapper {

    CENTER( HSSFCellStyle.VERTICAL_CENTER ),
    TOP( HSSFCellStyle.VERTICAL_TOP ),
    BOTTOM( HSSFCellStyle.VERTICAL_BOTTOM );

    private final short poiValue;

    private Valign( short poiAlign ) {
        this.poiValue = poiAlign;
    }

    public Short poiValue() {
        return poiValue;
    }

}


