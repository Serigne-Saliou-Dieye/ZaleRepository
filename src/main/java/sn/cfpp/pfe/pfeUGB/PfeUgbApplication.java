package sn.cfpp.pfe.pfeUGB;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sn.cfpp.pfe.pfeUGB.model.Client;
import sn.cfpp.pfe.pfeUGB.repositories.ClientRepository;

@SpringBootApplication
public class PfeUgbApplication implements CommandLineRunner{

	@Autowired
	private ClientRepository clientRepository;

	public static void main(String[] args) {
		SpringApplication.run(PfeUgbApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{

		Client cl1 = new Client(null, "Demba", "44334433", "demba12"," demba@gmail.com" );
		Client cl2 = new Client(null, "Samba", "44884433", "samba14"," samba@gmail.com" );
		clientRepository.saveAll(Arrays.asList(cl1, cl2));
	}

}
	