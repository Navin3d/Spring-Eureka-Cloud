package gmc.learning.cloud.api.gateway;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class GlobalFilterConfiguration {

	@Bean
	@Order(1)
	public GlobalFilter secondPreFilter() {
		return (exchange, chain) -> {
			log.info("My second global pre-filter...");
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				log.info("My Fourth global Post-Filter");
			}));
		};
	}

	@Bean
	@Order(2)
	public GlobalFilter thirdPreFilter() {
		return (exchange, chain) -> {
			log.info("My Third global Pre-filter...");
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				log.info("My Third global Post-Filter");
			}));
		};
	}

	@Bean
	@Order(3)
	public GlobalFilter fourPreFilter() {
		return (exchange, chain) -> {
			log.info("My Fourth Pre-Filter...");
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				log.info("My Second global Post-Filter");
			}));
		};
	}

}
