The ***UserAuthN/AuthZ*** Service is a dedicated Spring Boot microservice that manages authentication (AuthN) and authorization (AuthZ) for the system.
It uses Spring Security with ***OAuth2*** to deliver a robust security layer for user login, identity verification, and secure token distribution.

Key capabilities include:

***User Authentication using Spring Security OAuth2***

***Token Generation (OAuth2 compliant) for secure API access***

***Role-Based Access Control (RBAC) for fine-grained permissions***

***Authorization Filters ensuring only valid and authorized users can access specific services***

***Secure communication between microservices using bearer tokens***

This service acts as the central identity provider, ensuring scalable, standards-based security across the system.
