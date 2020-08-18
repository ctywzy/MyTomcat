package niotomcat.server;

import niotomcat.tomcatobj.HttpSession;

import java.util.HashMap;
import java.util.Map;

public class ServletContext {

    public static Map<String, Object> servlets = new HashMap<>();

    static Map<String, HttpSession> sessions = new HashMap<>();
}
