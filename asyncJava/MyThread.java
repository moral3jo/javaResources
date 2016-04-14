public class MyThread implements Runnable {
  HttpServletRequest request;
  List<AsyncContext> contexts;
  public MyThread(HttpServletRequest parameter, List<AsyncContext> contexts) {
    //por aqui también tendria que venir el fichero a tratar, la ruta o algo asi.
    this.request = parameter;
    this.contexts=contexts;
  }
	@Override
  public void run() {
    //INICIO DE PROCESADO DE FICHERO O ALGO ASI
    //informamos a la web de que estamos en ello
		AsyncWebService.actualizar(contexts, request, "init fichero");
		try {
			Thread.sleep(15000);//aqui va un procesamiento de algo chungo
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//hemos terminado informamos a la web
		AsyncWebService.actualizar(contexts, request, "fin fichero");
	}
}
