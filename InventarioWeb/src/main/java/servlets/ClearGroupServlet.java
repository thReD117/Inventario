package servlets;
import Logic.DatabaseManager; // Este es el importante
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/ClearGroupServlet")
public class ClearGroupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Obtiene el groupId directamente del parámetro de la solicitud
        String groupId = request.getParameter("groupId");

        if (groupId == null || groupId.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            response.getWriter().write("{\"status\":\"error\", \"message\":\"El ID del grupo no puede estar vacío.\"}");
            return;
        }

        try {
            // Llama a tu método clearGroup con el String groupId
            DatabaseManager.clearGroup(groupId);
            response.setStatus(HttpServletResponse.SC_OK); // 200 OK
            response.getWriter().write("{\"status\":\"success\", \"message\":\"Grupo limpiado exitosamente.\"}");

        } catch (Exception e) { // Considera capturar excepciones más específicas si tu DatabaseManager las lanza
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Error interno del servidor al limpiar el grupo: " + e.getMessage() + "\"}");
            e.printStackTrace(); // Esto imprimirá el error en los logs de tu servidor (Tomcat, etc.)
        }
    }
}