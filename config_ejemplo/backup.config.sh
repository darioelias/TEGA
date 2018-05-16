#Archivo de configuración del procedimiento de backup ([DIR_TEGA]/proc/backup.sh)

#Datos de conexión a PosgreSQL
host=localhost
port=5432
db=tegadb
user=usuario_psql
passDB=clave_psql

#Clave para la encriptación (gpg)
passEncrip=clave_encriptacion

#Directorios:
#	En los paths no usar ~, siempre usar el path absoluto.

#	Dirección absoluta a la carpeta de TEGA
dirTEGA=/home/usuario/tega
#	Dirección absoluta a la carpeta donde se alojará la copia de seguridad
#	Esta carpeta es excluida del backup
dirBackups=$dirTEGA/backups
#	Dirección absoluta a la carpeta para almacenar los archivos temporales
dirTmp=$dirBackups/tmp
#	Dirección absoluta a la carpeta para almacenar los logs
dirLogs=$dirTEGA/logs

#Cantidad máxima de backups a mantener en la carpeta de backups
cantBackupsMax=12

#Archivos de logs:
archivoLogEjecucion=$dirLogs/backup.log
archivoSTDERR=$dirLogs/backup.err
