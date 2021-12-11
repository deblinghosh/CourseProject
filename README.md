### Title
REST API to search movies from IMDB

### Description
IMDb is an online database of information related to films, television series, home videos, video games, and streaming content online
 – including cast, production crew and personal biographies, plot summaries, trivia, ratings, and fan and critical reviews.

Though IMDb have robust search mechanism through user interface but lack support for REST APIs which can be integrated
 with customized movie search application across multiple movie databases.
 
The goal of this project was to build REST API using which you can search / sort movies based on filters e.g. year, name.

The REST APIs are build using Spring Boot Framework.
Search API uses jsoup library to crawl IMDb search results.
Note : Current implementation of search is only scoped for feature films.

**API**: GET {service-host:port}/movie-service/movies/search

**Authorization** : Public

**Search Filter Attributes:**
 * name (movie name)
 * year (release year)

**Valid sortBy attributes (case insensitive):**
 * rating (IMDb user rating - default)
 * year (release year)

**Valid sortOrder values:**
 * asc (ascending order)
 * desc (descending order - default)
 
**Paging Information**
 * In the request, count represents the number of movies you’d like to return. Default is 10.
 * In the request, startIndex represents the index of a movies to start page from, i.e. count = 10 and startIndex = 11 will get you
   the second page with 10 users; count = 20 and startIndex = 41 will get you the 3rd page with 20 movies. Default is 1.
 * In the response, totalResults represents the total number of movies that match the criteria specified in the filter.
 * In the response, startIndex represents the index of a user the page starts with (should match startIndex from the request).
 * In the response, itemsPerPage represents the number of movies in the response. Should match the count, if the totalResults >= count.
 
**Examples**
1. Get paginated first 10 movies (default sorted by rating in descending order)

   API Request : {service-host:port}/movie-service/movies/search 

2. Get paginated first 10 movies from year 1998 (default sorted by rating in descending order)

   API Request : {service-host:port}/movie-service/movies/search?filter=year=1998
   
3. Get paginated first 10 movies filtered by name (containing Christmas) (default sorted by rating in descending order)

   API Request : {service-host:port}/movie-service/movies/search?filter=name=Christmas
   
4. Get paginated first 10 Christmas movies from year 2005 (default sorted by rating in descending order)

   API Request : {service-host:port}/movie-service/movies/search?filter=year=2005 and name=Christmas
   
5. Get paginated first 20 Christmas movies sorted by year in descending order

   API Request : {service-host:port}/movie-service/movies/search?filter=name=Christmas&sortBy=year&sortOrder=desc&count=20

6. Get paginated second 20 (count = 20, startIndex = 21) worst rated movies in year 2005

   API Request : {service-host:port}/movie-service/movies/search?filter=year=2005&sortBy=rating&sortOrder=asc&count=20&startIndex=21
   
### Running the service
#### Pre requisite
1. Maven installed to build project.
2. JVM installed (v1.8 onwards) to run the movie service.

#### Steps
1. Clone project.
2. Open terminal from the directory where the project is checked out.
3. Build sprint boot jar using maven (install maven) : /CourseProject> mvn clean package
4. Move to target directory : cd target
5. Executing jar : /CourseProject/target> java -Dserver.port=[port] -jar imdb-crawler-1.0.0.jar 
   Example : If executing locally on port 9001 then use below command. Make sure no other service is running in the port. 
   - java -Dserver.port=9001 -jar imdb-crawler-1.0.0.jar 
   - The service should be up in localhost:9001 and can be verified by : http://localhost:9001/movie-service/actuator/health

### Project presentation

### Future enhancements
1. Crawl data from other movie database and do aggregation on review, resolve name conflicts, calculate average out ratings.
2. Support more filter attributes and compound queries for search.
   
### Reference Documentation
For further reference, please consider the following sections:
* [Spring Boot](https://docs.spring.io/spring-boot/docs/2.6.1/)
* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [JSoup](https://jsoup.org/)
* [IMDB Title Search](https://www.imdb.com/search/title/)

