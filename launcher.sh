#!/bin/sh

netId="nxr200053"
domain="utdallas.edu"
config="config.txt"
gitURL="https://github.com/nishd8/AOS-1"

#get first-line of config file
firstLine=$(head -n 1 "$config")

#get number of nodes
nodes=`echo "$firstLine" | cut -c1-1 `


i=0
nodesArray=()

#get node hosts and save in array
while [ "$i" -le "$nodes" ]; do

      if [ "$i" = 0 ]
      then
        read -r line
      else
        read -r line

        nodesArray[$(( i - 1 ))]=$( echo "$line" | awk '{ print $2 }' )
      fi

      i=$(( i + 1 ))
done < "$config"



for n in "${nodesArray[@]}"
do
  ssh -o StrictHostKeyChecking=no  $netId"@""$n"".""$domain" "javac Main.java; java Main;"
done