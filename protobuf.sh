#!/bin/bash

# protoc should be installed and included in your PATH environment variable for this execution.
src_dir=./src/main/java/com/potatosoft/protobuf
output_dir=./src/main/java
protoc --java_out=$output_dir $src_dir/*.proto
