package com.proyecto.front.controller;

@RequestMapping("/Modulo/**")
@Controller
public class Modulo extends BaseController{
  
  /*
   * Lanzamos la exception pero no llegará a la web. Es capturada desde BaseController
   */
  @RequestMapping(value = "/llamadaAjax")
  public ModelAndView showForm(HttpServletRequest request, ModelMap model) throws AccesoServiciosException {
    return servidor.llamada();
  }
  
  
  @ExceptionHandler(AccesoServiciosException.class)
	@ResponseBody
	public void handleAjaxAccesoServiciosException(AccesoServiciosException ex, HttpServletResponse response){		
		returnAjaxError(ex, response);

	}

}
