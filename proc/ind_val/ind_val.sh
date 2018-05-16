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

archivoGenotipos="genotipos.tsv";
archivoParametros="parametros.tsv";
archivoLog="ind_val.log";

cd $pathDestino;

genAlelos=$(grep -wi "IND_VAL_ALELOS_EJECUTAR"  $archivoParametros | awk -F "\t" '{print $4}' | sed 's/\s//g');

mkdir "archivos_exp_logs";

cmd="Rscript $pathProc/ind_val.r $archivoGenotipos $archivoParametros 1>$archivoLog 2>&1";
eval $cmd;


mv $archivoGenotipos archivos_exp_logs/$archivoGenotipos
mv $archivoParametros archivos_exp_logs/$archivoParametros
mv $archivoLog archivos_exp_logs/$archivoLog

cd archivos_exp_logs
zip -q archivos_exp_logs.zip *
mv archivos_exp_logs.zip ../
cd ..
rm -rf archivos_exp_logs

if [ $genAlelos == 1 ]
then
	cd alelos
	zip -q alelos.zip *
	mv alelos.zip ../
	cd ..
	rm -rf alelos
fi

rm -f Rplots.pdf

