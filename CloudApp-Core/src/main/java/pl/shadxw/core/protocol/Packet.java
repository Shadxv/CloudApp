package pl.shadxw.core.protocol;

import pl.shadxw.core.network.PacketListener;

public interface Packet <T extends PacketListener> {

    void handle(T t0);

}
