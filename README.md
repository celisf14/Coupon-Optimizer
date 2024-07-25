># Coupon Optimizer
Coupon Optimizer es una aplicación desarrollada en Java que implementa una API REST para maximizar el uso de un cupón de compra en Mercado Libre. Este proyecto fue desarrollado como parte del Challenge Cupón, utilizando la biblioteca OR-Tools de Google para resolver el problema del “Knapsack” (problema de la mochila) adaptado a esta situación específica.

## Knapsack Solver

El Knapsack Solver es un algoritmo utilizado para resolver el problema de la mochila (Knapsack Problem), un clásico problema de optimización combinatoria. En este proyecto, se ha adaptado para maximizar el valor total de los ítems comprados sin exceder el monto del cupón. Aquí se explica cómo funciona a nivel interno:

Descripción del Problema

Dado un conjunto de ítems, cada uno con un peso (precio) y un valor, el objetivo es determinar la combinación de ítems que maximiza el valor total sin que la suma de los pesos exceda una capacidad máxima (monto del cupón).

Algoritmo del Knapsack Solver

	1.	Definición del Problema:
	•	Ítems: Cada ítem tiene un item_id, un precio (peso) y un valor.
	•	Capacidad: El monto del cupón representa la capacidad máxima de la mochila.
	2.	Modelo Matemático:
El problema se puede formular como un problema de programación lineal entera:

\text{Maximizar } \sum_{i=1}^{n} v_i x_i

sujeto a:

\sum_{i=1}^{n} p_i x_i \leq C


x_i \in \{0, 1\}

donde:
•	 v_i  es el valor del ítem  i .
•	 p_i  es el precio del ítem  i .
•	 C  es la capacidad del cupón.
•	 x_i  es una variable binaria que indica si el ítem  i  es seleccionado (1) o no (0).
3.	Implementación con OR-Tools:
•	Inicialización: Se define el modelo de optimización y las variables de decisión  x_i .
•	Restricciones: Se agrega la restricción de capacidad, asegurando que la suma de los precios de los ítems seleccionados no exceda el monto del cupón.
•	Función Objetivo: Se define la función objetivo para maximizar el valor total de los ítems seleccionados.
•	Resolución: Se utiliza el solver de OR-Tools para resolver el modelo y encontrar la combinación óptima de ítems.
4.	Proceso del Solver:
•	Branch and Bound: El solver utiliza técnicas como el “Branch and Bound” para explorar el espacio de soluciones posibles. Este método divide el problema en subproblemas más pequeños y evalúa soluciones parciales, descartando aquellas que no pueden mejorar la solución óptima encontrada hasta el momento.
•	Poda: Durante el proceso de búsqueda, se eliminan ramas del árbol de soluciones que no cumplen con las restricciones o que no pueden superar la solución actual.
5.	Resultado:
•	El solver devuelve la combinación de ítems que maximiza el valor total sin exceder el monto del cupón.
•	Se obtiene la lista de item_ids seleccionados y el valor total correspondiente.

Ejemplo Práctico

Supongamos que tenemos los siguientes ítems y un cupón de $500:

	•	Ítem 1: Precio $100, Valor $100
	•	Ítem 2: Precio $210, Valor $210
	•	Ítem 3: Precio $260, Valor $260
	•	Ítem 4: Precio $80, Valor $80
	•	Ítem 5: Precio $90, Valor $90

El solver determinará que la combinación óptima es seleccionar los ítems 1, 2, 4 y 5, maximizando el valor total a $480 sin exceder los $500 del cupón.

Este algoritmo es eficiente y puede manejar listas grandes de ítems, proporcionando una solución óptima al problema de la mochila en el contexto de la maximización del uso de un cupón de compra.

