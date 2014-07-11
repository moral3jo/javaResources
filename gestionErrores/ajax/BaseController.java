package com.proyecto.front.controller;

public class BaseController{
		PROTECTED
  

	protected void returnAjaxError(AccesoServiciosException exception, HttpServletResponse response) {
		String detalleMensaje = obtieneError(exception);
		devolverErrorWeb(detalleMensaje);
	}
    
	private String obtieneError(AccesoServiciosException exception){
		//Podemos validar tipo de excepcion original
		if( ex.getCause() instanceof ServerWebApplicationException ){
		  ServerWebApplicationException swaEx = (ServerWebApplicationException)ex.getCause();
		  detalleMensaje = getServerWebApplicationExceptionError( swaEx );
		}else{
		  return textoGenericoError();
		}
	}
	
	private devolverErrorWeb(String detalleMensaje){
	  //response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		try {
			response.getWriter().write(detalleMensaje);
		} catch (IOException e) {
			LOGGER.info(Arrays.toString(e.getStackTrace()) );
		}
	}
	
}
