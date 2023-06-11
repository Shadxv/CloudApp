package pl.shadxw.master.server.listeners;

import lombok.Getter;
import pl.shadxw.api.util.JSONChat;
import pl.shadxw.master.models.ConnectedPlayer;
import pl.shadxw.master.network.NetworkManager;
import pl.shadxw.master.protocol.login.PacketLoginInListener;
import pl.shadxw.master.protocol.login.PacketLoginInStart;

import java.util.UUID;

public class LoginListener implements PacketLoginInListener {

    @Getter private final ConnectedPlayer connectedPlayer;

    private LoginState loginState;

    public LoginListener(NetworkManager networkManager){
        this.connectedPlayer = new ConnectedPlayer(networkManager);
        this.loginState = LoginState.STARTING;
    }

    @Override
    public void handleLoginStart(PacketLoginInStart packetLoginInStart) {
        if(this.connectedPlayer.isLoggedIn()){
            this.connectedPlayer.getNetworkManager().disconnect(new JSONChat().append("Already logged in").end().build());
        } else {
            if(this.loginState != LoginState.STARTING) this.connectedPlayer.getNetworkManager().disconnect(new JSONChat().append("Already received Login Start Packet").end().build());
            this.connectedPlayer.setName(packetLoginInStart.getName());
            this.connectedPlayer.setUuid(packetLoginInStart.getUuid().orElse(UUID.randomUUID()));
            this.loginState = LoginState.STARTED;
            this.connectedPlayer.getNetworkManager().disconnect(new JSONChat().append("Name: " + this.connectedPlayer.getName() + ",\nUUID: " + this.connectedPlayer.getUuid()).end().build());
        }
    }

    private enum LoginState {
        STARTING,
        STARTED,
        ENCRYPTION,
        AUTH,
        SUCCESS

    }

}
