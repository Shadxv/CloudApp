package pl.shadxw.master.server.listeners;

import lombok.Getter;
import org.json.JSONException;
import pl.shadxw.core.console.MessageType;
import pl.shadxw.driver.CloudAppDriver;
import pl.shadxw.master.CloudAppMaster;
import pl.shadxw.master.network.NetworkManager;
import pl.shadxw.master.protocol.status.*;
import pl.shadxw.master.server.JSONPingResponseBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            this.networkManager.disconnect();
        } else {
            this.hasRequestedStatus = true;
            List<String> motd = Arrays.asList("This server uses CloudApp by Shadxw#2137");
            List<String> kick = Arrays.asList("This server uses CloudApp by Shadxw#2137");
            /*String response = new JSONPingResponseBuilder(
                    "localhost",
                    25565,
                    "data:image/png;base64,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",
                    this.networkManager.getProtocolVersion(),
                    "1.19.3",
                    motd,
                    new ArrayList<String>(),
                    0,
                    20,
                    kick
            ).buildServerPingResponse();*/
            try{
                this.networkManager.sendPacket(new PacketStatusOutInfo(
                        ((CloudAppMaster)CloudAppDriver.getApp()).getMinecraftServer().getStatusResponseBuilder()
                                .build(this.networkManager.getProtocolVersion())));
            } catch (JSONException e){
                CloudAppDriver.getApp().getConsole().writeLine(e.getMessage(), MessageType.ERROR);
                this.networkManager.disconnect();
            }
        }
    }

    @Override
    public void handlePingRequest(PacketStatusInPing packetStatusInPing) {
        this.networkManager.sendPacket(new PacketStatusOutPong(packetStatusInPing.getTime()));
        this.networkManager.disconnect();
    }

}
