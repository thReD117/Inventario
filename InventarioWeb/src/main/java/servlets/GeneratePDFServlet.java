package servlets;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
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
import java.io.OutputStream;

    @WebServlet(name = "GeneratePDFServlet", urlPatterns = {"/GeneratePDFServlet"})
public class GeneratePDFServlet extends HttpServlet {
        
        
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Configuramos la respuesta como PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Inventario.pdf");

        // Obtenemos el stock
        Stock stock = (Stock) getServletContext().getAttribute("stock");
        List<Clothing> inventario = stock.getAllClothing();

        // Creamos el PDF
        try (OutputStream out = response.getOutputStream()) {
            // Inicializamos el documento PDF (usando iText, por ejemplo)
            com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            com.itextpdf.text.pdf.PdfWriter.getInstance(document, out);
            document.open();

            // Logo
            String logoPath = getServletContext().getRealPath("/img/logo.jpg"); // Asegúrate que la ruta sea correcta
            com.itextpdf.text.Image logo = com.itextpdf.text.Image.getInstance(logoPath);
            logo.scaleToFit(100, 100);
            document.add(logo);

            // Título
            document.add(new com.itextpdf.text.Paragraph("Inventario de Prendas"));
            document.add(new com.itextpdf.text.Paragraph(" ")); // Espacio

            // Tabla
            com.itextpdf.text.pdf.PdfPTable table = new com.itextpdf.text.pdf.PdfPTable(5); // Por ejemplo, 5 columnas
            table.addCell("ID");
            table.addCell("Nombre");
            table.addCell("Color");
            table.addCell("Talla");
            table.addCell("Temporada");

            // Llenar la tabla
            for (Clothing c : inventario) {
                table.addCell(c.getId());
                table.addCell(c.getDescription());
                table.addCell(c.getColor());
                table.addCell(c.getSize());
                table.addCell(c.getSeason().toString());
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet que genera el PDF con el inventario actual";
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
}
