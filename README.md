# About

Some finagle example code for demonstration purposes.

## Running

    $ ./sbt
    > update
    > test
    > run -f config/development.scala
    $ curl http://localhost:9900/shutdown.txt
    > exit

## Ostrich

    $ ./sbt
    > run -f config/development.scala
    $ ./scripts/serviceClient.rb "hello world"
    $ ./scripts/serviceClient.rb "please throw an exception"
    $ curl http://localhost:9900/stats.txt

## Configuration

Look in the config directory. You can tweak logging options, ports, etc.
