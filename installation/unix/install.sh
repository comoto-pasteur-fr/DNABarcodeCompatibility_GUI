#!/usr/bin/env bash
#====================================
# Make sure to run the script as root
#if [ "$EUID" -ne 0 ]
#  then echo "Please run as root (sudo)"
#  exit
#fi

#====================================
# Export installation folder into the PATH if not already in.
[[ ":$PATH:" != *":/usr/local/bin:"* ]] && export PATH="${PATH}:/usr/local/bin"

#====================================
# Test if /usr/local/bin/DnaBarcodeCompatibility.jar exits and if so ask whether to update
if [ -f /usr/local/bin/DnaBarcodeCompatibility.jar ]; then
	ANSWER="";
	while [ ! "$ANSWER" = "Y" -a ! "$ANSWER" = "Yes" -a  ! "$ANSWER" = "No" -a  ! "$ANSWER" = "N" ] ; do
		read -p "DnaBarcodeCompatibility is already installed. Force installation? Yes/No: " ANSWER
	done

	[[ "$ANSWER" = "No" ]] || [[ "$ANSWER" = "N" ]] && exit 0
fi

#====================================
# Installation procedure
echo "Installation now requires root priviledges:"
[[ ! -d /usr/local/bin/ ]] && sudo mkdir -p /usr/local/bin/
sudo cp DnaBarcodeCompatibility*.jar /usr/local/bin/DnaBarcodeCompatibility.jar
sudo cp DnaBarcodeCompatibility_startup.sh /usr/local/bin/DnaBarcodeCompatibility

sudo chmod ugo+rx /usr/local/bin/DnaBarcodeCompatibility*.jar
sudo chmod ugo+rx /usr/local/bin/DnaBarcodeCompatibility

#====================================
# Test DnaBarcodeCompatibility installation
hash DnaBarcodeCompatibility; [[ $? -eq 1 ]] && echo "DNABarcodeCompatibility_GUI installation failed!" && exit 1
hash DnaBarcodeCompatibility; [[ $? -eq 0 ]] && echo "DNABarcodeCompatibility_GUI has been successfully installed!"

#====================================
# Check R installation
hash R; [[ $? -eq 1 ]] && echo "R is not installed, please install R: https://www.r-project.org/ and the DNABarcodeCompatibility R-package before using the GUI" && exit 1
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

