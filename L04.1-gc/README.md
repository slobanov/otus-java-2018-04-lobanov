# L04.1-gc

This app is monitors garbage collections and writes to the log the number of gcs of each type (young, old) ) and the time that was spent on collections per minute.

How to run:
* run `$ mvn clean package` - this will run tests and output final artifact.
* run `$ mvn exec:exec@{GCName}`, where `{GCName}` can be one of `G1`, `CMS`, `Serial` or `Parallel` to run application against given GC.

Aggregated statistics on number of gcs and total spent time for different types of GC can be found in [notebook](gc.ipynb).

- - - -

Sergey Lobanov
[s.y.lobanov@yandex.ru](mailto:s.y.lobanov@yandex.ru?Subject=otus-java-2018-04-lobanov)
