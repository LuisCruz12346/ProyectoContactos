package com.example.springboot;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConfigureServices {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;
    
    public Departamento saveDepartment(Departamento department) {
        return departamentoRepository.save(department);
    }

    public List<Departamento> findAllDepartment() {
        return departamentoRepository.findAll();
    }

    @Transactional
    public void deleteDepartment(Long id) {
        if (departamentoRepository.existsById(id)) {
            departamentoRepository.deleteById(id);
        } 
    }
    // Agregar empresa a departamento
}