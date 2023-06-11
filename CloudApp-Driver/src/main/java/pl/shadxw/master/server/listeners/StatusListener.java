package pl.shadxw.master.server.listeners;

import lombok.Getter;
import org.json.JSONException;
import pl.shadxw.api.util.JSONChat;
import pl.shadxw.core.console.MessageType;
import pl.shadxw.driver.CloudAppDriver;
import pl.shadxw.master.CloudAppMaster;
import pl.shadxw.master.network.NetworkManager;
import pl.shadxw.master.protocol.status.*;
import pl.shadxw.master.server.JSONPingResponseBuilder;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StatusListener implements PacketStatusInListener {

    @Getter private final NetworkManager networkManager;
    private boolean hasRequestedStatus;

    public StatusListener(NetworkManager networkManager){
        this.networkManager = networkManager;
        this.hasRequestedStatus = false;
    }

    @Override
    public void handleStatusRequest(PacketStatusInInfo packetStatusInStatus) {
        if(hasRequestedStatus){
            this.networkManager.disconnect(new JSONChat().append("Already Requested").end().build());
        } else {
            this.hasRequestedStatus = true;
            try{
                this.networkManager.sendPacket(new PacketStatusOutInfo(
                        ((CloudAppMaster)CloudAppDriver.getApp()).getMinecraftServer().getStatusResponseBuilder()
                                .build(this.networkManager.getProtocolVersion())));
            } catch (JSONException e){
                CloudAppDriver.getApp().getConsole().writeLine(e.getMessage(), MessageType.ERROR);
                this.networkManager.disconnect(new JSONChat().append(e.getMessage()).end().build());
            }
        }
    }

    @Override
    public void handlePingRequest(PacketStatusInPing packetStatusInPing) {
        this.networkManager.sendPacket(new PacketStatusOutPong(packetStatusInPing.getTime()));
        this.networkManager.disconnect(new JSONChat().append("Finished").end().build());
    }

}
