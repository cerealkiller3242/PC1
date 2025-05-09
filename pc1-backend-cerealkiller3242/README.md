[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/F06i2pN7)
# 📝 **Práctica Calificada 3 – Desarrollo Basado en Plataformas**

## ⚙️ **Contexto del desafío**

El Ministerio de Salud necesita desarrollar un sistema backend para la **gestión, seguimiento y control de historias
clínicas de pacientes**. El sistema debe permitir el registro de pacientes, médicos y consultas médicas, manteniendo un
historial completo y garantizando restricciones médicas y de confidencialidad para el acceso a la información sensible
de los pacientes.

## 📌 **Requisitos del modelo `Paciente`**

Tu modelo de datos deberá cumplir con lo siguiente:

- **Número de Historia Clínica**: Campo obligatorio e **inmutable**. Será tu clave primaria.
- **Nombres y Apellidos**: Editables mediante endpoint dedicado.
- **Fecha de nacimiento y Sexo**: Datos **inmutables** después del registro inicial.
- **Tipo de sangre**: Campo obligatorio e inmutable (A+, A-, B+, B-, AB+, AB-, O+, O-).
- **Alergias**: Lista editable de alergias conocidas del paciente (opcional).
- **Estado**: Modificable (ACTIVO, INACTIVO, FALLECIDO).
- **Correo electrónico**: Campo opcional para notificaciones. Debe validarse con formato de email estándar.
- **Médico de cabecera**: Cada paciente puede tener asignado un médico principal responsable de su atención general.
- Cada paciente puede tener múltiples **consultas médicas** registradas en el sistema.

## 📌 **Requisitos del modelo `Médico`**

- **Código de colegiatura**: Campo obligatorio e **inmutable**. Será tu clave primaria.
- **Nombres y Apellidos**: Editables mediante endpoint dedicado.
- **Especialidad**: Campo obligatorio (MEDICINA_GENERAL, CARDIOLOGIA, DERMATOLOGIA, PEDIATRIA, etc.).
- **Estado**: Modificable (ACTIVO, LICENCIA, RETIRADO).
- **Correo institucional**: Campo obligatorio para comunicaciones. Debe validarse con formato de email estándar.
- Un médico puede ser el médico de cabecera de múltiples pacientes.
- Un médico puede registrar múltiples consultas médicas.

## 🚫 **Restricciones Médicas para Consultas**

El sistema deberá validar automáticamente impedimentos médicos antes de registrar consultas. Está **prohibido**
registrar una consulta en los siguientes casos:

- **Médico inactivo**: Un médico con estado diferente a "ACTIVO" no puede atender consultas.
- **Paciente fallecido**: No se pueden registrar consultas a pacientes con estado "FALLECIDO".
- **Especialidad incorrecta**: Ciertas consultas requieren especialidades específicas (por ejemplo, consultas
  pediátricas solo pueden ser atendidas por pediatras).
- **Límite de consultas diarias**: Un médico no puede atender más de 15 consultas en un mismo día.
- **Conflicto horario**: No se pueden programar dos consultas para el mismo médico en el mismo horario.

## 📡 **Endpoints Obligatorios (14 en total)**

A continuación, encontrarás los endpoints que deben ser implementados exactamente como se especifican. Recuerda que cada
endpoint tiene restricciones según roles asignados a los usuarios del sistema.

