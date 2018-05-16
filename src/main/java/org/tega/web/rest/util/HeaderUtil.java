/**
 *  This file is part of TEGA (Tools for Evolutionary and Genetic Analysis)
 *  TEGA website: https://github.com/darioelias/TEGA
 *
 *  Copyright (C) 2018 Dario E. Elias & Eva C. Rueda
 *
 *  TEGA is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  TEGA is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *  
 *  Additional permission under GNU AGPL version 3 section 7
 *  
 *  If you modify TEGA, or any covered work, by linking or combining it with
 *  STRUCTURE, DISTRUCT or CLUMPP (or a modified version of those programs),
 *  the licensors of TEGA grant you additional permission to convey the resulting work.
 */


/**
 * @author Dario E. Elias
 * @version 1.0 
 */


package org.tega.web.rest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

/**
 * Utility class for HTTP headers creation.
 */
public final class HeaderUtil {

    private static final Logger log = LoggerFactory.getLogger(HeaderUtil.class);

    private HeaderUtil() {
    }

    public static HttpHeaders createAlert(String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-proyectoApp-alert", message);
        headers.add("X-proyectoApp-params", param);
        return headers;
    }

    public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
        return createAlert("proyectoApp." + entityName + ".created", param);
    }

    public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
        return createAlert("proyectoApp." + entityName + ".updated", param);
    }

    public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
        return createAlert("proyectoApp." + entityName + ".deleted", param);
    }

    public static HttpHeaders createFailureAlert(String entityName, String errorKey, String defaultMessage) {
        log.error("Entity creation failed, {}", defaultMessage);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-proyectoApp-error", "error." + errorKey);
        headers.add("X-proyectoApp-params", entityName);
        return headers;
    }

    public static HttpHeaders importFailureAlert(String entityName, String errorKey, String defaultMessage) {
        log.error("Import failed, {}", defaultMessage);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-proyectoApp-error", "error." + errorKey);
        headers.add("X-proyectoApp-params", entityName);
        headers.add("X-proyectoApp-error-msj", defaultMessage);
        return headers;
    }

    public static HttpHeaders codigoRepetido() {
       // log.error("Entity creation failed, código repetido");
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-proyectoApp-error", "error.codigo-repetido");
        return headers;
    }
    public static HttpHeaders errorModelo(String codigo) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-proyectoApp-error", codigo);
        return headers;
    }
    public static HttpHeaders errorUploadArchivo() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-proyectoApp-error", "error.upload-archivo");
        return headers;
    }

    public static HttpHeaders errorDownloadArchivo() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-proyectoApp-error", "error.download-archivo");
        return headers;
    }

    public static HttpHeaders createEntityDeletionByIdInAlert(String entityName, Integer param) {
        return createAlert("proyectoApp." + entityName + ".deleteByIdIn", param.toString());
    }
    public static HttpHeaders errorBackup(String msj) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-proyectoApp-error", "backup.error");
        headers.add("X-proyectoApp-error-msj", msj);
        return headers;
    }

    public static HttpHeaders errorPrivacidad() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-proyectoApp-error", "error.privacidad");
        return headers;
    }

    public static HttpHeaders error(String msj) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-proyectoApp-error", msj);
        return headers;
    }
}
