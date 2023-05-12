package pl.shadxw.master.protocol;

import lombok.Getter;
import pl.shadxw.master.exceptions.IncorrectValueException;
import pl.shadxw.master.network.NetworkManager;
import pl.shadxw.api.protocol.PacketDecoder;
import pl.shadxw.master.protocol.handshake.PacketHandshakeIn;
import pl.shadxw.master.protocol.status.PacketStatusInInfo;
import pl.shadxw.master.protocol.status.PacketStatusInPing;

public class MasterPacketDecoder extends PacketDecoder {

    @Getter private final NetworkManager networkManager;

    public MasterPacketDecoder(NetworkManager networkManager){
        this.networkManager = networkManager;
    }

    @Override
    public void decodeMinecraftPacket(int packetID) {
        //Zrobić Enum albo mape z id i classami pakietów, metode create itp itd;
        if(!networkManager.isRecivedHandshake()) packetID--;
        switch (packetID){
            case -1 -> {
                var packet = new PacketHandshakeIn(super.dataManager);
                super.out.add(packet);
            }
            case 0 -> {
                var packet = new PacketStatusInInfo();
                super.out.add(packet);
            }
            case 1 -> {
                var packet = new PacketStatusInPing(super.dataManager);
                super.out.add(packet);
            }
            default -> {
                throw new IncorrectValueException("Incorrect packet id: " + packetID);
            }
        }
    }

}
