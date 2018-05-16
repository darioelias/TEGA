#!/bin/bash

#	This file is part of TEGA (Tools for Evolutionary and Genetic Analysis)
#	TEGA website: https://github.com/darioelias/TEGA
#
#	Copyright (C) 2018  Dario E. Elias & Eva C. Rueda
#
#	TEGA is free software: you can redistribute it and/or modify
#	it under the terms of the GNU Affero General Public License as published by
#	the Free Software Foundation, either version 3 of the License, or
#	(at your option) any later version.
#
#	TEGA is distributed in the hope that it will be useful,
#	but WITHOUT ANY WARRANTY; without even the implied warranty of
#	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#	GNU General Public License for more details.
#
#	You should have received a copy of the GNU Affero General Public License
#	along with this program.  If not, see <https://www.gnu.org/licenses/>.
#
#	Additional permission under GNU AGPL version 3 section 7
#
#	If you modify TEGA, or any covered work, by linking or combining it with
#	STRUCTURE, DISTRUCT or CLUMPP (or a modified version of those programs),
#	the licensors of TEGA grant you additional permission to convey the resulting work.


#Parametros:
#	1: comando a ejecutar del procedimiento
#	2: usuario con el que se ejecutará el comando
#	3: clave del usuario
#	4: id del Análisis de Genotipos 
#	5: path absoluto a la carpeta donde se encuentran los archivos del procedimiento 
#	6: path absoluto a la carpeta donde se alojarán los archivos generados 
#	   por el procedimiento y donde se encuentran los archivos exportados.

comandoProc=$1
usuario=$2
passUsuario=$3
idAnalisis=$4
pathProc=$5
pathDestino=$6


echo "$(date): Inicio ($comandoProc)"

dirTmp=$(mktemp -d "/tmp/XXXXXXXXX")

cp $pathProc/* $dirTmp
chmod u+x $dirTmp/*
cd $dirTmp

cmd="$comandoProc $idAnalisis $dirTmp $pathDestino"

if [ -n "$usuario" ]
then

	grupo="$(id -gn $USER)"
	ownerActual=$(ls -ld $pathDestino | awk '{print $3}')

	if [ "$usuario" != "$ownerActual" ]
	then
		echo $passUsuario | sudo -S -p "" chown -R $usuario $pathDestino
	fi

	echo $passUsuario | sudo -S -p "" chown -R $usuario $dirTmp

	echo $passUsuario | sudo -S -p "" -u $usuario $cmd

	if [ "$usuario" != "$ownerActual" ]
	then
		echo $passUsuario | sudo -S -p "" chown -R $USER:$grupo $pathDestino
	fi

	echo $passUsuario | sudo -S -p "" chown -R $USER:$grupo $dirTmp

else
	#Si no se utiliza un usuario específico el procedimiento se ejecuta 
	#con el usuario actual

	eval $cmd
fi

rm -rf $dirTmp

echo "$(date): Fin ($comandoProc)"
