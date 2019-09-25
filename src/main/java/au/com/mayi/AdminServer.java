package au.com.mayi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@SpringBootApplication
@EnableAdminServer
public class AdminServer {   
   public static void main(String[] args) {
      SpringApplication.run(AdminServer.class, args);
   }
}