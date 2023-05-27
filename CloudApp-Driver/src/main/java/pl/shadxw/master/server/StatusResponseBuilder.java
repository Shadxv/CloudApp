package pl.shadxw.master.server;


import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;

public class StatusResponseBuilder {

    private int maxPlayers;
    private int onlinePlayers;
    private List<String> players;

    private final String motd;
    private final File icon;
    private final String base64Icon;

    public StatusResponseBuilder(int maxPlayers, int onlinePlayers, String motd, String iconPath) throws IOException {
        this.maxPlayers = maxPlayers;
        this.onlinePlayers = onlinePlayers;
        this.motd = motd;
        this.icon = new File(iconPath);

        byte[] imageBytes = Files.readAllBytes(icon.toPath());
        this.base64Icon = Base64.getEncoder().encodeToString(imageBytes);
    }

    public String build(int protocolVersion) throws JSONException {
        String versionName = ProtocolVersion.getProtocol(protocolVersion).getName();

        return  new JSONObject()
                .put("version", new JSONObject()
                        .put("name", versionName)
                        .put("protocol", protocolVersion))
                .put("players", new JSONObject()
                        .put("max", this.maxPlayers)
                        .put("online", this.onlinePlayers)
                        .put("sample", new JSONArray()))
                .put("description", new JSONObject()
                        .put("text", this.motd))
                .put("favicon", "data:image/png;base64," + this.base64Icon)
                .toString();
    }


}