| #  | Método | Endpoint                                   | Descripción                                                                 | Roles Permitidos                        |
|----|--------|--------------------------------------------|-----------------------------------------------------------------------------|-----------------------------------------|
| 1  | POST   | /pacientes                                 | Crear un nuevo paciente en el sistema.                                      | ADMIN, RECEPCIONISTA                    |
| 2  | GET    | /pacientes/{historiaId}                    | Consultar todos los datos de un paciente por su número de historia clínica. | ADMIN, MEDICO, ENFERMERO                |
| 3  | PUT    | /pacientes/{historiaId}/nombres            | Actualizar solamente nombres y apellidos de un paciente.                    | ADMIN, RECEPCIONISTA                    |
| 4  | PUT    | /pacientes/{historiaId}/medico-cabecera    | Asignar o actualizar el médico de cabecera de un paciente.                  | ADMIN, RECEPCIONISTA                    |
| 5  | DELETE | /pacientes/{historiaId}                    | Eliminar un paciente del sistema (si no tiene consultas registradas).       | ADMIN                                   |
| 6  | GET    | /pacientes                                 | Listar pacientes, con filtros opcionales por nombre o estado.               | ADMIN, MEDICO, ENFERMERO                |
| 7  | POST   | /consultas                                 | Registrar una nueva consulta médica previa validación.                      | ADMIN, MEDICO, RECEPCIONISTA            |
| 8  | GET    | /consultas/validar/{medicoId}/{pacienteId} | Verificar si es válido que un médico atienda a un paciente.                 | ADMIN, MEDICO, RECEPCIONISTA            |
| 9  | GET    | /pacientes/{historiaId}/historial          | Consultar el historial médico completo de un paciente.                      | ADMIN, MEDICO                           |
| 10 | PUT    | /pacientes/{historiaId}/estado             | Actualizar el estado de un paciente.                                        | ADMIN, MEDICO                           |
| 11 | POST   | /auth/register                             | Registrar un nuevo usuario en el sistema.                                   | ADMIN                                   |
| 12 | POST   | /auth/login                                | Iniciar sesión y obtener un token JWT.                                      | PUBLIC                                  |
| 13 | GET    | /auth/me                                   | Obtener la información del usuario autenticado actual.                      | ADMIN, MEDICO, ENFERMERO, RECEPCIONISTA |
| 14 | GET    | /medicos/{especialidadId}/disponibilidad   | Obtener la disponibilidad de médicos por especialidad.                      | ADMIN, MEDICO, RECEPCIONISTA            |

## 🔐 **Roles y permisos del sistema**

El sistema hospitalario tendrá varios niveles de acceso claramente diferenciados:

- **Administración del sistema**:
    - `ADMIN`: Puede realizar cualquier acción sobre el sistema (crear, actualizar, eliminar, consultar).
    - `RECEPCIONISTA`: Puede registrar pacientes, actualizar información y programar consultas. No puede acceder al
      historial médico completo.

- **Personal médico**:
    - `MEDICO`: Puede consultar información de pacientes, registrar consultas y actualizar estados. Acceso completo al
      historial médico.
    - `ENFERMERO`: Acceso limitado a consulta de información básica de pacientes, sin posibilidad de modificar
      registros médicos.

> **Importante**: Debes asegurar la protección estricta de los endpoints según estos roles asignados.

## ⚠️ **Requisitos Técnicos Adicionales**

- Usa relaciones `@OneToMany`, `@ManyToOne` y `@ManyToMany` correctamente en JPA para modelar la relación
  médico-paciente y consultas.
- Implementa consultas avanzadas exclusivamente con métodos JPA (`@Query` o métodos derivados de JpaRepository).
- Debes implementar DTO claramente definidos y excepciones personalizadas con manejo global (`@ControllerAdvice`).
- Seguridad obligatoria con roles (`ADMIN`, `MEDICO`, `ENFERMERO`, `RECEPCIONISTA`). Define adecuadamente la protección
  de endpoints según rol.
- 🛠️ **Variables de entorno**:
  Todos los valores sensibles y configuraciones como credenciales de base de datos, puertos
  y claves secretas (por ejemplo, para JWT) deben manejarse exclusivamente mediante **variables de entorno**.
  No se
  permite hardcodear ningún dato sensible en el código fuente.
- 🐘 **Base de datos PostgreSQL usando Docker**:
  La base de datos del sistema deberá ser **PostgreSQL** y debe correr
  dentro de un contenedor Docker.

## 🔎 **Criterios de Evaluación**

- Claridad, consistencia y complejidad del modelo de datos (relaciones y entidades).
- Cumplimiento correcto de reglas y validaciones solicitadas.
- Implementación efectiva y limpia de capas Repository y Service.
- Eficacia del manejo de excepciones globales y validaciones.
- Implementación adecuada de seguridad basada en roles.
- Código ordenado, legible y uso correcto de las mejores prácticas.

⏳ **Tiempo límite**: 2 horas.

🤖 **Uso de IA**: Permitido como asistente, pero se evaluará tu comprensión y aplicación práctica.

¡Éxitos! 🚀
