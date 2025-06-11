package servlets;

import Logic.Stock;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "DeleteClothingByIdServlet", urlPatterns = {"/DeleteClothingByIdServlet"})
public class DeleteClothingByIdServlet extends HttpServlet {

    /**
     * Procesa la petición (tanto GET como POST).
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtenemos el parámetro "id" de la petición
        String id = request.getParameter("id");

        // Obtenemos el stock desde el contexto
        Stock stock = (Stock) getServletContext().getAttribute("stock");

        // Intentamos eliminar la prenda
        boolean success = stock.removeById(id);

        // Preparamos la respuesta
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if (success) {
                out.println("Prenda eliminada con éxito.");
            } else {
                out.println("No se encontró la prenda con ID: " + id);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
protected void doDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String queryString = request.getQueryString(); // obtiene "id=XXX"
    String id = null;

    if (queryString != null && queryString.startsWith("id=")) {
        id = queryString.substring(3); // ejemplo simple para obtener el valor de id
    }

    // El resto igual
    Stock stock = (Stock) getServletContext().getAttribute("stock");
    boolean success = stock.removeById(id);

    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {
        if (success) {
            out.println("Prenda eliminada con éxito.");
        } else {
            out.println("No se encontró la prenda con ID: " + id);
        }
    }
}



    @Override
    public String getServletInfo() {
        return "Servlet que elimina una prenda por su ID";
    }
}

