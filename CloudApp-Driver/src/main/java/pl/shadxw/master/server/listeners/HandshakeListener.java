package pl.shadxw.master.server.listeners;

import lombok.Getter;
import pl.shadxw.api.util.JSONChat;
import pl.shadxw.master.protocol.ClientState;
import pl.shadxw.master.protocol.handshake.PacketHandshakeInListener;
import pl.shadxw.master.exceptions.IncorrectValueException;
import pl.shadxw.master.network.NetworkManager;
import pl.shadxw.master.protocol.handshake.PacketHandshakeIn;
import pl.shadxw.master.protocol.login.PacketLoginOutDisconnect;

public class HandshakeListener implements PacketHandshakeInListener {

    @Getter private final NetworkManager networkManager;

    public HandshakeListener(NetworkManager networkManager){
        this.networkManager = networkManager;
    }

    @Override
    public void handleHandshake(PacketHandshakeIn packetHandshakeIn) {
        switch (packetHandshakeIn.getState()){
            case 1 -> { //Ping state
                //CloudAppDriver.getDriver().getConsole().writeLine("Recived handshake packet!", MessageType.NORMAL);
                this.networkManager.setPacketListener(new StatusListener(this.networkManager));
                this.networkManager.setClientState(ClientState.STATUS);
            }
            case 2 -> { //Login state
                //CloudAppDriver.getDriver().getConsole().writeLine("Recived login packet!", MessageType.NORMAL);
                this.networkManager.setPacketListener(new LoginListener(this.networkManager));
                this.networkManager.setClientState(ClientState.LOGIN);
            }
            default -> {
                throw new IncorrectValueException("Incorrect state: " + packetHandshakeIn.getState());
            }
        }
        this.networkManager.setProtocolVersion(packetHandshakeIn.getProtocolVersion());
    }
}
