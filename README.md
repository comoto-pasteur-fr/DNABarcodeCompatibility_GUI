About
=================

Java front-end for the DNABarcodeCompatibility R-package.


Installation 
================

#### Requirements

* [Java (JDK 8)](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) because this version from Oracle includes JavaFx.
* [DNABarcodeCompatibility R-package](https://github.com/comoto-pasteur-fr/DNABarcodeCompatibility#installation).
* `rJava` R-package: in an R console, type in `install.packages("rJava")`. 


#### Downloads 

* for [Windows/Unix/MacOSX](https://www.dropbox.com/s/wa1thx156znjdbb/DNABarcodeCompatibility_Unix.zip?dl=0)

#### Installation on Windows 

* Unzip the downloaded file
* Setup the R_HOME system environment variable [here](https://github.com/comoto-pasteur-fr/DNABarcodeCompatibility_GUI/tree/master/installation/windows/set_RHOME.md)
* Double-click on the jar file 
* The first time you start the application, a window pops up to set up the R library paths. To this end, open an R console and type in the `.libPaths()` command to list all paths, and simply copy and paste individual path **without** quotes into the pop up window.

#### Installation on Unix/MacOSx

* Unzip the downloaded file
* Run the install.sh file as superuser in a terminal console
```
sudo bash install.sh
```
* On **Linux**: double-click on DNABarcodeCompatibility_startup.sh and select 'launch'
* On **MacOSX**: double-click on the 'DNABarcodeCompatibility' launcher ![alt tag](https://comoto-pasteur-fr.github.io/DNABarcodeCompatibility_GUI/applet.png) (that you can place anywhere you like in your system)
* The first time you start the application, a window pops up to set up the R library paths. To this end, open an R console and type in the `.libPaths()` command to list all paths, and simply copy and paste individual path **without** quotes into the pop up window.

Documentation
================

* For users

[Quick start tutorial](https://comoto-pasteur-fr.github.io/DNABarcodeCompatibility_GUI/tutorial.pdf)

* For developers

[API documentation](https://comoto-pasteur-fr.github.io/DNABarcodeCompatibility_GUI/)

To build an executable JAR 
```
mvn clean install
```

Support
=========

Please use the [github ticket system](https://github.com/comoto-pasteur-fr/DNABarcodeCompatibility_GUI/issues) to report issues or suggestions. 
We also welcome pull requests.



Reference
==========

Céline Trébeau, Jacques Boutet de Monvel, Fabienne Wong Jun Tai, Raphaël Etournay. (2018, May 31). comoto-pasteur-fr/DNABarcodeCompatibility: First complete release (Version v0.0.0.9000). Zenodo. [![DOI](https://zenodo.org/badge/DOI/10.5281/zenodo.1256863.svg)](https://doi.org/10.5281/zenodo.1256863)


