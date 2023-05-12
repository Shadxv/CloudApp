package pl.shadxw.master.protocol.status;

import pl.shadxw.core.network.PacketListener;

public interface PacketStatusOutListener extends PacketListener {

    default void handle(){}

}
