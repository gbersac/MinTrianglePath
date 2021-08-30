# MinTrianglePath

How to run the application:

1- Git clone the repository on your computer

2- Build the application. Move to the root of this repository and run this command `sbt assembly`

3- Run the application by using the command: `java -cp target/scala-2.13/MinTrianglePath-assembly-0.1.0-SNAPSHOT.jar app.MinTrianglePath` or, for better input format: `cat << EOF | java -cp target/scala-2.13/MinTrianglePath-assembly-0.1.0-SNAPSHOT.jar app.MinTrianglePath`

4- To run tests, type `sbt test`
