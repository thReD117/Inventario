package servlets;

import Domain.*;
import Enums.*;
import Logic.Stock;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "AddClothingServlet", urlPatterns = {"/AddClothingServlet"})
public class AddClothingServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Clothing clothing = null;
        
        String CATEGORY = request.getParameter("CATEGORY").toUpperCase();
        Float PRICE = Float.valueOf(request.getParameter("PRICE"));
        int QUANTITY = Integer.parseInt(request.getParameter("QUANTITY"));
        String SIZE = request.getParameter("SIZE");
        String MATERIAL = request.getParameter("MATERIAL");
        String COLOR = request.getParameter("COLOR");
        String DESCRIPTION = request.getParameter("DESCRIPTION");
        Season SEASON = Season.valueOf(request.getParameter("SEASON").toUpperCase());
        Gender GENDER = Gender.valueOf(request.getParameter("GENDER").toUpperCase());
        String CATEGORY_ENUM = request.getParameter("CATEGORY_ENUM").toUpperCase();
        switch (CATEGORY) {
            case "LOWER" -> {
                CategoryLow category = CategoryLow.valueOf(CATEGORY_ENUM);
                clothing = new Lower(SIZE, MATERIAL, COLOR, DESCRIPTION, PRICE, QUANTITY, SEASON, GENDER, category);
            }
            case "SUPERIOR" -> {
                CategorySup category = CategorySup.valueOf(CATEGORY_ENUM);
                clothing = new Superior(SIZE, MATERIAL, COLOR, DESCRIPTION, PRICE, QUANTITY, SEASON, GENDER, category);
            }
            case "INTERIOR" -> {
                CategoryInte category = CategoryInte.valueOf(CATEGORY_ENUM);
                clothing = new Interior(SIZE, MATERIAL, COLOR, DESCRIPTION, PRICE, QUANTITY, SEASON, GENDER, category);
            }
            case "ACCESSORIES" -> {
                CategoryAcc category = CategoryAcc.valueOf(CATEGORY_ENUM);
                clothing = new Accessories(SIZE, MATERIAL, COLOR, DESCRIPTION, PRICE, QUANTITY, SEASON, GENDER, category);
            }
        }
        
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if(clothing == null){
                out.print("No se añadió imbécil");
                return;
            }

            ((Stock) getServletContext().getAttribute("stock")).add(clothing);

            out.print("La prenda fue añadida");
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
