# 520-Project

## How to Run
1. Install [Maven](https://maven.apache.org/download.cgi) build tool
2. To Run
    1. Either run EleNaApplication with Intellij
    2. Or in terminal from root directory type ./mvnw spring-boot:run
3. In  browser go to localhost:8080
4. Go to http://localhost:8080/importData?path=yourFilePath inputting your OSM file path to parse the file and input 
the node and edge values into the elenaDb.

## Database setup
1. Download [postgres with stackbuilder](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads#windows) all default settings port 5432
2. Launch stackbuilder and download PostGIS under Spatial Extensions 
3. Launch pgadmin4
4. Create database elenaDb with password 'password'

## Adding dependencies
1. Go to maven repository online and copy and paste the dependency into pom.xml and import them as libraries on intellij

## Using LiveReload
1. Download chrome extension [Live Reload](https://chrome.google.com/webstore/detail/livereload/jnihajbhpnppcggbcgedagnkighmdlei)
2. Go to localhost:8080 and click the LiveReload icon to enable it on localhost:8080
3. Recompile any java file to trigger the live reload so you don't have to restart the server to see changes

## How to test
1

## PostGIS Links
1. [PostGis DB Geography](https://postgis.net/workshops/postgis-intro/geography.html)
2. [Hibernate object relational mapping(ORM) to PostGis DB](https://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#spatial-overview)
3. [Point class with coordinate error checking](https://locationtech.github.io/jts/javadoc/org/locationtech/jts/geom/Point.html)
