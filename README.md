## Building and Running steps
Open a terminal in the root project root directory.

Build the project with ```./gradlew build```. It will build a fat spring bot jar that contains all dependencies and can be run directly. It will utilize the version defined in the **gradle.properties** file, and for now it is 0.1.0-SNAPSHOT.

Then, if you want to directly run the app, you can just make ```java -jar build/libs/psp-0.1.0-SNAPSHOT.jar``` and it will start the server. Please consider that it must be run with Java starting with version 21.

If you want to have a containerized application, then you need to:  
1) Build a Docker image with ```docker build --build-arg JAR_VERSION=0.1.0-SNAPSHOT -t psp .```  
2) Run docker image with ```docker run -p 8080:8080 --name psp psp  ``` to see the application running in the terminal or with ```docker run -d -p 8080:8080 --name psp psp  ``` to run the image in the detached mode, and it will run in the background.

  
Also, you can access API documentation (currently single endpoint) by accessing http://localhost:8080/swagger-ui.html.



## Implementation Notes

### Tech Stack  
I chose the stack that is supposed to be used on your real project, which is Kotlin, Spring Weblfux, and Corotuines.

### Timeouts:  
I did not set up any timeouts inside the code because it was supposed to be just a mock implementation, and in real cases, of course we need to set up timeouts, implement retries, etc.

### Security and encryption:  
In the real app, of course, we need to encrypt sensitive information. The algorithm depends on the nature of the information; if this information can be encrypted, stored, and then never decrypted, like passwords, where after storing we will just check if the user inputs a correct raw password but no way use the raw version of the stored password itself, we can use hashing algorithms like **BCrypt**. But in the case of something like CVV code, which later may need to be decrypted to be sent downstream, we need algorithms that allow both encrypting and decrypting based on some secret and possibly salt, and one of the most famous solutions is **AES**.

    
Encryption of the whole traffic using **SSL/TLS** is a must if our API will be exposed to the global network, but in case that our service is deployed in the private network and exposed via some load balancer like *HAProxy* or another service that exposed to the global network, then we can sacrifice **SSL/TLS** for the decreasing latency and increasing throughput, but still there are vulnerabilities that can happen inside the private network, like illegal access to the machine having access to this network and something similar, so generally and especially in the financial domain it is essential to have encryption of all traffic even inside the private network, as it will ensure us the safety of the data our system process.

  
Also, **authentication** and **authorization** were not mentioned in the task, but of course, in the real system, we will need mechanisms for validating who accesses our service and which permissions it has.

  
### Metrics  
Of course, any production-ready application must store and expose metrics that allow us to monitor the application during its operational stage. However, for the test task, I didn't add this functionality to avoid going beyond its scope.



## Design Notes  
    
### Architecture:

I used **layered architecture**, and while, particularly for the test task, I didn’t create any sort of specification with strict relation definitions, I tried to make it really clean. We have:

**Domain**: layer with a clean definition of our business entities that is isolated and does not utilize any external components.

**DAO**: data access layer that is responsible for any operations related to the persistence of the data.

**Service**: business logic layer, which contains implementations of our system's functions.

**Web**: layer that contains everything related to our web interface, like API endpoints, filters, exception handling, etc.

**Messages**: layer containing messages (data transfer objects) that our system receives and sends.

**Platform**: layer that now contains two parts: **configuration** and **properties**. Configuration puts all our system's components together, basically utilizing dependency injection. Properties is the place where we define schemes for the system's properties, and thanks to dependency injection, we can then access them all over our application.

**Lib**: layer that contains various utility components. One of the main parts is **validation**. This package contains a small framework for validation, and while it is quite imperative, it defines validation abstractions along with various implementations that provide us with the flexibility and configurability of the validation processes in our application.
