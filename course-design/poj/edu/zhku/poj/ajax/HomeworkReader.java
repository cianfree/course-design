package edu.zhku.poj.ajax;

import edu.zhku.fr.priv.tag.Validate;
import edu.zhku.json.Json;
import edu.zhku.json.JsonParseException;
import edu.zhku.json.reader.JsonReader;
import edu.zhku.poj.domain.Homework;

public class HomeworkReader implements JsonReader {

    @Override
    public String read(Object src) throws JsonParseException {
        if(src == null || !(src instanceof Homework)) return "null";
        Homework hw = (Homework) src;
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":\"").append(hw.getId()).append("\",");
        sb.append("\"postTime\":\"").append(Json.toJson(hw.getPostTime())).append("\",");
        sb.append("\"problemName\":\"").append(hw.getProblem().getName()).append("\",");
        sb.append("\"problemDescription\":\"").append(Validate.fixLen(hw.getProblem().getDescription(), 40)).append("\",");
        sb.append("\"courseName\":\"").append(hw.getCourse().getName()).append("\",");
        sb.append("\"courseDescription\":\"").append(Validate.fixLen(hw.getCourse().getDescription(), 40)).append("\"");
        sb.append("}");
        return sb.toString();
    }

}
