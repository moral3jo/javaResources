@MultipartConfig(maxFileSize = 10*1024*1024,maxRequestSize = 20*1024*1024,fileSizeThreshold = 5*1024*1024)
public class InicioServlet extends HttpServlet implements Servlet {
  
  //listado de conexiones para informar de que algo ocurre
  private List<AsyncContext> contexts = new LinkedList();
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    final String strOperativa = (request.getParameter(Constantes.strOperativa)!=null ? request.getParameter(Constantes.strOperativa) : Constantes.strCadenaVacia);
    if ("INICIO".equals(strOperativa) ) {
      //abrir primera pagina. Aqui puede ir el formulario que desencadenará todo
      strPagina = "formulario.jsp";
    }else if ("CARGAFORMULARIO".equals(strOperativa) ) {
      if (ServletFileUpload.isMultipartContent(request)) {
        //aqui cargamos el fichero por ejemplo y lo guardamos en un directorio para que el hilo lo pueda procesar
        final Part filePart = request.getPart("file");
        String code = TratadorDeFicheros.guardarFichero(filePart);
        //el code será un codigo unico que podremos devolver a la jsp para localizar un solo fichero. Aqui no está en uso
        Thread second = new Thread(new MyThread(request, contexts));
        second.start();
        strPagina = "procesando.jsp";
      }
    }else if("ESTADO_PROCESADO".equals(strOperativa)){
      //esta conexión la agrega al listado de conexiones a informar cuando pase algo
      final AsyncContext asyncContext = request.startAsync(request, response);
      asyncContext.setTimeout(0);
      contexts.add(asyncContext);
      strPagina = Constantes.room_subido;
      return;
    }
    
    redireccionar(strPagina);
  }
}
