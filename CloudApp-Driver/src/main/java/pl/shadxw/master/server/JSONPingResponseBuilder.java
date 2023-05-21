package pl.shadxw.master.server;

import java.util.List;

import static pl.shadxw.api.util.StringUtilities.*;

public class JSONPingResponseBuilder {

    private String host;
    private int port;
    private String base64_icon;
    private int protocol;
    private String version_name;
    private List<String> motd;
    private List<String> players;
    private int connected_players;
    private int max_players;
    private List<String> kick_message;

    public JSONPingResponseBuilder() {
    }

    public JSONPingResponseBuilder(String host, int port, String base64_icon, int protocol, String version_name, List<String> motd, List<String> players, int connected_players, int max_players, List<String> kick_message) {
        this.host = host;
        this.port = port;
        this.base64_icon = base64_icon;
        this.protocol = protocol;
        this.version_name = version_name;
        this.motd = motd;
        this.players = players;
        this.connected_players = connected_players;
        this.max_players = max_players;
        this.kick_message = kick_message;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getBase64_icon() {
        return base64_icon;
    }

    public void setBase64_icon(String base64_icon) {
        this.base64_icon = base64_icon;
    }

    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public List<String> getMotd() {
        return motd;
    }

    public void setMotd(List<String> motd) {
        this.motd = motd;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public int getConnected_players() {
        return connected_players;
    }

    public void setConnected_players(int connected_players) {
        this.connected_players = connected_players;
    }

    public int getMax_players() {
        return max_players;
    }

    public void setMax_players(int max_players) {
        this.max_players = max_players;
    }

    public List<String> getKick_message() {
        return kick_message;
    }

    public void setKick_message(List<String> kick_message) {
        this.kick_message = kick_message;
    }

    public String buildServerPingResponse() {
        StringBuilder sb = new StringBuilder();

        // Version name
        String versionName = "Minecraft";
        if (this.getVersion_name() != null)
            if (!this.getVersion_name().isEmpty())
                versionName = parse(this.getVersion_name());
        sb.append("{\"version\":{\"name\":\"").append(versionName).append("\",");

        // Protocol
        sb.append("\"protocol\":").append(this.getProtocol()).append("},");

        // Max players
        sb.append("\"players\":{\"max\":").append(this.getMax_players()).append(",");

        // Online players
        sb.append("\"online\":").append(this.getConnected_players()).append(",");

        // Player sample
        sb.append("\"sample\":[");
        /*if (conf.getPlayers() != null) {
            if (conf.getPlayers().size() > 0) {
                int count = 0;
                for (final String player : FakeMCServer.getConfig().getPlayers()) {
                    if (count != 0)
                        sb.append(",");
                    count++;
                    sb.append("{\"name\":\"").append(StringUtils.parse(player)).append("\",\"id\":\"").append(UUID.randomUUID().toString()).append("\"}");
                    if (count == 10 && conf.getPlayers().size() > 10)
                        break;
                }
            }
        }*/
        sb.append("]},");

        // MOTD
        sb.append("\"description\":{\"text\":\"").append(this.getMotd() == null ? "A Minecraft Server" : parse(combineList(this.getMotd()))).append("\"},");

        // Icon
        sb.append("\"favicon\": \"").append(this.getBase64_icon() == null ? "" : this.getBase64_icon()).append("\",\"previewsChat\": true,\"enforcesSecureChat\": true}");

        return sb.toString();
    }
}


