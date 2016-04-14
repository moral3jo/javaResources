public class AsyncWebService{
  public static void actualizar(List<AsyncContext> contexts, HttpServletRequest request, String message){
    //actualizar estado en la web
    List<AsyncContext> asyncContexts = new ArrayList(contexts);
    contexts.clear();
    System.out.println("Estamos informando a :"+asyncContexts.size()+" paginas a la vez.");
    String htmlMessage = "<p><b>INFO</b><br/>" + message + "</p>";
    ServletContext sc = request.getServletContext();
    //Con esto lo que hacemos es que si se abre otra ventana se vea todo el historial y no solo lo nuevo
    if (sc.getAttribute("messages") == null) {
      sc.setAttribute("messages", htmlMessage);
    } else {
      String currentMessages = (String) sc.getAttribute("messages");
      sc.setAttribute("messages", htmlMessage + currentMessages);
    }
    for (AsyncContext asyncContext : asyncContexts) {
      try  {
        PrintWriter writer = asyncContext.getResponse().getWriter();
        writer.println(htmlMessage);
        writer.flush();
        asyncContext.complete();
      } catch (Exception ex) {}
    }
  }
}
