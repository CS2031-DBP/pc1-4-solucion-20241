# Solución PC1 2024-1 Sección 2

## Enunciado (15 pts)
La empresa de tecnología "InnovateTech" ha experimentado problemas importantes con su actual sistema de gestión de proyectos. Los datos se pierden, los permisos son confusos y la interfaz es propensa a errores. Por eso, han contratado tus servicios para desarrollar un nuevo sistema de gestión de proyectos temporal, que permita gestionar tareas y equipos de manera segura y eficiente mientras desarrollan un sistema permanente.

El sistema debe permitir a los empleados registrarse y loguearse de forma segura. Cada empleado se identifica por un ID único, nombre, apellido, correo electrónico, departamento y fecha de ingreso. Cada empleado puede pertenecer a uno o varios proyectos, y cada proyecto tiene un líder. Los proyectos se identifican por un código único y contienen detalles como el nombre del proyecto, descripción, fecha de inicio y fecha de finalización.

Los proyectos están compuestos por varias tareas. Cada tarea tiene un código único y contiene detalles como nombre, descripción, fecha límite, y responsable (empleado asignado). Un proyecto puede tener múltiples tareas, pero cada tarea tiene solo un responsable.

El sistema debe permitir realizar acciones clave, como crear un proyecto, asignar empleados a un proyecto, crear y asignar tareas, y cambiar el estado de una tarea (por ejemplo, de "pendiente" a "en progreso" o "completada"). El sistema también debe incluir opciones para filtrar tareas por estado y obtener una lista de proyectos activos.

Además, se requiere un sistema de permisos para garantizar que solo los empleados autorizados puedan realizar ciertas acciones, como eliminar un proyecto o reasignar tareas a otros empleados. La seguridad debe ser una prioridad, con manejo adecuado de autenticación y autorización.

Tu objetivo es desarrollar este sistema de gestión de proyectos temporal, cumpliendo con todos los requerimientos mencionados, incluyendo modelado de datos, operaciones CRUD, testing, autenticación, manejo de excepciones y desarrollo de endpoints funcionales.

## Rúbrica

<table>
    <tr>
        <th> </th>
        <th>2pts</th>
        <th>1pts</th>
        <th>0pts</th>
    </tr>
    <tr>
        <td><b>Entidades y Relaciones</b></td>
        <td>
        El estudiante identifica e implementa de manera correcta todas las entidades necesarias para resolver el problema. Además, establece todas las relaciones adecuadas entre estas entidades sin cometer errores. Todos los atributos requeridos para la resolución del problema están presentes y se vinculan de manera coherente. 
        </td>
        <td>
        El estudiante identifica e implementa algunas entidades, pero puede faltar una o más. Las relaciones entre las entidades pueden tener errores en solo una entidad o estar incompletas. Los atributos del problema no están completamente relacionados o tienen errores en su conexión.
        </td>
        <td>
        El estudiante no logra identificar ni implementar la mayoría de las entidades necesarias para el problema, o las relaciones entre ellas están incorrectas en 2 entidades o más o no existen. Los atributos y sus conexiones no se aplican correctamente, lo que indica falta de comprensión del problema.
        </td>
    </tr>
    <tr>
        <td><b>Capa de persistencia</b></td>
        <td>
        El estudiante define correctamente la interfaz de repositorio para cada entidad. Define correctamente las configuraciones de conexión a base de datos en el archivo application.properties. Define y usa por lo menos 4 query methods distribuidos entre las 4 interfaces de repositorios.
        </td>
        <td>
        El estudiante no define la interfaz de repositorio para todas las entidades y/o presenta errores en su implementación. Define y usa menos de 4 query methods entre las 4 entidades.
        </td>
        <td>
       El estudiante no define ninguna interfaz de repositorio.No define ningún query method.
        </td>
    </tr>
    <tr>
        <td><b>Implementación de endpoints</b></td>
        <td>
        El estudiante logra implementar correctamente entre 10 y 8 endpoints solicitados en el enunciado, sin ninguna falla de compilación.
        </td>
        <td>
        El estudiante logra implementar correctamente entre 7 a 4 endpoints solicitados en el enunciado, con algunas fallas en el código o en la sintaxis en los demás endpoints.
        </td>
        <td>
        El estudiante logra implementar correctamente menos de 4 endpoints solicitados en el enunciado, con fallas evidentes en los demás endpoints.
        </td>
    </tr>
    <tr>
        <td><b>Manejo de Excepciones</b></td>
        <td>
        El estudiante implementa por lo menos 3 clases propias de excepción y las añade correctamente al controlador global de excepciones de modo que devuelven los código de estados adecuados.
        </td>
        <td>
        El estudiante implementa 2 o 1 clases propias de excepción y las añade correctamente al controlador global de excepciones de modo que devuelven los código de estados adecuados.
        </td>
        <td>
        El estudiante no implementa ninguna clase propias de excepción y/o las excepciones no devuelven los  códigos de estado correctamente.
        </td>
    </tr>
    <tr>
        <td><b>Spring Security</b></td>
        <td>
        El estudiante implementa de manera correcta el flujo de inicio de sesión (login), registro (register) y redirección para usuarios no autorizados.Los usuarios autorizados tienen acceso a las rutas correctas, mientras que los no autorizados son redirigidos correctamente, con respuestas de error apropiadas como HTTP 401 o 403. El flujo cumple todos los requisitos y no presenta errores.
        </td>
        <td>
        El estudiante implementa parcialmente el flujo de login, registro y redirección para usuarios no autorizados. Puede haber errores en el uso de códigos HTTP, como códigos incorrectos o respuestas ambiguas. El flujo de redirección puede tener fallas, permitiendo el acceso a usuarios no autorizados o bloqueando a usuarios legítimos.
        </td>
        <td>
        El estudiante no logra implementar el flujo de login, registro y redirección para usuarios no autorizados de manera correcta. El uso de códigos HTTP es incorrecto o confuso, sin cumplir las normas de seguridad. Los usuarios no autorizados pueden tener acceso indebido a recursos, o el flujo se interrumpe debido a errores críticos.
        </td>
    </tr>
    <tr>
        <td><b>Testing</b></td>
        <td>
        El estudiante emplea Testcontainers para probar un repositorio que contiene funciones especiales. Además, realiza pruebas para al menos dos endpoints de la aplicación.
        </td>
        <td>
        El estudiante no utiliza Testcontainers para probar el repositorio o lo hace de manera erróneo/incompleta. Además, se realizan pruebas para menos de dos endpoints.
        </td>
        <td>
        El estudiante no emplea Testcontainers para probar el repositorio, y realiza testing en menos de dos endpoints.
        </td>
    </tr>
</table>
