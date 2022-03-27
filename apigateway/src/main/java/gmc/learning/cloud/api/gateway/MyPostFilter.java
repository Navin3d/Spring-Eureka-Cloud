package gmc.learning.cloud.api.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class MyPostFilter implements GatewayFilter, Ordered {

	final Logger logger = LoggerFactory.getLogger(MyPostFilter.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			logger.info("MyPostFilter is Executed...");
		}));
	}

	@Override
	public int getOrder() {
		return -2;
	}

}
