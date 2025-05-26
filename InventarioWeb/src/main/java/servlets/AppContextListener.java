package servlets;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import Logic.Stock;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Inicializa el stock que usarán todos los servlets
        Stock stock = new Stock();
        stock.loadFromDB(); 

        // Lo pone en el servletContext para poder compartirlo con cualquier servlet
        sce.getServletContext().setAttribute("stock", stock);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Cleanup if needed
    }
}
