# JDK 21 / Spring 7 / Tomcat 11 runtime verification

## Verified environment

- Java: Temurin 21.0.12.1
- Spring Framework: 7.0.10 (no Spring Boot)
- Tomcat: 11.0.27
- Servlet / Dynamic Web Module: 6.1
- Maven: 3.9.16

## Checks completed on 2026-10-28

- `mvn clean package`: successful; 66 Java sources compiled with release 21.
- No Java test sources are currently present; Maven success is not a full test suite result.
- WAR: `target/intr-1.0.0-BUILD-SNAPSHOT.war`.
- WAR library inspection: no old Spring, Servlet/JSP API, Log4j 1, or DBCP 1 JARs; container-provided Servlet/JSP/EL APIs are excluded.
- Main logging resource: `WEB-INF/classes/logback.xml`.
- An isolated Tomcat instance loaded the Maven-generated expanded application at `/intr` on loopback port 18080.
- Spring root context and DispatcherServlet initialized successfully.
- Static resource `/intr/resources/js/head_script.js`: HTTP 200.
- Unauthenticated `/intr/intrBoardInqy1010.do`: HTTP 302.
- Login page `/intr/intrMainInqy1010.do`: HTTP 200, including its database query and JSP rendering.
- The temporary verification server was stopped after these checks.

## Eclipse steps

1. Refresh the project (F5), then Maven > Update Project.
2. Verify Project Facets: Java 21 and Dynamic Web Module 6.1.
3. Verify Targeted Runtimes points to the installed Tomcat 11 directory; the runtime display name alone does not establish its actual version.
4. In the Servers view, open the server configuration and select JDK 21 for the runtime.
5. Project > Clean. With the server stopped, use its Clean action and publish the project.
6. Start the server and open `http://localhost:<configured-port>/intr/intrMainInqy1010.do`.
7. Check the console for successful Spring initialization and any subsequent errors.

The IDE context root is `intr`. Manual deployment of the versioned WAR normally uses the WAR filename as its context path unless configured otherwise.

## Remaining manual checks

- Login/logout and authenticated menu permissions.
- Business queries, modifications, approval flows, and transaction rollback.
- File upload/download, multiple files, Korean filenames, and the 100 MiB request limit.
- Excel download with real query results.
- SMTP mail sending.
- JSPs beyond the login page and their JavaScript interactions.

## Known non-blocking warnings

- The Spring MVC XML namespace is deprecated in Spring 7, but remains in use.
- Compilation reports deprecated `URL(String)` constructors, redundant casts, and annotation-processing warnings.

No application data modifications or mail sending were performed during verification. Build and diagnostic artifacts are kept in the workspace-level `.maven-migration-cache` directory, outside the application repository.
