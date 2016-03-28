# td-agent-forwarder

This program uses when you forward log files to td-agent by fluency.

## About Fluency
https://github.com/komamitsu/fluency

http://komamitsu.hatenablog.com/entry/2015/12/07/003335

## Install
- Java1.8 or higher
- td-agent

### Setting
- td-agent
<code>
\<source\>  
  type forward  
  port 24224  
\</source\>  
\<match td.*.*\>    
  type tdlog    
  endpoint api.treasuredata.com  
  apikey 4148/e53be904f093ebd4b4b8e6a19b6f57515556740b  
  auto_create_table  
  num_threads 4  
  use_gzip_command  
  buffer_type file  
  buffer_path /var/log/td-agent/buffer/td  
  buffer_chunk_limit 16m  
  buffer_queue_limit 5000  
  use_ssl true  
\</match\>  
</code>
