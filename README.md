About
=================

Java front-end for the DNABarcodeCompatibility R-package.


Installation 
================

#### Requirements

* [DNABarcodeCompatibility R-package](https://github.com/comoto-pasteur-fr/DNABarcodeCompatibility#installation) installed.
* [Java (JDK 8)](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) as this version from Oracle includes JavaFx.

#### Downloads 

* [Windows](https://www.dropbox.com/s/4v1zs355gvktwpp/DnaBarcodeCompatibility.maven.app_20180703.jar?dl=0)
* [Unix/MacOSX](https://www.dropbox.com/s/k3wqf8ijwrt8ggq/DNABarcodeCompatibility_Unix.zip?dl=0)

#### Installation on Windows 

* Unzip the downloaded file
* Setup the R_HOME system environment variable [here](https://github.com/comoto-pasteur-fr/DNABarcodeCompatibility_GUI/tree/master/installation/windows/set_RHOME.md)
* Double-click on the jar file 
* The first time you start the application, a window pops up to set up the R library paths that you copy-paste individually **without** quotes from an R console in which you type in the following command:
```
.libPaths()
```

#### Installation on Unix/MacOSx

* Unzip the downloaded file
* Run the install.sh file as superuser in a terminal console
```
sudo bash install.sh
```
* On **Linux**: double-click on DNABarcodeCompatibility_startup.sh and select 'launch'
* On **MacOSX**: double-click on the 'DNABarcodeCompatibility' launcher (that you can place anywhere you like in your system)
* The first time you start the application, a window pops up to set up the R library paths that you copy-paste individually **without** quotes from an R console in which you type in the following command:
```
.libPaths()
```

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


