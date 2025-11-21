# ☁️ Comprehensive Revision Notes: Microservices & Spring Cloud

This document summarizes the key concepts of microservices architecture, focusing on inter-service communication, load balancing, and **Service Discovery** using **Spring Cloud** and **Netflix Eureka**.

---

##  1. Microservices Architecture

Microservices architecture is a way to structure a large application as a collection of smaller, independent, and loosely coupled services.

* **Autonomy:** Each service can be developed, deployed, and scaled independently.
* **Focus:** Each service performs a specific business function (e.g., User Service, Product Service, Payment Service).

---

##  2. Communication Among Services

Services must communicate to complete a transaction.

| Type | Mechanism | Analogy |
| :--- | :--- | :--- |
| **Synchronous** | Direct **HTTP/REST** calls. The calling service waits for an immediate response. | Calling a friend on the phone. |
| **Asynchronous** | Using **Message Queues** (like Kafka). Services send a message and don't wait for an immediate reply. | Sending a text message. |

---

##  3. Load Balancing (Distributing Traffic)

Load balancing ensures that requests are evenly distributed across multiple running instances of a service, preventing any single instance from being overloaded.

* **Client-Side Load Balancing:** The service initiating the request (the "client") is responsible for knowing the location of all available service instances and choosing one to communicate with.
    * In Spring Cloud, this is handled by configuring a **`RestTemplate`** with a load-balancing client library.

---

##  4. Service Discovery (The Real-Time Phonebook)

Service discovery is the mechanism that allows microservices to find each other's network locations dynamically, as IPs and ports change during scaling operations.

### Netflix Eureka

Netflix Eureka is the primary tool used in Spring Cloud for service discovery. It acts as the central registry.

* **Server/Registry:** The Eureka server maintains a live list of all running microservices.
* **Client/Registration:** Every microservice includes a Eureka Client dependency and registers itself with the server upon startup.
* **Heartbeat:** Registered services send regular **heartbeat** signals to the Eureka server. If the heartbeat stops, the server removes the inactive service instance from the registry.

---

##  5. Spring Cloud & Eureka Implementation

Spring Cloud simplifies the integration of service discovery and load balancing into your application code.

| Concept | Implementation in Spring Cloud |
| :--- | :--- |
| **Service Registration** | Add the `eureka-client` dependency and use the `@EnableEurekaClient` annotation (or `@EnableDiscoveryClient`). |
| **Dynamic Discovery** | Use a **`@LoadBalanced RestTemplate`** to communicate. The `RestTemplate` automatically looks up the service name (e.g., `http://PRODUCT-SERVICE/`) in Eureka, resolves it to an IP/port, and handles load distribution. |
| **Configuration** | Define environment variables (like ports) to run multiple instances of the same service for testing and scalability. |

## Helpful link for implementation
https://www.baeldung.com/spring-cloud-netflix-eureka
