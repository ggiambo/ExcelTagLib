package net.rcfmedia.taglib.excel.tags.net.rcfmedia.taglib.excel.utils;

public class Border {

    private final BorderDef borderDef;
    private final BorderType borderType;

    public Border( BorderDef borderDef, BorderType borderType ) {
        this.borderDef = borderDef;
        this.borderType = borderType;
    }

    public BorderDef getBorderDef() {
        return borderDef;
    }

    public BorderType getBorderType() {
        return borderType;
    }

    @Override
    public int hashCode() {
        return borderDef.hashCode() * borderType.hashCode();
    }

    @Override
    public boolean equals( Object obj ) {
        if( this == obj ) {
            return true;
        }
        if( !( obj instanceof Border ) ) {
            return false;
        }
        Border other = (Border) obj;
        return this.borderDef.equals( other.getBorderDef() ) && this.borderType.equals( other.getBorderType() );
    }

}
