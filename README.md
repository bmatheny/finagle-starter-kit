# About

Some finagle example code for demonstration purposes.

## Running

    $ ./sbt
    > update
    > test
    > run -f config/development.scala
    $ curl http://localhost:4242/shutdown.txt
    > exit

## Ostrich

    $ ./sbt
    > run -f config/development.scala
    $ ./scripts/serviceClient.rb "hello world"
    $ curl http://localhost:4242/stats.txt

## Configuration

Look in the config directory. You can tweak logging options, ports, etc.
