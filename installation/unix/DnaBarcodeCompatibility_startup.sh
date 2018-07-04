#!/usr/bin/env bash

[[ ":$PATH:" != *":/usr/local/bin:"* ]] && export PATH="/usr/local/bin:${PATH}"
[[ ":$PATH:" != *":/usr/bin:"* ]] && export PATH="/usr/bin:${PATH}"
[[ ":$PATH:" != *":/bin:"* ]] && export PATH="/bin:${PATH}"
 
hash R; [[ $? -eq 1 ]] && echo "Please, check your R installation. Exiting..." && exit 1

export R_HOME=$(R RHOME)

java -jar /usr/local/bin/DnaBarcodeCompatibility.jar

exit 0
