# Build Instructions

### Cloning from GitHub (include the GitHub SSH URL of the repo).
+ Go to the repository Located at [samurai_server GitHub](https://github.com/project-progression-system/samurai-server)

+ Click the "Clone or download" green button.

+ Select Clone with SSH and copy.

+ Open IDE and select checkout from version control 

+ Select Git

+ Past into URL field 

+ Open

+ The pom.xml will have issues so import as maven project when prompted by system after its done building. 

+ Or do not open right away and at home screen select import and import as a maven project. 

+ Select the import maven projects automatically.
 
+ Then change the name to samurai-server(the first way I find easier, however this second way is the proper way).

### Any changes which might be needed in `application.properties`, or in *File/Project Structure*, etc.
+ If lines 4-6 are not commented out comment them out.

### Anything that needs to be set in the run configuration.
+ Samurai-Server is the run config you want(has automatically appeared on all imports so far).

### Any server configuration needed.
##### In Order for the server to work in conjunction with our app you need to do the following to prepopulate the data base with at least 1 assignment.
######Base Url: http://samuraiserver-env.r9pnamsd2a.us-east-2.elasticbeanstalk.com/rest/titan/

+ use the teacher.json file as a reference to prepopulate a teacher user, using the base url under /users

+ use the student.json file a reference to prepopulate a teacher user, using the base url under /users

+ use the assignment.json file a reference to prepopulate a assignment, using the base url under /assignment
 
+ use the completion.json file a reference to prepopulate a completion of an assignment, using the base url under /completions