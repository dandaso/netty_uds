require 'socket'
client = UNIXSocket.open('/tmp/netty.sock')
client.puts "1"

while(line = client.gets) do
  puts line
  sleep 1
  client.puts "1"
end
