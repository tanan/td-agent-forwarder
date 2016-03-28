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

`

    <source>
    
      type forward  
      port 24224  
    
    </source>
    
    <match td.*.*>    
    
      type tdlog
      endpoint api.treasuredata.com
      apikey <your api key>
      auto_create_table  
      num_threads 4 
      use_gzip_command  
      buffer_type file  
      buffer_path /var/log/td-agent/buffer/td  
      buffer_chunk_limit 16m  
      buffer_queue_limit 5000  
      use_ssl true  
    
    </match>  
    
