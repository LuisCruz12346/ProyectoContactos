@Configuration
public class RestClientConfig {

    // aplication.propirties
    //spring.http.client.connect-timeout=5s
    //spring.http.client.read-timeout=10s
    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:8081")
                .build();
    }
}

@Data
@Builder
public class NotificacionDto {
    private final String destino;
    private final Long resultado;
}


@Slf4j
@Service
@RequiredArgsConstructor
public class NotificacionClient {

    private static final int MAX_INTENTOS = 3;

    private final RestClient restClient;

    @Async
    public void enviar(String destino, Long resultado) {
        int intentos = 0;
        while (true) {
            try {
                restClient.post()
                        .uri("/notificaciones")
                        .body(NotificacionDto.builder().destino(destino).resultado(resultado).build())
                        .retrieve()
                        .toBodilessEntity();
                return;

            } catch (HttpClientErrorException e) {
                log.error("Rechazada {}: {}", destino, e.getStatusCode(), e);
                return;

            } catch (ResourceAccessException | HttpServerErrorException e) {
                if (++intentos >= MAX_INTENTOS) {
                    log.error("Fallo en envio a {}", destino, e);
                    return;
                }
                try {
                    Thread.sleep(1000L * intentos);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }
}



