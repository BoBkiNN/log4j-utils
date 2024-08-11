<a href="https://jitpack.io/#BoBkiNN/log4j-utils">
<img style="align: center" 
src="https://jitpack.io/v/BoBkiNN/IndigoDataIo.svg" alt="JitPack release">
</a>
  
This is utility library that provides new log4j2 configuration utilities

### Usage (Maven):
1. Add JitPack repository:
 ```xml
<repository>
  <id>jitpack.io</id>
  <url>https://jitpack.io</url>
</repository>
```
2. Add dependency:
```xml
<dependency>
  <groupId>com.github.BoBkiNN</groupId>
  <artifactId>log4j-utils</artifactId>
  <version>1.0.0</version>
</dependency>
```

---
### [LoggerNameRewritePolicy](src/main/java/xyz/bobkinn/log4jutils/LoggerNameRewritePolicy.java)
Rewrites logger name
```xml
<Rewrite name="rewrite">
  <LoggerNameRewritePolicy>
    <KeyValuePair key="com.sedmelluq.lava." value="LavaPlayer"/>
  </LoggerNameRewritePolicy>
  <AppenderRef ref="rewrite1"/>
</Rewrite>
```
This config above replaces logger names that starts with `com.sedmelluq.lava.` with `LavaPlayer`.\
One pair can specify multiple keys by separating them with `,` like `key="com.sedmelluq.lava.,MyAudioLoader"`.\
If key ends with `.` symbol, then this replacement will be used if original logger name starts with this key, or if not then original logger name must match key.
