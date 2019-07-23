**Simple API**

Simple REST API project using spring boot and JPA to persistense layer.

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

**Course**

* Course code is not auto-generated and must be especified at creation time.
* Endpoint: /api/v1/course
* GET /api/v1/course ; /api/v1/course/{code}
* POST: `{"code":"01C", "title":"Ciense", "description":"A Ciense course"}`
* PUT /api/v1/course/{code}
* DELETE /api/v1/course/{code}

**Course enrollment**

* Endpoint: /api/v1/course/{id}/enrollment
* To add a student into a course
* POST: /api/v1/course/{course-code}/enrollment/{student-id}
* To remove an enrollment
* DELETE: /api/v1/course/{course-code}/enrollment/{student-id}

**Student enrolls**

* Endpoint: /api/v1/student/{id}/enrolls
