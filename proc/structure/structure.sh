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

archivoGenotipos="genotipos.str";
archivoParametros="parametros.tsv";
archivoCantidades="cantidades.tsv";
archivoConjuntos="conjuntos_muestras.tsv";

archivoLog="$pathDestino/ejecucion.log";

cd $pathDestino;
mkdir "structure";
mkdir "structure_out";
mkdir "structureHarvester";
mkdir "clumpp";
mkdir "distruct";

echo "$(date): Inicio" >> $archivoLog;

mv $archivoGenotipos structure/$archivoGenotipos;

genLogs=$(grep -wi 	"STRUCTURE_GEN_LOGS" $archivoParametros | awk -F "\t" '{print $4}' | sed 's/\s//g');

if [ $genLogs == "1" ]
then
	mkdir "structure_log";
fi

cantMuestras=$(grep -wi  "muestras"  		  $archivoCantidades | awk -F "\t" '{print $2}' | sed 's/\s//g');
cantLoci=$(grep -wi		 "loci"  			  $archivoCantidades | awk -F "\t" '{print $2}' | sed 's/\s//g');
cantConjuntos=$(grep -wi "conjuntos_muestras" $archivoCantidades | awk -F "\t" '{print $2}' | sed 's/\s//g');


grep -wi "STRUCTURE_MAIN" 	$archivoParametros | sed 's/STRUCTURE_//gi' | awk -F "\t" '{print "#define "$2" "$4}' > structure/mainparams; 
grep -wi "STRUCTURE_EXTRA" 	$archivoParametros | sed 's/STRUCTURE_//gi' | awk -F "\t" '{print "#define "$2" "$4}' > structure/extraparams;


grep -wi "DISTRUCT" $archivoParametros | sed 's/DISTRUCT_//gi' |  awk -F "\t" '{print "#define "$2" "$4}' > distruct/drawparams.aux;  

cmd="cat $pathProc/distruct.ind.params distruct/drawparams.aux > distruct/drawparams.ind";
eval $cmd;
cmd="cat $pathProc/distruct.pop.params distruct/drawparams.aux > distruct/drawparams.pop";
eval $cmd;

sed '1d' $archivoConjuntos | awk -F "\t" '{print $1" "$3}' > distruct/detalleConjuntos.ssv; 


grep -wi "CLUMPP" $archivoParametros | awk -F "\t" '{print $2" "$4}' > clumpp/clumppparams.ssv;  

vCLUMPP_M=$(grep -wi "CLUMPP_M" clumpp/clumppparams.ssv);
vCLUMPP_W=$(grep -wi "CLUMPP_W" clumpp/clumppparams.ssv);
vCLUMPP_S=$(grep -wi "CLUMPP_S" clumpp/clumppparams.ssv);
vCLUMPP_GREEDY_OPTION=$(grep -wi "CLUMPP_GREEDY_OPTION" clumpp/clumppparams.ssv);
vCLUMPP_REPEATS=$(grep -wi "CLUMPP_REPEATS" clumpp/clumppparams.ssv);
vCLUMPP_PRINT_PERMUTED_DATA=$(grep -wi "CLUMPP_PRINT_PERMUTED_DATA" clumpp/clumppparams.ssv);
vCLUMPP_PRINT_EVERY_PERM=$(grep -wi "CLUMPP_PRINT_EVERY_PERM" clumpp/clumppparams.ssv);
vCLUMPP_PRINT_RANDOM_INPUTORDER=$(grep -wi "CLUMPP_PRINT_RANDOM_INPUTORDER" clumpp/clumppparams.ssv);

cmdSed="sed 's/CLUMPP_M/$vCLUMPP_M/gi' | sed 's/CLUMPP_W/$vCLUMPP_W/gi' | sed 's/CLUMPP_S/$vCLUMPP_S/gi' | sed 's/CLUMPP_GREEDY_OPTION/$vCLUMPP_GREEDY_OPTION/gi' | sed 's/CLUMPP_REPEATS/$vCLUMPP_REPEATS/gi' | sed 's/CLUMPP_PRINT_PERMUTED_DATA/$vCLUMPP_PRINT_PERMUTED_DATA/gi' | sed 's/CLUMPP_PRINT_EVERY_PERM/$vCLUMPP_PRINT_EVERY_PERM/gi' | sed 's/CLUMPP_PRINT_RANDOM_INPUTORDER/$vCLUMPP_PRINT_RANDOM_INPUTORDER/gi'";

cmd="cat $pathProc/clumpp.ind.params | $cmdSed | sed 's/CLUMPP_//gi' > clumpp/clumpp.ind.params";
eval $cmd;
cmd="cat $pathProc/clumpp.pop.params | $cmdSed | sed 's/CLUMPP_//gi' > clumpp/clumpp.pop.params";
eval $cmd;


kDesde=$(grep -wi 		"STRUCTURE_K_DESDE"  $archivoParametros | awk -F "\t" '{print $4}' | sed 's/\s//g');
kHasta=$(grep -wi 		"STRUCTURE_K_HASTA"  $archivoParametros | awk -F "\t" '{print $4}' | sed 's/\s//g');
cantReplicas=$(grep -wi "STRUCTURE_REPLICAS" $archivoParametros | awk -F "\t" '{print $4}' | sed 's/\s//g');
cantHilos=$(grep -wi 	"STRUCTURE_HILOS" 	 $archivoParametros | awk -F "\t" '{print $4}' | sed 's/\s//g');

