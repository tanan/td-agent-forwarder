# td-agent-forwarder

This program uses when you forward log files to td-agent by fluency.

## About Fluency
https://github.com/komamitsu/fluency

http://komamitsu.hatenablog.com/entry/2015/12/07/003335

## Install
- Java1.8 or higher
- td-agent

## Setting
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

- app.config

`
    
    <configuration>
      <database>td.work_dev</database>
      <tablename>anan_cookie_COMPASS</tablename>
      <folder>/var/samba/deqwas/test</folder>
      <filename>File*.tsv</filename>
      <columns>col1,col2...</columns>
      <columntype>col1:integer</columntype>
      <pPort>24224</pPort>
      <sPort>24225</sPort>
      <maxBufferSize>33554432</maxBufferSize>
      <flushIntervalMillis>200</flushIntervalMillis>
      <maxRetryCount>12</maxRetryCount>
    </configuration>
    

- TdLogForwarder.sh

`

    #!/bin/bash
    EXECUTE_HOME=/opt/TdLogForwarder
    JAVA_HOME=/usr/bin/java
    CLASSPATH=$EXECUTE_HOME/config
    JAVA_OPTS="-DparserConfigurationFile=$EXECUTE_HOME/config/$1"
    
    for lib in `ls -1 $EXECUTE_HOME/lib/*.jar` ; do
      CLASSPATH=$CLASSPATH:$lib
    done
    
    $JAVA_HOME $JAVA_OPTS -cp $CLASSPATH Td.LogForwarder.LogForwarder

## Execute
bash /opt/TdLogForwarder/bin/TdLogForwarder.sh \<config file\>
