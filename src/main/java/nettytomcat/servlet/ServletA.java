package nettytomcat.servlet;

import nettytomcat.custome.CustomServlet;
import nettytomcat.custome.CustomeRequest;
import nettytomcat.custome.CustomeResponse;

public class ServletA extends CustomServlet {


    @Override
    protected void doGet(CustomeRequest request, CustomeResponse response) throws Exception {
        response.write("This is ServletA");
    }

    @Override
    protected void doPost(CustomeRequest request, CustomeResponse response) throws Exception {
        doGet(request,response);
    }
}
