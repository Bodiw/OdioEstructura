#!/bin/sh
# Script de ejecucion de la gui

# Ejecutable de Java
DIR="/usr/lib/jvm/jdk-17.0.1+12/bin/java"
# Directorio del jar 
JAR="/home/bogdan/Desktop/UPM/Estructura/OdioEstructura/OdioEstructura-0.1.jar"
# Directorio del emulador
EMU="/home/bogdan/Desktop/UPM/Estructura/emu/em88110"
# Configuracion del emulador
CONF="/home/bogdan/Desktop/UPM/Estructura/src/conf"
# Direccion del binario
BIN="/home/bogdan/Desktop/UPM/Estructura/bin/pro.bin"

# Configuracion inicial

# Formato de display de los registros
# Opciones: "HEX", "DEC"
regDisp="HEX"
# Incluir el "Rnm |" a la izda de los registros 
# Opciones: "Yes", "No"
rDisp="Yes"
# Incluir espacios entre los registors
# Opciones: "Yes", "No"
spaceDisp="Yes"
# Formato de display de la MP
# Opciones: "HEX", "CHAR"
MPDisp="CHAR"
# Tamano Inicial de Next
# Opciones: 1-10000
nextStep="1"
# Direccion Inicial de la MP
# Opciones: 0-8000
startMP="4000"

$DIR -jar $JAR $EMU $CONF $BIN $regDisp $rDisp $spaceDisp $MPDisp $nextStep $startMP