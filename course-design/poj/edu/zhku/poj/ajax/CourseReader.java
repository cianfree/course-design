package edu.zhku.poj.ajax;

import edu.zhku.json.JsonParseException;
import edu.zhku.json.reader.JsonReader;
import edu.zhku.poj.domain.Course;

public class CourseReader implements JsonReader {

    @Override
    public String read(Object src) throws JsonParseException {
        if(src instanceof Course) {
            Course course = (Course) src;
            StringBuilder sb = new StringBuilder("{");
            sb.append("\"id\":\"").append(course.getId()).append("\",")
                .append("\"name\":\"").append(course.getName()).append("\",")
                .append("\"owner\":\"").append(course.getOwner().getName()).append("\"")
                .append("}");
            return sb.toString();
        }
        return "null";
    }

}
