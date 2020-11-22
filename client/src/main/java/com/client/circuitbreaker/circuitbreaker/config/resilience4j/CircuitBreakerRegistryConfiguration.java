package com.client.circuitbreaker.circuitbreaker.config.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.common.CompositeCustomizer;
import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer;
import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigurationProperties.InstanceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class CircuitBreakerRegistryConfiguration {

    private static final String CIRCUIT_BREAKER_DEFAULT = "default";

    @Bean
    public CircuitBreakerRegistry obtenerCircuitBreakerRegistry(CircuitBreakerProperties circuitBreakerProperties) {
        // Se inicializa el CircuitBreakerRegistry a partir de configuración "default" obtenida en las propiedades.
        // De no estar crea el objeto con los valores por defecto.
        CircuitBreakerRegistry circuitBreakerRegistry = circuitBreakerProperties
                .findCircuitBreakerProperties(CIRCUIT_BREAKER_DEFAULT)
                .map(instanceProperties ->
                        CircuitBreakerRegistry.of(this.buildCircuitBreakerConfig(CIRCUIT_BREAKER_DEFAULT, instanceProperties, circuitBreakerProperties))
                )
                .orElseGet(CircuitBreakerRegistry::ofDefaults);

        Map<String, InstanceProperties> configsMap = circuitBreakerProperties
                .getConfigs()
                .entrySet()
                .stream()
                .filter(entry -> !entry.getKey().equals(CIRCUIT_BREAKER_DEFAULT))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Map<String, InstanceProperties> instancesMap = circuitBreakerProperties.getInstances();

        this.saveInstancePropertiesOnRegistry(configsMap, circuitBreakerProperties, circuitBreakerRegistry);
        this.saveInstancePropertiesOnRegistry(instancesMap, circuitBreakerProperties, circuitBreakerRegistry);

        return circuitBreakerRegistry;
    }

    /**
     * Registra en el objeto {@link CircuitBreakerRegistry} las configuraciones {@link CircuitBreakerConfig}.
     *
     * @param instancePropertiesMap
     * @param circuitBreakerProperties
     * @param circuitBreakerRegistry
     */
    private void saveInstancePropertiesOnRegistry(Map<String, InstanceProperties> instancePropertiesMap, CircuitBreakerProperties circuitBreakerProperties, CircuitBreakerRegistry circuitBreakerRegistry) {
        for (Map.Entry<String, InstanceProperties> entry : instancePropertiesMap.entrySet()) {
            CircuitBreakerConfig circuitBreakerConfig = this.buildCircuitBreakerConfig(entry.getKey(), entry.getValue(), circuitBreakerProperties);
            circuitBreakerRegistry.addConfiguration(entry.getKey(), circuitBreakerConfig);
        }
    }

    /**
     * Construye el objeto {@link CircuitBreakerConfig}.
     *
     * @param name
     * @param instanceProperties
     * @param circuitBreakerProperties
     * @return
     */
    private CircuitBreakerConfig buildCircuitBreakerConfig(String name, InstanceProperties instanceProperties, CircuitBreakerProperties circuitBreakerProperties) {

        CircuitBreakerConfigCustomizer circuitBreakerConfigCustomizer = CircuitBreakerConfigCustomizer.of(name, builder -> {
        });
        List<CircuitBreakerConfigCustomizer> circuitBreakerConfigCustomizerList = Collections.singletonList(circuitBreakerConfigCustomizer);
        CompositeCustomizer<CircuitBreakerConfigCustomizer> compositeCustomizer = new CompositeCustomizer<>(circuitBreakerConfigCustomizerList);

        return circuitBreakerProperties.createCircuitBreakerConfig(name, instanceProperties, compositeCustomizer);
    }
}
