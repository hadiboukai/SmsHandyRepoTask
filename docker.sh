#!/bin/sh
task=$1
mkdir -m 777 -p $task/storage
exec docker run --rm --read-only \
  --tmpfs=/checker/scratch/build \
  --volume="$PWD/$task/solution:/checker/input:ro" \
  --volume="$PWD/$task:/checker/output" \
  inloop-java-example $task
