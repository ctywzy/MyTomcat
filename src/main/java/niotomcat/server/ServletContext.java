package niotomcat.server;

import niotomcat.tomcatobj.HttpSession;

import java.util.HashMap;
import java.util.Map;

public class ServletContext {

    /**
     * 存储servlet信息
     */
    public static Map<String, Object> servlets = new HashMap<>();

    /**
     * 存储session信息
     */
    public static Map<String, HttpSession> sessions = new HashMap<>();
}
