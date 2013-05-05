package edu.zhku.poj.ajax;

import edu.zhku.fr.domain.User;
import edu.zhku.json.JsonParseException;
import edu.zhku.json.reader.JsonReader;

public class UserReader implements JsonReader {

    @Override
    public String read(Object src) throws JsonParseException {
        if(src == null || !(src instanceof User)) return "null";
        User user = (User) src;
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":\"").append(user.getId()).append("\",");
        sb.append("\"account\":\"").append(user.getAccount()).append("\",");
        sb.append("\"name\":\"").append(user.getName()).append("\",");
        sb.append("\"sex\":\"").append(user.getSex()).append("\"");
        sb.append("}");
        return sb.toString();
    }

}
