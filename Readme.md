# Gestion de empleados

El sistema establece una estructura organizacional: en el nivel superior se encuentran los departamentos, los cuales agrupan a las empresas que operan bajo contrato con la plataforma; y en el nivel más específico, cada empresa tiene asociados a sus empleados, quienes pueden ser gestionados tanto por el administrador del sistema como por la propia empresa cliente.
## Entidades

### Entidad Departamento
- id : Long(PK, auto-generado)
- nombre : String (requerido)
- estado : String (requerido) * 
- fecha : Date (requerido, predeterminado = Actualizacion de registro )

### Entidad Empresa
- id : Long(PK, auto-generado)
- nombre : String (unico, requerido)
- sector : String (requerido)
- numEmpleados : int (requerido, predeterminado  = 0)
- pais : String (requerido)
- fecha : Date (requerido, predeterminado = Actualizacion de registro )

### Empleado
- id : Long(PK, auto-generado)
- nombre : String (requerido)
- cargo : String (requerido)
- email : String (unico, requerido)
- telefono : String (requerido)
- empresa : int(FK , requerido)
- departamento : int(FK, requerido)
- estado : String (requerido) * 

## DTOs Requeridos

### DepCreateDTO

```
{
    "nombre" : "Electronicos",
    "estado" : "Activo",
}
```
- El id de genera automaticamente.
- La fecha se genera automaticamente (La fecha es de la ultima modificacion).  
### DepResponseDTO

```
{
    "id" : 1,
    "nombre" : "Electronicos",
    "estado" : "Activo",
    "fecha"  : "2026-06-24"
}
```

### EmpresaCreateDTO

```
{
    "nombre" : "Solari SA",
    "sector" : "Activo",
    "pais"  :   "Mexico",
    "departamentos" : [1]
}
```
- El id se genera automaticamente.
- La fecha se genera automaticamente (La fecha es de la ultima modificacion).
- El numero de empleados se pone como 0. 
- En el formulario se mostraran los nombres pero al seleccionar se añaden como el id de los departamentos. 

### EmpresaResponseDTO
```
{
    "id"    : 1,
    "nombre" : "Solari SA",
    "sector" : "Activo",
    "numEmpleados"  : 54,
    "pais"  :   "Mexico",
    "fecha" : "2026-06-24"
}
```
### EmpleadoCreateDTO

```
{
    "nombre" : "Lucia Fernandez",
    "cargo" : "Dir. Inovacion",
    "Departamento"  : "Electronicos",  
    "Empresa"  :   "Solari SA",
    "estado"   :   "activo"
}
```
- El id de genera automaticamente.
- La fecha se genera automaticamente (La fecha es de la ultima modificacion).

### EmpleadoResponseDTO

```
{
    "id"    : 1,
    "nombre" : "Lucia Fernandez",
    "cargo" : "Dir. Inovacion",
    "departamento"  : "Electronicos",  
    "empresa"  :   "Solari SA",
    "estado"   :   "activo"
}
```
### InformacionResponseDTO
```
{
    "numDepartamentos"    : 8,
    "numEmpresas"    : 5,
    "empleados"    : 7
}
```


## EndPoints requeridos
### Departamentos:
1. Crear departamento

POST /api/departamentos
Body: DepCreateDTO
Response: DepResponseDTO (201 Created)

2. Obtener todos los departamentos

GET /api/departamentos
Response: List<DepResponseDTO> (200 OK)

3. Actualizar departamento


4. Borrar departamento

DELETE /api/departamentos/{id}
Response: 204 
Error: 404 si no existe

5. Contar num. de departamentos/empresas y empleados.

GET /api/numInformacion
Response: InformacionResponseDTO (200 OK)

6. Agregar empresa a departamento
POST /api/departamentos/agregarEmpresa
Body: idEmpresa
Response:  200 
 

### Empresa:

1. Crear empresa 

POST /api/empresas
Body: EmpresaCreateDTO
Response: EmpresaResponseDTO (201 )

2. Obtener todas las empresas

GET /api/empresas
Response: List<EmpresaResponseDTO> (201 )

3. Actualizar empresa

4. Eliminar empresa
DELETE /api/empresas/{id}
Response: 204 
Error: 404 si no existe

### Empleados:

1. Crear empleado
POST /api/empleados
Body: EmpleadoCreateDTO
Response: EmpleadoResponseDTO (201 )

2. Obtener todos los empleados

GET /api/empleados
Response: List<EmpleadoResponseDTO> (201 )

3. Actualizar Empleado

4. Eliminar empleado
DELETE /api/empleados/{id}
Response: 204 
Error: 404 si no existe

## Arquitectura del proyecto


|--main.package
|------controller
|----------|HelloController.java
|-------dtos
|----------|DepCreateDTO.java
|----------|DepResponseDTO.java
|----------|EmpresaCreateDTO.java
|----------|EmpresaResponseDTO.java
|----------|EmpleadoCreateDTO.java
|----------|EmpleadoResponseDTO.java
|-------servicios
|----------|ConfigureServices.java
|-------repositorio
|----------|DepartamentoRepository.java
|----------|EmpresaRepository.java
|----------|EmpleadoRepository.java
|-------entidades
|----------|Departamento.java
|----------|Empresa.java
|----------|Empleado.java
|-------map
|----------|Mapeo.java
|--Application.java