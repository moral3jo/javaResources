package com.bbva.khvd.front.util;

public class AccesoServiciosException extends Exception {

  public AccesoServiciosException(WebApplicationException e) {
		super(e);
	}
	
	public AccesoServiciosException(ServerWebApplicationException e) {
		super(e);
	}
	
	public AccesoServiciosException(ClientWebApplicationException e) {
		super(e);
	}
	
	public AccesoServiciosException(BusinessServiceException e) {
		super(e);
	}
	
	public AccesoServiciosException(String string) {
		super(string);
	}
	
	private static final long serialVersionUID = 1L;
}
