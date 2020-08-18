package niotomcat.tomcatobj;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {

    private Map<String, Object> sessionMap = new HashMap<>();

    private String id;

    HttpSession(String id){
        this.id = id;
    }


    public void setAttribute(String key, Object value) {
        sessionMap.put(key, value);
    }

    public Object getAttribute(String key){
        return sessionMap.get(key);
    }

    public void removeAttribute(String key){
        sessionMap.remove(key);
    }
}

