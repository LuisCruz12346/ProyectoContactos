package com.example.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

@RestController
public class HelloController {

    @Autowired
    private ConfigureServices configureServices;
// Sera post, para pruebas
    @GetMapping("/departamentos/agregar/{nombre}")
    public Departamento agregarDep(@PathVariable("nombre") String nombre) {
        Departamento dep = Departamento.builder()
                .nombre(nombre)
                .estado("Activo")
                .fecha(LocalDate.now())
                .build();

        return configureServices.saveDepartment(dep);
    }

    @GetMapping("/departamentos")
    public List<Departamento> obtenerDeps() {
        return configureServices.findAllDepartment();
    }
// Sera Delete
    @GetMapping("/departamentos/eliminar/{id}")
    public List<Departamento> borrarDep(@PathVariable("id") Long id) {
        configureServices.deleteDepartment(id);
        return configureServices.findAllDepartment();
    }

}