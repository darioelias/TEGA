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
#	1: id del Análisis de Genotipos 
#	2: path absoluto a la carpeta donde se encuentran los archivos del procedimiento 
#	3: path absoluto a la carpeta donde se alojarán los archivos generados 
#	   por el procedimiento y donde se encuentran los archivos exportados.


idAnalisis=$1;
pathProc=$2
pathDestino=$3;

cd "$pathDestino";

echo "";
cat ejecucion.log;

if [ ! -f "structureHarvester/cmd.txt" ]; then
	cant=$(find structure_out/ -type f -name '*.out_f'  | wc -l);
	echo "$(date): structure (finished runs: $cant)";
fi
echo "";

