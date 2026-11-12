@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToMany
    @JoinTable(
        name = "rol_autorizacion",
        joinColumns = @JoinColumn(name = "rol_id"),
        inverseJoinColumns = @JoinColumn(name = "autorizacion_id")
    )
    @Builder.Default
    private List<Autorizacion> autorizaciones = new ArrayList<>();
}

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Autorizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToMany(mappedBy = "autorizaciones")
    @Builder.Default
    private List<Rol> roles = new ArrayList<>();
}


public interface RolRepositorio extends JpaRepository<Rol, Long> {

    @Query("SELECT a.nombre FROM Rol r JOIN r.autorizaciones a WHERE r.nombre = :nombreRol")
    List<String> regresarAutorizaciones(@Param("nombreRol") String nombreRol);
}

@Service
@RequiredArgsConstructor
public class AutorizacionServicio {

    private final RolRepositorio rolRepositorio;

    public List<String> regresarAutorizacionesDeRol(String rol) {
        return rolRepositorio.regresarAutorizaciones(rol);
    }
}
