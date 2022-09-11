package com.palg.tp;

import com.palg.tp.DAO.ObjectDetail;
import com.palg.tp.DAO.ObjectDetailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TpApplication {

	private static final Logger log = LoggerFactory.getLogger(TpApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TpApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(ObjectDetailRepository repository) {
		return (args) -> {
			// save a few customers
			repository.save(new ObjectDetail(1L, "a"));
			repository.save(new ObjectDetail(2L, "b"));
			repository.save(new ObjectDetail(3L, "c"));

			// fetch all customers
			log.info("ObjectDetail found with findAll():");
			log.info("-------------------------------");
			for (ObjectDetail objectDetail : repository.findAll()) {
				log.info(objectDetail.toString());
			}
			log.info("");

			// fetch an individual customer by ID
			ObjectDetail objectDetail = repository.findById(1L).get();
			log.info("Object detail with key 1L:");
			log.info("--------------------------------");
			log.info(objectDetail.toString());
			log.info("");

			// fetch customers by last name
			log.info("Delete ObjectDetail with key 1L:");
			log.info("--------------------------------------------");
			repository.deleteById(1L);
			log.info("ObjectDetail found with findAll():");
			log.info("-------------------------------");
			for (ObjectDetail ob : repository.findAll()) {
				log.info(ob.toString());
			}
			log.info("");
		};
	}
}
