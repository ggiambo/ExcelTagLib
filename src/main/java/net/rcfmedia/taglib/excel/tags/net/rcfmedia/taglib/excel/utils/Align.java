package net.rcfmedia.taglib.excel.tags.net.rcfmedia.taglib.excel.utils;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;

public enum Align implements PoiWrapper {

    CENTER( HSSFCellStyle.ALIGN_CENTER ),
    LEFT( HSSFCellStyle.ALIGN_LEFT ),
    RIGHT( HSSFCellStyle.ALIGN_RIGHT );

    private final short poiValue;

    private Align( short poiValue ) {
        this.poiValue = poiValue;
    }

    public Short poiValue() {
        return poiValue;
    }

}


