#!/usr/bin/ruby

require 'socket'
require 'optparse'

options = {
  :port => 4242
}
opts = OptionParser.new do |opts|
  opts.banner = "Usage: serviceClient.rb [option] text"

  opts.on("-p", "--port POT", Integer, "Use the specified port for connecting to the service") do |port|
    options[:port] = port.to_i
  end
end

def client(data, port = PORT)
  begin
    sock = TCPsocket.new('127.0.0.1', port)
    sock.write(data + "\n")
    reply = sock.readline
    puts("Sent: #{data}")
    puts("Received: #{reply}")
    sock.close
  rescue Exception => e
    puts("Caught exception sending data, is the server listening?")
    puts("Exception: #{e.message}")
  end
end

opts.parse!
if ( ARGV.length == 0 ) then
  puts(opts)
  exit
else
  client(ARGV[0], options[:port])
end
