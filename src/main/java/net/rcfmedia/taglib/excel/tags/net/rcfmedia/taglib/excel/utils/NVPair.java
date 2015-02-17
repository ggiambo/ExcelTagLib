package net.rcfmedia.taglib.excel.tags.net.rcfmedia.taglib.excel.utils;

public class NVPair<N, V> {

    private N name;
    private V value;

    public NVPair( N name, V value ) {
        this.name = name;
        this.value = value;
    }

    public N getName() {
        return name;
    }

    public V getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return name.hashCode() * value.hashCode();
    }

    @Override
    public boolean equals( Object obj ) {
        if( this == obj ) {
            return true;
        }
        if( !( obj instanceof NVPair ) ) {
            return false;
        }
        NVPair other = (NVPair) obj;
        return this.getName().equals( other.getName() ) && this.getValue().equals( other.getValue() );
    }
}
