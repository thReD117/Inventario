package servlets;

import Domain.Clothing;
import Logic.Stock;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@WebServlet(name = "ShowClothingsServlet", urlPatterns = {"/ShowClothingsServlet"})
public class ShowClothingsServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtiene el stock y genera el arraylist para poder recorrerlo
        Stock stock = (Stock) getServletContext().getAttribute("stock");
        
        // Crea una lista de mapas para convertir los datos del stock a JSON y que JS pueda insertarlos en el html
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            // Crea toda la información
            List<Map<String, Object>> data = new ArrayList<>();
            for(Clothing clothing : stock.listFullStock()){
                HashMap<String, Object> item = new HashMap<>();
                item.put("idCompleta", clothing.getId());
                item.put("idIndividual", clothing.getIndividualId());
                item.put("idGrupal", clothing.getGroupId());
                item.put("tipo", clothing.getClass().getName().replace("Domain.", ""));
                item.put("categoria", clothing.getCategory());
                item.put("talla", clothing.getSize());
                item.put("material", clothing.getMaterial());
                item.put("color", clothing.getColor());
                item.put("descripcion", clothing.getDescription());
                item.put("precio", clothing.getPrice());
                item.put("temporada", clothing.getSeason());
                item.put("genero", clothing.getGender());
                data.add(item);
            }
            
            // Lo convierte a JSON
            String json = new Gson().toJson(data);
            
            // Manda la información a JS
            out.print(json);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
