#!/usr/bin/env bash

#====================================
# Export installation folder into the PATH if not already in, and put it at the end (low priority).
[[ ":$PATH:" != *":/usr/local/bin:"* ]] && export PATH="${PATH}:/usr/local/bin"
[[ ":$PATH:" != *":/usr/bin:"* ]] && export PATH="${PATH}:/usr/bin"
[[ ":$PATH:" != *":/bin:"* ]] && export PATH="${PATH}:/bin"

#====================================
# Check R installation
hash R; [[ $? -eq 1 ]] && echo "R not found, please install R: https://www.r-project.org/ and the DNABarcodeCompatibility R-package before using the GUI" && exit 1
hash R; [[ $? -eq 0 ]] && echo "Check R installation: OK" 

#====================================
# Check R version (at least R 3.2 is required)
RVERSION=$(R --version | awk '/R version/{print $3}')
MAINRELEASE=$(echo $RVERSION | awk 'BEGIN{FS="."}{print $1}')
SUBRELEASE=$(echo $RVERSION | awk 'BEGIN{FS="."}{print $2}')
if [ $MAINRELEASE -gt 3 ] || ([ $MAINRELEASE -eq 3 ] && [ $SUBRELEASE -ge 2 ]); then
    echo "Check R version >= 3.2: $RVERSION OK"
else
    echo "You R installation is outdated: version $RVERSION. At least v3.2 is required to run the DNABarcodeCompatibility R-package" 
	echo "Please, update R and install the DNABarcodeCompatibility R-package before using the DNABarcodeCompatibility GUI." 
    exit 1
fi

export R_HOME=$(R RHOME)

JAR=$(find . -depth 1 | grep DnaBarcodeCompatibility.maven.app_[0-9].*.jar)
java -jar $JAR

exit 0
