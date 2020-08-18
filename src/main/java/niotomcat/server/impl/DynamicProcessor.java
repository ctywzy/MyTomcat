package niotomcat.server.impl;

import niotomcat.server.Processor;
import niotomcat.server.ServletContext;
import niotomcat.tomcatobj.HttpServletRequest;
import niotomcat.tomcatobj.HttpServletResponse;

public class DynamicProcessor implements Processor {

    @Override
    public void process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        //1、假设请求的地址为  /hello.action  ->对应的是hello 这个servlet 类
        //从request中取出getRequestURI
        String url = httpServletRequest.getUri();

        //2、去掉 / 及action 两个部分 ，得到hello  ，就是servlet类名
        String servletName = url.substring(1, url.indexOf("."));

        //单例形式存储在Map中，每次先判断是否存在

        HttpServlet httpServlet = null;
        if(!ServletContext.servlets.containsKey(servletName)){
            try {
                Class clasz = Class.forName(servletName);
                httpServlet = (HttpServlet) clasz.newInstance();

                httpServlet.init();

                ServletContext.servlets.put(servletName, httpServlet);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            httpServlet = (HttpServlet) ServletContext.servlets.get(servletName);
        }

        httpServlet.service(httpServletRequest, httpServletResponse);


    }
}
