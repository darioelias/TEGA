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


package org.tega.config.locale;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.TimeZoneAwareLocaleContext;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.util.WebUtils;

import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.*;

/**
 * Angular cookie saved the locale with a double quote (%22en%22).
 * So the default CookieLocaleResolver#StringUtils.parseLocaleString(localePart)
 * is not able to parse the locale.
 *
 * This class will check if a double quote has been added, if so it will remove it.
 */
public class AngularCookieLocaleResolver extends CookieLocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        parseLocaleCookieIfNecessary(request);
        return (Locale) request.getAttribute(LOCALE_REQUEST_ATTRIBUTE_NAME);
    }

    @Override
    public LocaleContext resolveLocaleContext(final HttpServletRequest request) {
        parseLocaleCookieIfNecessary(request);
        return new TimeZoneAwareLocaleContext() {
            @Override
            public Locale getLocale() {
                return (Locale) request.getAttribute(LOCALE_REQUEST_ATTRIBUTE_NAME);
            }

            @Override
            public TimeZone getTimeZone() {
                return (TimeZone) request.getAttribute(TIME_ZONE_REQUEST_ATTRIBUTE_NAME);
            }
        };
    }

    @Override
    public void addCookie(HttpServletResponse response, String cookieValue) {
        // Mandatory cookie modification for AngularJS to support the locale switching on the server side.
        super.addCookie(response, "%22" + cookieValue + "%22");
    }

    private void parseLocaleCookieIfNecessary(HttpServletRequest request) {
        if (request.getAttribute(LOCALE_REQUEST_ATTRIBUTE_NAME) == null) {
            // Retrieve and parse cookie value.
            Cookie cookie = WebUtils.getCookie(request, getCookieName());
            Locale locale = null;
            TimeZone timeZone = null;
            if (cookie != null) {
                String value = cookie.getValue();

                // Remove the double quote
                value = StringUtils.replace(value, "%22", "");

                String localePart = value;
                String timeZonePart = null;
                int spaceIndex = localePart.indexOf(' ');
                if (spaceIndex != -1) {
                    localePart = value.substring(0, spaceIndex);
                    timeZonePart = value.substring(spaceIndex + 1);
                }
                locale = (!"-".equals(localePart) ? StringUtils.parseLocaleString(localePart.replace('-', '_')) : null);
                if (timeZonePart != null) {
                    timeZone = StringUtils.parseTimeZoneString(timeZonePart);
                }
                if (logger.isTraceEnabled()) {
                    logger.trace("Parsed cookie value [" + cookie.getValue() + "] into locale '" + locale +
                        "'" + (timeZone != null ? " and time zone '" + timeZone.getID() + "'" : ""));
                }
            }
            request.setAttribute(LOCALE_REQUEST_ATTRIBUTE_NAME,
                (locale != null ? locale: determineDefaultLocale(request)));

            request.setAttribute(TIME_ZONE_REQUEST_ATTRIBUTE_NAME,
                (timeZone != null ? timeZone : determineDefaultTimeZone(request)));
        }
    }
}
