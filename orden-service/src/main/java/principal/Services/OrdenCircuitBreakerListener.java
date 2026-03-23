package principal.Services;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class OrdenCircuitBreakerListener {
    private final CircuitBreakerRegistry circuitBreakerRegistry;
    private final OrdenRecoveryService ordenRecoveryService;
    public OrdenCircuitBreakerListener(CircuitBreakerRegistry cbr, OrdenRecoveryService ordenRecoveryService) {
        this.circuitBreakerRegistry = cbr;
        this.ordenRecoveryService = ordenRecoveryService;
    }
    @PostConstruct
    public void registrandoCircuitBreakerEventListener(){
        //CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("productoServiceCB");
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.getAllCircuitBreakers()
                .stream()
                .filter(cb -> cb.getName().equals("productoServiceCB"))
                .findFirst()
                .orElse(null);
        if (circuitBreaker != null)
            circuitBreaker.getEventPublisher().onStateTransition(this::handleStateTransition);
        else
            System.err.println("No se encontro el registro de circuitbreaker");
        System.out.println("Registrando CircuitBreaker Eventos");
    }
    private void handleStateTransition(CircuitBreakerOnStateTransitionEvent event) {
        System.out.println("Estado del Circuit breaker productoServiceCB: "+ event.getStateTransition());
        if(event.getStateTransition().getToState() == CircuitBreaker.State.CLOSED){
            System.out.println("Cerrando Circuit Breaker productoServiceCB. Llamando a ejecutar la recuperación de ordenes temporales....");
            ordenRecoveryService.recuperOrdenesPendientes();
        }
    }
}
