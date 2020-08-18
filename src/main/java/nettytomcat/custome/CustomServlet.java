package nettytomcat.custome;

public abstract class CustomServlet {
    private static final String GET_METHDO = "GET";

    public void service(CustomeRequest request, CustomeResponse response) throws Exception{

        if(GET_METHDO.equals(request.getMethod())){
            doGet(request, response);
        }else{
            doPost(request, response);
        }
    }

    protected abstract void doGet(CustomeRequest request, CustomeResponse response) throws Exception;

    protected abstract void doPost(CustomeRequest request, CustomeResponse response) throws Exception;

}
