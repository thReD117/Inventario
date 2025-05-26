package servlets;

import Domain.Clothing;
import Logic.Stock;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ShowClothingsServlet", urlPatterns = {"/ShowClothingsServlet"})
public class ShowClothingsServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtiene el stock y genera el arraylist para poder recorrerlo
        Stock stock = (Stock) getServletContext().getAttribute("stock");
        
        // Genera las filas del html que le serán pasadas a JS para escribirlas
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String html = "";
            for(Clothing clothing : stock.listFullStock()){ 
                html += "<tr>"+String.format("""
                                            <td> %1$s </td>
                                            <td> %2$s </td>
                                            <td> %3$s </td>
                                            <td> %4$s </td>
                                            <td> %5$s </td>
                                            <td> %6$s </td>
                                            <td> %7$s </td>
                                            """,
                                        clothing.getId(),
                                        clothing.getCategory().getDisplayName(),
                                        clothing.getGender(),
                                        clothing.getColor(),
                                        clothing.getSize(),
                                        clothing.getSeason(),
                                        clothing.getQuantity()==0 ? "No stock" : "In stock"
                                        ) + "</tr>";
            }
            out.print(html);
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
