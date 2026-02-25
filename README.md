## Required environment variables
* `DB_HOST` — PostgreSQL host
* `DB_PORT` — PostgreSQL port
* `DB_NAME` — PostgreSQL database name
* `DB_USER` — PostgreSQL user
* `DB_PASSWORD` — PostgreSQL password

## Health Check Verification 
**Command:**
```
curl -i localhost:8080/health
```
### Status: Database Connected
![health200.png](https://github.com/user-attachments/assets/0ce0dae8-ae7d-49fc-9403-8ab16788ced2)

### Status: Database Stopped
![health503.png](https://github.com/user-attachments/assets/6ca4f34e-8915-420b-98c7-c5d6fefd2e6e)


## Startup Logs
```json
{"@timestamp":"2026-02-24T20:05:52.607037700Z","log":{"level":"INFO","logger":"org.springframework.boot.actuate.endpoint.web.EndpointLinksResolver"},"process":{"pid":9800,"thread":{"name":"restartedMain"}},"service":{"name":"restaurant-menu-api","node":{}},"message":"Exposing 2 endpoints beneath base path ''","tags":["COMMONS-LOGGING"],"ecs":{"version":"8.11"}}
{"@timestamp":"2026-02-24T20:05:52.685124700Z","log":{"level":"INFO","logger":"org.springframework.boot.tomcat.TomcatWebServer"},"process":{"pid":9800,"thread":{"name":"restartedMain"}},"service":{"name":"restaurant-menu-api","node":{}},"message":"Tomcat started on port 8080 (http) with context path '/'","tags":["COMMONS-LOGGING"],"ecs":{"version":"8.11"}}
{"@timestamp":"2026-02-24T20:05:52.692377300Z","log":{"level":"INFO","logger":"com.example.restaurantmenuapi.RestaurantMenuApiApplication"},"process":{"pid":9800,"thread":{"name":"restartedMain"}},"service":{"name":"restaurant-menu-api","node":{}},"message":"Started RestaurantMenuApiApplication in 6.137 seconds (process running for 6.678)","tags":["COMMONS-LOGGING"],"ecs":{"version":"8.11"}}
{"@timestamp":"2026-02-24T20:05:53.268046600Z","log":{"level":"INFO","logger":"org.apache.catalina.core.ContainerBase.[Tomcat].[localhost].[/]"},"process":{"pid":9800,"thread":{"name":"RMI TCP Connection(1)-192.168.0.100"}},"service":{"name":"restaurant-menu-api","node":{}},"message":"Initializing Spring DispatcherServlet 'dispatcherServlet'","ecs":{"version":"8.11"}}
{"@timestamp":"2026-02-24T20:05:53.269047800Z","log":{"level":"INFO","logger":"org.springframework.web.servlet.DispatcherServlet"},"process":{"pid":9800,"thread":{"name":"RMI TCP Connection(1)-192.168.0.100"}},"service":{"name":"restaurant-menu-api","node":{}},"message":"Initializing Servlet 'dispatcherServlet'","tags":["COMMONS-LOGGING"],"ecs":{"version":"8.11"}}
{"@timestamp":"2026-02-24T20:05:53.270047300Z","log":{"level":"INFO","logger":"org.springframework.web.servlet.DispatcherServlet"},"process":{"pid":9800,"thread":{"name":"RMI TCP Connection(1)-192.168.0.100"}},"service":{"name":"restaurant-menu-api","node":{}},"message":"Completed initialization in 1 ms","tags":["COMMONS-LOGGING"],"ecs":{"version":"8.11"}}
```

## Graceful Shutdown
![shutdown.png](https://github.com/user-attachments/assets/16126c9f-8ea9-424b-b989-274c23aec321)

```json 
{"@timestamp":"2026-02-24T20:23:26.247728100Z","log":{"level":"INFO","logger":"com.example.restaurantmenuapi.config.GracefulShutdownLogger"},"process":{"pid":11372,"thread":{"name":"Thread-5"}},"service":{"name":"restaurant-menu-api","node":{}},"message":"SIGTERM received. Starting graceful shutdown.","ecs":{"version":"8.11"}}
{"@timestamp":"2026-02-24T20:23:26.249729900Z","log":{"level":"INFO","logger":"org.springframework.boot.tomcat.GracefulShutdown"},"process":{"pid":11372,"thread":{"name":"Thread-5"}},"service":{"name":"restaurant-menu-api","node":{}},"message":"Commencing graceful shutdown. Waiting for active requests to complete","tags":["COMMONS-LOGGING"],"ecs":{"version":"8.11"}}
{"@timestamp":"2026-02-24T20:23:26.256751900Z","log":{"level":"INFO","logger":"org.springframework.boot.tomcat.GracefulShutdown"},"process":{"pid":11372,"thread":{"name":"tomcat-shutdown"}},"service":{"name":"restaurant-menu-api","node":{}},"message":"Graceful shutdown complete","tags":["COMMONS-LOGGING"],"ecs":{"version":"8.11"}}
{"@timestamp":"2026-02-24T20:23:26.261255600Z","log":{"level":"INFO","logger":"org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean"},"process":{"pid":11372,"thread":{"name":"Thread-5"}},"service":{"name":"restaurant-menu-api","node":{}},"message":"Closing JPA EntityManagerFactory for persistence unit 'default'","tags":["COMMONS-LOGGING"],"ecs":{"version":"8.11"}}
{"@timestamp":"2026-02-24T20:23:26.263261400Z","log":{"level":"INFO","logger":"com.zaxxer.hikari.HikariDataSource"},"process":{"pid":11372,"thread":{"name":"Thread-5"}},"service":{"name":"restaurant-menu-api","node":{}},"message":"HikariPool-1 - Shutdown initiated...","ecs":{"version":"8.11"}}
{"@timestamp":"2026-02-24T20:23:26.264261500Z","log":{"level":"INFO","logger":"com.zaxxer.hikari.HikariDataSource"},"process":{"pid":11372,"thread":{"name":"Thread-5"}},"service":{"name":"restaurant-menu-api","node":{}},"message":"HikariPool-1 - Shutdown completed.","ecs":{"version":"8.11"}}
```

