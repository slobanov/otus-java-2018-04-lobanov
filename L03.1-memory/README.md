# L03.1-memory

Java object memory consumption test stand.

For measuring purposes this app uses [Java Agent for Memory Measurements](https://github.com/jbellis/jamm).
It measures the full memory footprint of an empty string, object and different types of collections and arrays.
App prints it's result into `System.out` in the following format:
```
Empty String                                                           | memory = 40 [bytes]
Object                                                                 | memory = 16 [bytes]
{random ints} ArrayList [empty]                                        | memory = 40 [bytes]
{random ints} ArrayList [size=1]                                       | memory = 96 [bytes]
....
```

How to run:
* run `$ mvn clean package exec:exec`
* or, you can use docker `$ docker run --rm otusjava201804lobanov/l031-memory`


- - - -

Sergey Lobanov
[s.y.lobanov@yandex.ru](mailto:s.y.lobanov@yandex.ru?Subject=otus-java-2018-04-lobanov)
