package edu.zhku.poj.ajax;

import edu.zhku.fr.priv.tag.Validate;
import edu.zhku.json.JsonParseException;
import edu.zhku.json.reader.JsonReader;
import edu.zhku.poj.domain.Problem;

public class ProblemReader implements JsonReader {

    @Override
    public String read(Object src) throws JsonParseException {
        if(src == null || !(src instanceof Problem)) return "null";
        Problem pro = (Problem) src;
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":\"").append(pro.getId()).append("\",");
        sb.append("\"name\":\"").append(pro.getName()).append("\",");
        sb.append("\"description\":\"").append(Validate.fixLen(pro.getDescription(), 30)).append("\"");
        sb.append("}");
        return sb.toString();
    }

}
