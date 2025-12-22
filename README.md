# Banking Platform (TP Microservices - Pro)

## Services
- api-gateway (8080)
- account-service (8081) - PostgreSQL + Liquibase
- transfer-service (8082) - PostgreSQL + Liquibase + Feign + Kafka producer
- notification-service (8083) - Kafka consumer

## Infra (DEV local)
docker compose up -d

Keycloak:
- http://localhost:8088
- realm: bank-realm
- users: user1/password, admin1/password
- roles: USER, ADMIN

## Flow
1) Get token from Keycloak
2) Call Gateway:
    - POST http://localhost:8080/api/transfers
    - Authorization: Bearer <token>

## OpenShift (CRC)
- Create project: oc new-project bank-dev
- Apply manifests (order):
    - infra (Keycloak/Kafka if you deploy them on cluster)
    - services manifests in each module openshift/

## Tester rapidement (DEV local)
1 Lancer infra :
-   cd infra
-   docker compose up -d
2 Lancer services :
    ./mvnw -DskipTests clean package
    java -jar api-gateway/target/*.jar
    java -jar account-service/target/*.jar
    java -jar transfer-service/target/*.jar
    java -jar notification-service/target/*.jar
3 Récupérer token Keycloak (Direct access grants) :
- curl -s -X POST "http://localhost:8088/realms/bank-realm/protocol/openid-connect/token" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "client_id=bank-client" \
  -d "grant_type=password" \
  -d "username=user1" \
  -d "password=password"

4 Appeler api-gateway :

curl -X POST "http://localhost:8080/api/transfers" \
-H "Authorization: Bearer <ACCESS_TOKEN>" \
-H "Content-Type: application/json" \
-d '{"fromAccountId":1,"toAccountId":2,"amount":150.00}'
