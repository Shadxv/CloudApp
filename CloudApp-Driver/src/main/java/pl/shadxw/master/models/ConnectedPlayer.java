package pl.shadxw.master.models;

import lombok.Getter;
import lombok.Setter;
import pl.shadxw.master.network.NetworkManager;

import java.util.UUID;

public class ConnectedPlayer {

    @Getter @Setter private String name;
    @Getter @Setter private UUID uuid;
    @Getter @Setter boolean loggedIn;
    @Getter private final NetworkManager networkManager;

    public ConnectedPlayer(NetworkManager networkManager){
        this.loggedIn = false;
        this.networkManager = networkManager;
    }

}
