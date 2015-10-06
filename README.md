ENBT [![Build Status](https://travis-ci.org/Ensemplix/ENBT.svg?branch=master)](https://travis-ci.org/Ensemplix/ENBT)
============
**E**nsemplix **N**amed **B**inary **F**ormat - это наша реализация сериализации данных, которую придумали в Mojang. Спецификацию формата можно прочитать [здесь](http://goo.gl/1H3qr2). Мы пошли дальше любых других реализаций этого формата и используем метапрограммирование на аннотациях. Наша реализация близка по сути к обычной явовской сериализации.

## Подключение

Для подключения библиотеки в своем проекте необходимо использовать Maven или Gradle.

### Maven
```xml
<repositories>
    <repository>
        <id>Ensemplix</id>
        <url>http://maven.ensemplix.ru/artifactory/Ensemplix</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>ru.ensemplix.nbt</groupId>
        <artifactId>ENBT</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

### Gradle
```gradle
repositories {
    maven {
        url 'http://maven.ensemplix.ru/artifactory/Ensemplix/'
    }
}

dependencies {
   compile 'ru.ensemplix.nbt:ENBT:1.0-SNAPSHOT'
}
```
## Использование
Далее приведен пример работы с servers.dat в клиенте игры. Мы, например, используем библиотеку для редактирования списка серверов на лету.
```java
public class Server {

    @NBT
    public String name;

    @NBT
    public String ip;

    @NBT
    public boolean hideAddress;

}
```
```java
public class ServerList {

    @NBT
    public List<Server> servers;

}
```
```java
// Превращаем в объект.
NBTInputStream in = new NBTInputStream(new FileInputStream("servers.dat"), false);
ServerList serverList = in.readObject(ServerList.class);
// Далее делаем, что хотим с объектом и сохраняем опять.
NBTOutputStream out = new NBTOutputStream(new FileOutputStream("servers.dat"), false);
out.writeObject(serverList);
```
