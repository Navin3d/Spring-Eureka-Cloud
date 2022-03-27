package gmc.learning.cloud.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bootstrap.encrypt.KeyProperties;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.config.server.encryption.SingleTextEncryptorLocator;
import org.springframework.cloud.context.encrypt.EncryptorFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.encrypt.TextEncryptor;

@SpringBootApplication
@EnableConfigServer
public class CloudsampleapiconfigserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudsampleapiconfigserverApplication.class, args);
	}
	
//	@Autowired
//	private KeyProperties key;
//	private TextEncryptor encryptor;
//
//	@Bean(name = "encryptor")
//	public TextEncryptor encryptor(){
//		this.encryptor = new EncryptorFactory(this.key.getSalt()).create(this.key.getKey());
//		return encryptor;
//	}
//
//	@Bean(name = "locator")
//	public SingleTextEncryptorLocator locator(){
//		return new SingleTextEncryptorLocator(this.encryptor);
//	}

}