cantCorridas=$((cantReplicas * (kHasta-kDesde+1)));

echo "$(date): structure (total number of runs: $cantCorridas)" >> $archivoLog;

numsRand=($(shuf -i 0-1000000 -n $cantCorridas));
cmd="";
for(( i=kDesde; i<=kHasta; i++ ))
do
	for(( j=1; j<=cantReplicas; j++ ))
	do
	 	result="../structure_out/K"$i"_"$j".out";

		if [ $genLogs == "1" ]
		then
			stout="../structure_log/K"$i"_"$j".log";
		else
			stout="/dev/null"
		fi

		D=${numsRand[$((j + cantReplicas*(i-kDesde) -1))]};
		cmd=$cmd"$pathProc/structure -i $archivoGenotipos -o $result -K $i -N $cantMuestras -L $cantLoci -D $D -m mainparams -e extraparams 1>$stout 2>&1 \n";
	done
done

cd structure;
echo -e $cmd > "cmdStructure.txt";

paramHilos="";
if [ $cantHilos != "0" ]
then
	paramHilos="-j $cantHilos";
fi

cmd="cat cmdStructure.txt | parallel --joblog parallel.log $paramHilos";
echo $cmd > "cmdParallel.txt";
eval $cmd;

cd ..;

echo "$(date): structureHarvester.py" >> $archivoLog;

cmd="python $pathProc/structureHarvester.py --dir=structure_out/ --out=structureHarvester --evanno --clumpp";
echo $cmd > "structureHarvester/cmd.txt";
eval $cmd;

cp structureHarvester/evanno.txt evanno.txt; 

echo "$(date): procesarEvanno.r" >> $archivoLog;

colorEvanno=$(grep -wi "STRUCTURE_COLOR_EVANNO_DELTA_K" $archivoParametros | awk -F "\t" '{print $4}' | sed 's/\s//g');
cmd="Rscript $pathProc/procesarEvanno.r evanno.txt evanno.deltaK.png evanno.mk.txt '$colorEvanno'";
echo $cmd > "structureHarvester/cmdProcesarEvanno.txt";
eval $cmd;

mejorK=$(cat evanno.mk.txt);

echo "$(date): CLUMPP" >> $archivoLog;

cd clumpp;

archivoInd="../structureHarvester/K$mejorK.indfile";
archivoPop="../structureHarvester/K$mejorK.popfile";

cmd="$pathProc/CLUMPP clumpp.ind.params -i $archivoInd -p $archivoPop -o clumpp.ind.out -j clumpp.ind.misc -k $mejorK -c $cantMuestras -r $cantReplicas 1>clumpp.ind.log 2>&1";
echo $cmd > "cmd.ind";
eval $cmd;

cmd="$pathProc/CLUMPP clumpp.pop.params -i $archivoInd -p $archivoPop -o clumpp.pop.out -j clumpp.pop.misc -k $mejorK -c $cantConjuntos -r $cantReplicas 1>clumpp.pop.log 2>&1";
echo $cmd > "cmd.pop";
eval $cmd;

echo "$(date): distruct" >> $archivoLog;

cd ../distruct;

archivoInd="../clumpp/clumpp.ind.out";
archivoPop="../clumpp/clumpp.pop.out";

cmd="$pathProc/distructLinux1.1 -p $archivoPop -i $archivoInd -N $cantMuestras -M $cantConjuntos -K $mejorK -o distruct.ind.ps -a -b detalleConjuntos.ssv -d drawparams.ind 1>distruct.ind.log 2>&1";
echo $cmd > "cmd.ind";
eval $cmd;

cmd="$pathProc/distructLinux1.1 -p $archivoPop -i $archivoInd -N $cantMuestras -M $cantConjuntos -K $mejorK -o distruct.pop.ps -a -b detalleConjuntos.ssv -d drawparams.pop 1>distruct.pop.log 2>&1";
echo $cmd > "cmd.pop";
eval $cmd;

cp distruct.ind.ps ../distruct.ind.ps;
cp distruct.pop.ps ../distruct.pop.ps;

cd ..;

echo "$(date): zip" >> $archivoLog;

mkdir "archivos_exp";
mv $archivoParametros archivos_exp/$archivoParametros;
mv $archivoCantidades archivos_exp/$archivoCantidades;
mv $archivoConjuntos archivos_exp/$archivoConjuntos;

cd archivos_exp
zip -q archivos_exp.zip *
mv archivos_exp.zip ../

cd ../structure
zip -q structure.zip *
mv structure.zip ../

cd ../structure_out
zip -q structure_out.zip *
mv structure_out.zip ../

cd ../structureHarvester
zip -q structureHarvester.zip *
mv structureHarvester.zip ../

cd ../clumpp
zip -q clumpp.zip *
mv clumpp.zip ../

cd ../distruct
zip -q distruct.zip *
mv distruct.zip ../

if [ $genLogs == "1" ]
then
	cd ../structure_log
	zip -q structure_log.zip *
	mv structure_log.zip ../
fi

cd ..
rm -rf structure
rm -rf structure_out
rm -rf structureHarvester
rm -rf clumpp
rm -rf distruct
rm -rf archivos_exp
rm -rf Rplots.pdf

if [ $genLogs == "1" ]
then
	rm -rf structure_log
fi

echo "$(date): Fin" >> $archivoLog;

