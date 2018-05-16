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
archivoLog="dapc.log";

cd $pathDestino;

FC_EJECUTAR=$(grep -wi "DAPC_FC_EJECUTAR"  $archivoParametros | awk -F "\t" '{print $4}' | sed 's/\s//g');
FC_K=$(grep -wi "DAPC_FC_K"  $archivoParametros | awk -F "\t" '{print $4}' | sed 's/\s//g');

mkdir "archivos_exp_logs";

cmd="Rscript $pathProc/dapc.r $archivoGenotipos $archivoParametros 1>$archivoLog 2>&1";
eval $cmd;


cp dapc/pmp_indplot.png pmp_indplot.png
cp dapc/pmp_boxplot_assig.png pmp_boxplot_assig.png
#cp dapc/pmp_boxplot_ss.png pmp_boxplot_ss.png
cp dapc/pca_scatter.png pca_scatter.png

if [ $FC_EJECUTAR == 1 ] && [ $FC_K == 0 ]
then
	cp find_clusters/k_stat.png k_stat.png
fi

mv $archivoGenotipos archivos_exp_logs/$archivoGenotipos
mv $archivoParametros archivos_exp_logs/$archivoParametros
mv $archivoLog archivos_exp_logs/$archivoLog

cd archivos_exp_logs
zip -q archivos_exp_logs.zip *
mv archivos_exp_logs.zip ../

cd ../dapc
zip -q dapc.zip *
mv dapc.zip ../

cd ../cv_dapc
zip -q cv_dapc.zip *
mv cv_dapc.zip ../

if [ $FC_EJECUTAR == 1 ]
then
	cd ../find_clusters
	zip -q find_clusters.zip *
	mv find_clusters.zip ../
fi

cd ..
rm -rf dapc
rm -rf cv_dapc
rm -rf find_clusters
rm -rf archivos_exp_logs
rm -f Rplots.pdf

