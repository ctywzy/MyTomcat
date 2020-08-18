package niotomcat.server;

import niotomcat.tomcatobj.HttpServletRequest;
import niotomcat.tomcatobj.HttpServletResponse;

public interface Servlet {

    /**
     * Servlet初始化
     */
    void init();

    /**
     * Servlet业务处理
     */
    void service(HttpServletRequest request, HttpServletResponse response);
}
