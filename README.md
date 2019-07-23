**Simple API**

Simple API project using spring boot and JPA to persistense layer.

**Run**

To run the api and the tests

* $ mvn clean test spring-boot:run

It will run at http://localhost:8080, by default the storage back-end is in memory H2.

**Students**

* Student id is auto-generated.
* Endpoint: /api/v1/students
* GET /api/v1/students ; /api/v1/students/{id}
* POST /api/v1/students: `{"firstName":"Ernesto",	"lastName":"Suarez"}`
* PUT /api/v1/students/{id}
* DELETE /api/v1/students/{id}

**Class**

* Class code is not auto-generated and must be especified at creation time.
* Endpoint: /api/v1/course
* GET /api/v1/course ; /api/v1/class/{code}
* POST: `{"code":"01C", "title":"Ciense", "description":"A Ciense course"}`
* PUT /api/v1/course/{code}
* DELETE /api/v1/course/{code}

**Student enrollment**

* Endpoint: /api/v1/students/{id}/enrollment
* To add a student into a course
* POST: /api/v1/students/{id}/enrollment/{couse-code}
* To remove an enrollment
* DELETE: /api/v1/students/{id}/enrollment/{course-code}

**Class enrolls**

* Endpoint: /api/v1/course/{code}/enrolls
* To get course enrolls
* GET /api/v1/course/{code}/enrolls
