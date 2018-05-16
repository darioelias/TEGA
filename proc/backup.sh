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


#Ejemplo para agregar la ejecución en cron:
#	en el archivo /etc/crontab agregar la linea (con sudo o usuario root): 
# 	03 * * * * usuario /home/usuario/tega/proc/backup/backup.sh CRON /home/usuario/tega/config/backup.config.sh
#	(se ejecuta el minuto 3 de todas las horas de todos los dias)
# 	0 0 1 * * usuario /home/usuario/tega/proc/backup/backup.sh CRON /home/usuario/tega/config/backup.config.sh
#	(se ejecuta una vez al mes, a las 00:00 del día 1)
#	despues de modificar el archivo reiniciar cron: /etc/init.d/cron restart


modoEjecucion=$1
#Modos de ejecución:
#	MANUAL: ejecución desde TEGA iniciada por el usuario
#	CRON: ejecución desde CRON

archivoConfig=$2
#Variables declaradas en el archivo de configuración:
	
#Datos de conexión a PostgreSQL
#	host=
#	port=
#	db=
#	user=
#	passDB=

#Clave para la encriptación (gpg)
#	passEncrip=

#Cantidad máxima de backups
#	cantBackupsMax=

#Directorios:
#	Dirección absoluta a la carpeta de TEGA
#	dirTEGA=
#	Dirección absoluta a la carpeta donde se alojará la copia de seguridad
#	dirBackups=
#	Carpeta para almacenar los archivos temporales
#	dirTmp=
#	Carpeta para almacenar los logs
#	dirLogs=

#Archivos de logs:
#	archivoLogEjecucion=
#	archivoSTDERR=


if [ ! -f "$archivoConfig" ]; then
    echo "No se encontró el archivo de configuración: $archivoConfig"
	exit 1
fi

aux=$(sed '/^\s*#/ d' $archivoConfig | sed "s/\s*=\s*/=/gi")
eval $aux

mkdir -p $dirLogs
mkdir -p $dirBackups
mkdir -p $dirTmp

archivoSTDERRLocal=$(mktemp "$dirTmp/backup_XXXXX.err")
archivoLogEjecucionLocal=$(mktemp "$dirTmp/backup_XXXXX.log")

#Por el momento si la ejecución es manual no se almacenan los errores en el
#archivo de log, son enviados al STDERR y luego se visualizan en la pantalla
#de backups de TEGA
if [ $modoEjecucion != "MANUAL" ] 
then
	exec 1<&-
	exec 2<&-
	exec 1>>$archivoLogEjecucionLocal
	exec 2>>$archivoSTDERRLocal
fi

fInicio=$(date)
echo "$fInicio: Inicio Ejecución (modo: $modoEjecucion)" >> $archivoLogEjecucionLocal

carpetaTmp=$(mktemp -d $dirTmp"/XXXXXX")

fecha=$(date +"%Y-%m-%d_%H-%M-%S")
archivoDestino=$dirBackups/backup_$fecha
archivoDestino=$(mktemp $archivoDestino"_XXX.tar.gz.gpg")

echo "$(date): Archivo destino: $archivoDestino" >> $archivoLogEjecucionLocal

echo "$(date): Inicio: rsync" >> $archivoLogEjecucionLocal
archivoExcl=$(mktemp "$dirTmp/backup.excl.XXXXX")
carpetaTEGA=$(basename $dirTEGA)
carpetaBackups=$(basename $dirBackups)

echo "$carpetaTEGA/$carpetaBackups" > $archivoExcl
rsync -a --exclude-from $archivoExcl $dirTEGA $carpetaTmp/
echo "$(date): Fin" >> $archivoLogEjecucionLocal

echo "$(date): Inicio: pg_dump" >> $archivoLogEjecucionLocal
export PGPASSWORD=$passDB
pg_dump -h $host -p $port -U $user $db > $carpetaTmp"/backup_db.sql"
unset PGPASSWORD
echo "$(date): Inicio" >> $archivoLogEjecucionLocal

echo "$(date): Inicio: tar -czk * | gpg ..." >> $archivoLogEjecucionLocal
cd $carpetaTmp
tar -czk * | gpg --batch --yes --cipher-algo AES256 --passphrase=$passEncrip -c -o $archivoDestino 
echo "$(date): Fin" >> $archivoLogEjecucionLocal

echo "$(date): Inicio: eliminación de archivos temporales" >> $archivoLogEjecucionLocal
cd $dirBackups
ls -1tr | grep "backup_.*.tar.gz.gpg" | head -n -$cantBackupsMax | xargs -d '\n' rm -f --
cd $dirTmp
rm -rf $carpetaTmp
echo "$(date): Fin" >> $archivoLogEjecucionLocal

echo "$(date): Fin Ejecución" >> $archivoLogEjecucionLocal 

if [ $modoEjecucion != "CRON" ] 
then
	echo "$(cat $archivoLogEjecucionLocal)"
fi

echo "$(date): Fin" >> $archivoSTDERRLocal

echo -e "$(cat $archivoLogEjecucionLocal)\n" >> $archivoLogEjecucion
echo -e "$fInicio: Inicio\n$(cat $archivoSTDERRLocal)\n" >> $archivoSTDERR

rm -rf $dirTmp/*
