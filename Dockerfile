from java
add target/nerve-0.0.1-SNAPSHOT-jar-with-dependencies.jar /workdir/ganglion.jar
expose 5700
cmd java -jar /workdir/ganglion.jar