package pl.shadxw.core.protocol;

public interface Decoder {

    void decodeMinecraftPacket(int packetID);

    void decodeCloudAppPacket(int packetID);

}
