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
#	1: Archivo de la copia de seguridad. Debe tener la extensión: tar.gz.gpg. Ejemplo: backup_2018-01-02_19-07-52_sJl.tar.gz.gpg  
#	2: Clave para desencriptar el archivo. La clave indicada en el archivo: config/backup.config.sh
#	3: Host del motor de la base de datos (si es local: localhost)
#	4: Puerto para la conixión al motor de la base de datos (habitualmente 5432)
#	5: Usuario del motor de la base de datos
#	6: Clave del usuario del motor de la base de datos
#	7: Nombre de la base de datos (si existe se eliminará y se vlverá a crear)
#	8: Valor del parámetro ENCODING del comando CREATE DATABASE de PostgreSQL
#	9: Valor del parámetro LC_COLLATE del comando CREATE DATABASE de PostgreSQL
#	10: Valor del parámetro LC_CTYPE del comando CREATE DATABASE de PostgreSQL

archivoBackup=$1
claveEncrip=$2
hostDB=$3
portDB=$4
userDB=$5
passDB=$6
db=$7
encoding=$8
lcCollate=$9
lcCType=${10}

echo "$(date): Inicio"
echo "$(date): Desencriptando"

gpg --batch --yes --cipher-algo AES256 --passphrase=$claveEncrip $archivoBackup  

echo "$(date): Desencomprimiendo"

archivoGZ="${archivoBackup%.*}" 
tar -xzf $archivoGZ 

if [ -z "${hostDB//}" ]; then
    echo "$(date): No se indicaron los parámetros para importar la base de datos. Se finaliza la ejecución sin importar la base de datos."
	exit 0
fi

if [ ! -f "backup_db.sql" ]; then
    >&2 echo "$(date): ERROR: No se encontró el archivo backup_db.sql. Se finaliza la ejecución sin importar la base de datos."
	exit 1
fi

echo "$(date): Importando Base de Datos"

export PGPASSWORD=$passDB
psql -h $hostDB -p $portDB -U $userDB -c "DROP DATABASE IF EXISTS $db;"
psql -h $hostDB -p $portDB -U $userDB -c "CREATE DATABASE $db ENCODING '$encoding' LC_COLLATE '$lcCollate' LC_CTYPE '$lcCType';"
grep -vE "CREATE EXTENSION|COMMENT ON EXTENSION" backup_db.sql | psql -q -1 -h $hostDB -p $portDB -U $userDB --set ON_ERROR_STOP=on $db
unset PGPASSWORD

echo "$(date): Fin"