![trilateracion](https://user-images.githubusercontent.com/42285662/217925859-6bf0fc06-b179-49b5-b86b-b92535403cc2.jpeg)

### Validaciones Adicionales

* optimizeCoupon: Este método es el punto de entrada para optimizar cupones. Realiza las siguientes acciones:

  *	Valida la solicitud (itemRequestDTO).  
  * Recupera los detalles de los ítems.    
  * Calcula el precio total de los ítems.    
  * Actualiza los ítems favoritos.    
  * Decide si se debe usar el solucionador de mochila o no.    
  * validateRequest: Valida la solicitud de ítems. Verifica si los ítems están presentes, si el monto no es NaN y si no es igual a cero.

* validateAndAnalyzeItemDetails: Valida y analiza los detalles de los ítems. Realiza lo siguiente:

  * Elimina los ítems inactivos.
  * Verifica que haya ítems disponibles.
  * Valida que todos los ítems sean del mismo sitio.
  * Comprueba que el precio mínimo sea menor o igual al monto disponible.
  * removeInactiveItems: Elimina los ítems inactivos del mapa.

### Flujo del proceso 

El proceso principal de la aplicación sigue los siguientes pasos:

	1.	Recepción de Datos: La aplicación recibe un arreglo de ítems que deben evaluarse y un valor de bono asociado.
	2.	Validación y Preparación de Datos: Se valida que el arreglo de ítems y el valor no estén vacíos. Luego, se eliminan los ítems duplicados y se redondea el valor a dos decimales para asegurar precisión en los cálculos.
	3.	Consulta de Detalles de Ítems: Se consulta el detalle de cada ítem en la lista mediante consultas asincrónicas (hilos) para mejorar los tiempos de respuesta. Además, se ha implementado una caché en memoria para optimizar las consultas a la API de ítems (https://api.mercadolibre.com/items/{item_id}).
	4.	Validaciones Adicionales y Proceso de Resolución: Tras la consulta de detalles, se realizan validaciones adicionales. Posteriormente, se ejecuta el proceso del algoritmo Knapsack Solver para obtener la solución óptima.
	5.	Respuesta Final: Finalmente, se retorna la respuesta esperada con los resultados del proceso.


![trilateracion](https://user-images.githubusercontent.com/42285662/217925859-6bf0fc06-b179-49b5-b86b-b92535403cc2.jpeg)

### Descripcion de los servicios expuestos

* CouponController

  Descripción: Este controlador proporciona servicios para la optimización de cupones y la obtención de estadísticas de ítems favoritos.

  Optimizar Cupón
  * Método: POST
  * Ruta: /coupon
  * Resumen: Optimiza el cupón basado en la solicitud de ítem proporcionada.
  * Descripción: Este endpoint recibe una solicitud con los detalles del ítem para optimizar un cupón. La optimización se realiza utilizando el servicio OptimizerServices.
  * Request Body: ItemRequestDTO
    * Descripción: Objeto que contiene los datos necesarios para la optimización del cupón.
  * Respuesta:
    * Código: 200 OK
    * Cuerpo: ItemResponseDTO
    * Descripción: Objeto que contiene el resultado de la optimización del cupón.


    curl --location 'http://localhost:8080/Coupon-Optimizer/coupon' \
    --header 'Content-Type: application/json' \
    --data '{
        "item_ids": [
            "MLC2069516100",
            "MLC1476580369",
            "MLC2498006388"
        ],
        "amount": 30000
      }'

* Obtener Detalles de Ítems

  * Método: POST
  * Ruta: /coupon/items-details
  * Resumen: Recupera los detalles de los ítems basados en los IDs proporcionados.
  * Descripción: Este endpoint permite recuperar los detalles de una lista de ítems utilizando el servicio OptimizerServices. Se puede enviar una lista de IDs de ítems para obtener la información asociada.
  * Request Body: List<String>
    * Descripción: Lista de IDs de ítems para los cuales se desea obtener detalles.
  * Respuesta:
    * Código: 200 OK
    * Descripción: Objeto que contiene los detalles de los ítems solicitados.


    curl --location 'http://localhost:8080/Coupon-Optimizer/coupon/items-details' \
    --header 'Content-Type: application/json' \
    --data '[
        "MLC2069516100",
        "MLC1476580369",
        "MLC2498006388"
    ]'

* Obtener Top N Ítems Favoritos

  *   Método: GET
  * Ruta: /coupon/stats/{top}
  * Resumen: Recupera los N ítems favoritos principales.
  * Descripción: Este endpoint devuelve los N ítems favoritos más importantes. La cantidad de ítems a devolver se especifica en la ruta.
  * Path Variable: top
    * Descripción: Número de ítems favoritos a recuperar.
  * Respuesta:
    * Código: 200 OK
    * Cuerpo: Object
    * Descripción: Objeto que contiene los ítems favoritos solicitados.


    curl --location 'http://localhost:8080/Coupon-Optimizer/coupon/stats/5' \    


* Obtener Top 5 Ítems Favoritos

  *   Método: GET
  * Ruta: /coupon/stats
  * Resumen: Recupera los 5 ítems favoritos principales.
  * Descripción: Este endpoint devuelve los 5 ítems favoritos más importantes, utilizando el servicio ItemFavoritesServiceImpl.
  * Respuesta:
    * Código: 200 OK
    * Cuerpo: Object
    * Descripción: Objeto que contiene los 5 ítems favoritos principales.


    curl --location 'http://localhost:8080/Coupon-Optimizer/coupon/stats' \



## Instalacion 
Para hacer uso de este servicio  se puede realizar de dos formas ya que el mismo se encuentra desplegado en Google Cloud y basta con acceder endpoint dónde se encuentran cada uno de sus métodos expuestos, otra forma en la cual se pueden utilizar es Clonando el repositorio de git y desplegarlo localmente haciendo una colección de igual forma a la base de datos que se encuentra desplegada en la nube. 

## Información General del Proyecto:

CouponOptimizer es una aplicación Spring Boot diseñada para optimizar cupones y gestionar estadísticas de ítems. El proyecto se basa en el framework Spring Boot y utiliza varias tecnologías y librerías para cumplir con sus objetivos.

*   Configuración
    La aplicación se configura a través del archivo application.yml y se gestiona con Maven. A continuación se detallan los aspectos principales de la configuración:

*   Configuración del Servidor

    Puerto: 8080

    Context Path: /Coupon-Optimizer

Configuración de Spring

* Nombre de la Aplicación: CouponOptimizer
* Base de Datos:
* URL: jdbc:mysql://34.67.139.142:3306/db-cupon
* Usuario: coupon-admin
* Contraseña: cuponmeli2024
* Driver: com.mysql.cj.jdbc.Driver
* JPA/Hibernate:
* Dialect: org.hibernate.dialect.MySQL8Dialect
* Mostrar SQL: true
* Formato SQL: true
* DDL Auto: update
* API Base URL:
* MercadoLibre API: https://api.mercadolibre.com/
* Cache:
  * TTL (Time-to-Live): 60 segundos
  * Tamaño Máximo: 1000 ítems
  * Configuración Asíncrona:
  * Tamaño del Pool Principal: 2
  * Tamaño Máximo del Pool: 50
  * Capacidad de la Cola: 100
  * Prefijo del Nombre del Hilo: async-

Dependencias

El proyecto utiliza Maven para la gestión de dependencias y construcción del proyecto. Las principales dependencias son:

* Spring Boot:
* spring-boot-starter-data-jpa - Para la integración con JPA y la gestión de datos.
* spring-boot-starter-web - Para la construcción de servicios web.
* spring-boot-starter-cache - Para la implementación de caché.
* spring-boot-starter-webflux - Para soporte de programación reactiva.

Otras Librerías:
* lombok - Para la generación de código repetitivo.
* ortools-java - Para la solución de problemas de optimización.
* caffeine - Para la gestión de caché en memoria.
* mysql-connector-j - Conector JDBC para MySQL.
* springdoc-openapi-starter-webmvc-ui - Para la documentación de la API con Swagger.
* json-simple - Para el manejo de JSON.

Testing:
* spring-boot-starter-test - Para pruebas unitarias y de integración.
* mockito-core - Para crear objetos simulados en pruebas.
* junit-jupiter - Para pruebas con JUnit 5.
* Herramientas de Construcción:
* Jacoco: Para la cobertura de pruebas.

## Escalamiento Futuro

CouponOptimizer está diseñado con un enfoque en la escalabilidad para adaptarse a futuras necesidades y crecimiento. A continuación se describen algunas áreas clave de escalamiento y mejoras potenciales:

1. Mejora del Rendimiento

   •	Optimización de Consultas: Implementar estrategias adicionales para optimizar las consultas a la base de datos y reducir el tiempo de respuesta, como el uso de índices y la optimización de consultas complejas.
   •	Escalabilidad de Caché: Ampliar el sistema de caché para manejar un mayor volumen de datos y consultas simultáneas, posiblemente integrando soluciones de caché distribuidas como Redis.

2. Escalabilidad Horizontal

   •	Microservicios: Considerar la adopción de una arquitectura basada en microservicios para dividir el sistema en servicios independientes y escalables, lo que facilitaría la gestión y el despliegue.
   •	Contenerización: Implementar contenerización con Docker y orquestación con Kubernetes para facilitar la escalabilidad horizontal y la gestión de recursos en entornos distribuidos.

3. Mejoras en el Procesamiento Asíncrono

   •	Ajustes en la Configuración de Hilos: Revisar y ajustar la configuración del pool de hilos para manejar mejor la carga de trabajo y mejorar la eficiencia en el procesamiento de tareas asíncronas.
   •	Optimización de la Cola de Tareas: Evaluar el uso de colas de mensajes avanzadas como RabbitMQ o Kafka para mejorar la gestión y el procesamiento de tareas asíncronas.

4. Ampliación de Funcionalidades

   •	Integración de Nuevas APIs: Incorporar la integración con nuevas APIs para ampliar la funcionalidad de la aplicación y proporcionar más opciones a los usuarios.
   •	Análisis de Datos y Reportes: Implementar herramientas avanzadas de análisis de datos y generación de informes para proporcionar estadísticas más detalladas y útiles a los usuarios.

5. Seguridad y Cumplimiento

   •	Auditoría y Monitoreo: Implementar herramientas de auditoría y monitoreo para detectar y responder a posibles problemas de seguridad en tiempo real.
   •	Cumplimiento Normativo: Asegurarse de que la aplicación cumpla con las normativas y estándares de seguridad y protección de datos aplicables a nivel local e internacional.

 ## conclusiones
 CouponOptimizer ha sido desarrollado como una solución robusta y flexible para la optimización de cupones y la gestión de estadísticas de ítems. Utilizando el framework Spring Boot, junto con una serie de tecnologías avanzadas, el proyecto ofrece un sistema eficiente y escalable para satisfacer las necesidades actuales y futuras.

Logros del Proyecto

    •	Optimización de Cupones: El proyecto permite la optimización efectiva de cupones, ayudando a los usuarios a maximizar el valor de sus descuentos y ofertas.
    •	Gestión de Datos de Ítems: Proporciona una solución integral para recuperar y gestionar los detalles de ítems, mejorando la capacidad de análisis y la toma de decisiones.
    •	Desempeño y Escalabilidad: La integración de tecnologías como caché en memoria y procesamiento asíncrono asegura un rendimiento sólido y la capacidad de manejar cargas crecientes.

Impacto

CouponOptimizer facilita a los usuarios la gestión de cupones y el análisis de datos, aportando una solución eficiente y confiable. La aplicación está diseñada para adaptarse a futuros requisitos y escalamiento, asegurando que continúe siendo relevante a medida que evolucionan las necesidades del negocio y del mercado.