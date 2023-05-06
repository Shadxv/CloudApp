package pl.shadxw.core.models;

public interface IPacket {

    void encode();

    void decode();

    boolean execute();

    boolean respond();


}
