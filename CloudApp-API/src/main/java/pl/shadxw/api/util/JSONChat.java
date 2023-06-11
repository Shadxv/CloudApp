package pl.shadxw.api.util;

import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONChat {

    private final JSONArray source;

    public JSONChat() {
        this.source = new JSONArray().put("");
    }

    public JSONElement append(String msg){
        return new JSONElement(this, msg);
    }

    public String build() {
        return source.toString();
    }

    public class JSONElement {

        private final JSONChat parent;

        private final JSONObject jsonObject;

        @SneakyThrows
        public JSONElement(JSONChat parent, String msg){
            this.parent = parent;
            this.jsonObject = new JSONObject().put("text", msg);
        }

        @SneakyThrows
        public JSONElement setColor(String hex){
            this.jsonObject.put("color", hex);
            return this;
        }

        @SneakyThrows
        public JSONElement setBold(boolean bold){
            this.jsonObject.put("bold", bold);
            return this;
        }

        @SneakyThrows
        public JSONElement setItalic(boolean italic){
            this.jsonObject.put("italic", italic);
            return this;
        }

        @SneakyThrows
        public JSONElement setUnderlined(boolean underlined){
            this.jsonObject.put("underlined", underlined);
            return this;
        }

        @SneakyThrows
        public JSONElement setStrikethrough(boolean strikethrough){
            this.jsonObject.put("strikethrough", strikethrough);
            return this;
        }

        @SneakyThrows
        public JSONElement setObfuscated(boolean obfuscated){
            this.jsonObject.put("obfuscated", obfuscated);
            return this;
        }

        @SneakyThrows
        public JSONElement setFont(String font){
            this.jsonObject.put("font", "minecraft:" + font);
            return this;
        }

        @SneakyThrows
        public JSONElement setInsertion(String insertion){
            this.jsonObject.put("insertion", insertion);
            return this;
        }

        public JSONChat end(){
            parent.source.put(jsonObject);
            return parent;
        }

    }

}
