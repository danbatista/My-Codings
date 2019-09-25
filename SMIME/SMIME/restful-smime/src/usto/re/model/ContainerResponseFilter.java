package usto.re.model;


import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;

public class ContainerResponseFilter implements com.sun.jersey.spi.container.ContainerResponseFilter{

	@Override
	public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {
		ResponseBuilder responseBuilder = Response.fromResponse(response.getResponse());
 System.out.println("Filtering on...");
	responseBuilder.header("Access-Control-Allow-Origin", "*");
	responseBuilder.header("Access-Control-Allow-Methods", 
                "POST, GET, OPTIONS");
        responseBuilder.header("Access-Control-Max-Age",
                "86400");
    responseBuilder.header("Access-Control-Allow-Headers", "Foo-Header,Origin, X-Request-Width, Content-Type, Accept");

	response.setResponse(responseBuilder.build());

	return response;
	}


}
