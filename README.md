This project contains some projects to test and practice microservices using spring.

It's divide in two groups:

- Infrastructure domain: contains project used to manage all services, it contains:
    - Eureka server
    - Config server
    - Admin server
    - API gateway
- Business domain: contains the services related to business
    - Product
    - Transactions
    - Customer

Every project contains a DockerFile to generate the corresponding image and the root contains a docker-compose to start
all images
