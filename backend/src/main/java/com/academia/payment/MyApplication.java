package com.academia.payment;

import com.academia.payment.util.CORSFilter;
import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
// Note that the class extends ResourceConfig and not Application because we want to make use of register()
public class MyApplication extends ResourceConfig {
    public MyApplication() {
        // Registering the CORSFilter class with the Jersey ResourceConfig
        register(CORSFilter.class);

        // Telling Jersey the CLASSPATH where the specified classes (in our case, CORSFilter) can be found
        packages("com.academia.payment");
    }
}