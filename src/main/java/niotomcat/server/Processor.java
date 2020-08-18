package niotomcat.server;

import niotomcat.tomcatobj.HttpServletRequest;
import niotomcat.tomcatobj.HttpServletResponse;

public interface Processor {

    /**
     * 处理请求，给出响应
     * @param httpServletRequest
     * @param httpServletResponse
     */
    void process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

}
