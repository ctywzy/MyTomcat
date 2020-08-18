package niotomcat.tomcatobj;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {

    private Map<String, Object> sessionMap = new HashMap<>();

    private String id;

    HttpSession(String id){
        this.id = id;
    }


}

