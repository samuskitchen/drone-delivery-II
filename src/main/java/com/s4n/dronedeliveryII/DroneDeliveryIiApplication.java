package com.s4n.dronedeliveryII;

import com.s4n.dronedeliveryII.controller.DomicileController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DroneDeliveryIiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DroneDeliveryIiApplication.class, args);
	}

	@Bean(initMethod = "readFiles")
	public DomicileController domicileController (){
		return new DomicileController();
	}

}
