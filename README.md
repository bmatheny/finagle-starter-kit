## About

Some finagle sample code for demonstration purposes.

## Running

Note, if you don't work at Tumblr you will need to do the following before
running sbt:

    $ export SBT_NO_PROXY=true

Now to run things...

```
$ ./sbt
> update
> test

...snip...
[info] == net.mobocracy.starter.StarterSpec ==
[info] + StarterService should
[info]   + accept a string
[info]   + throw an exception on magic string
[info] + StarterServiceServer should
[info]   + throw an exception if
[info]     + no port is specified
[info]     + no name is specified
[info]   + shutdown properly
...snip...

> run -f config/development.scala
$ curl http://localhost:9900/shutdown.txt
> exit
```

## Configuration

Look in the config directory. You can tweak logging options, ports, etc.

## Ostrich

    $ ./sbt
    > run -f config/development.scala
    $ ./scripts/serviceClient.rb "hello world"
    $ ./scripts/serviceClient.rb "please throw an exception"
    $ curl http://localhost:9900/stats.txt
    $ curl http://localhost:9900/shutdown.txt

### Output from above

```
$ ./scripts/serviceClient.rb "hello world"
Sent: hello world
Received: hello world 309ecc489c12d6eb4cc40f50c902f2b4d0ed77ee511a7c7a9bcd3ca86d4cd86f989dd35bc5ff499670da34255b45b0cfd830e81f605dcf7dc5542e93ae9cd76f

$ ./scripts/serviceClient.rb "please throw an exception"
Caught exception sending data, is the server listening?
Exception: end of file reached

$ curl http://localhost:9900/stats.txt
counters:
  StarterService/connects: 2
  StarterService/failures/java.lang.Exception: 1
  StarterService/requests: 2
  StarterService/success: 1
  jvm_gc_ConcurrentMarkSweep_cycles: 0
  jvm_gc_ConcurrentMarkSweep_msec: 0
  jvm_gc_ParNew_cycles: 3
  jvm_gc_ParNew_msec: 127
  jvm_gc_cycles: 3
  jvm_gc_msec: 127
  service_request_count: 2
gauges:
  StarterService/connections: 0
  StarterService/pending: 0
  jvm_fd_count: 107
  jvm_fd_limit: 10240
  jvm_heap_committed: 4214489088
  jvm_heap_max: 4214489088
  jvm_heap_used: 47287904
  jvm_nonheap_committed: 72814592
  jvm_nonheap_max: 1124073472
  jvm_nonheap_used: 71789944
  jvm_num_cpus: 8
  jvm_post_gc_CMS_Old_Gen_used: 0
  jvm_post_gc_CMS_Perm_Gen_used: 0
  jvm_post_gc_Par_Eden_Space_used: 0
  jvm_post_gc_Par_Survivor_Space_used: 8638904
  jvm_post_gc_used: 8638904
  jvm_start_time: 1315411957719
  jvm_thread_count: 13
  jvm_thread_daemon_count: 8
  jvm_thread_peak_count: 14
  jvm_uptime: 211539
labels:
metrics:
  StarterService/connection_duration: (average=21, count=2, maximum=35, minimum=6, p25=6, p50=6, p75=35, p90=35, p95=35, p99=35, p999=35, p9999=35, sum=42)
  StarterService/connection_received_bytes: (average=19, count=2, maximum=26, minimum=12, p25=12, p50=12, p75=26, p90=26, p95=26, p99=26, p999=26, p9999=26, sum=38)
  StarterService/connection_requests: (average=1, count=2, maximum=1, minimum=1, p25=1, p50=1, p75=1, p90=1, p95=1, p99=1, p999=1, p9999=1, sum=2)
  StarterService/connection_sent_bytes: (average=70, count=2, maximum=142, minimum=0, p25=0, p50=0, p75=142, p90=142, p95=142, p99=142, p999=142, p9999=142, sum=141)
  StarterService/request_latency_ms: (average=5, count=2, maximum=8, minimum=2, p25=2, p50=2, p75=8, p90=8, p95=8, p99=8, p999=8, p9999=8, sum=10)
  request_hash_msec: (average=1, count=2, maximum=2, minimum=0, p25=0, p50=0, p75=2, p90=2, p95=2, p99=2, p999=2, p9999=2, sum=2)

$ curl http://localhost:9900/shutdown.txt
ok
```

## Future Updates

 * Runtime heap profiling via [heapster](https://github.com/mariusaeriksen/heapster)
 * JMX example

## References

 * [Finagle Project](https://github.com/twitter/finagle)
 * [Finagle Overview](http://engineering.twitter.com/2011/08/finagle-protocol-agnostic-rpc-system.html)
 * [Ostrich Project](https://github.com/twitter/ostrich)
