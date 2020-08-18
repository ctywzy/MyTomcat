package niotomcat.server.impl;

import niotomcat.server.Servlet;
import niotomcat.tomcatobj.HttpServletRequest;
import niotomcat.tomcatobj.HttpServletResponse;

public abstract class HttpServlet implements Servlet {
    @Override
    public void init() {

    }

    /**
     * 根据request中的method来区分该请求哪个方法
     * @param request 请求
     * @param response 响应
     */
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {
        String method = request.getMethod();
        if("GET".equals(method)){
            doGet(request, response);
        }else if("POST".equals(method)){
            doPost(request, response);
        }
        //如有需要，还可以添加PUT，DELETE等方法
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response){}

    protected void doGet(HttpServletRequest request, HttpServletResponse response){};
}
