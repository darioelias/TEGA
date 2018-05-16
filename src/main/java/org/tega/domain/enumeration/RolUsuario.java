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


package org.tega.domain.enumeration;

import org.tega.domain.Authority;
import org.tega.security.AuthoritiesConstants;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;


public enum RolUsuario {
    IMPLEMENTADOR(){
		public Set<Authority> getAuthorities(){
			return new HashSet<Authority>(Arrays.asList(new Authority(AuthoritiesConstants.ADMIN),
													 	new Authority(AuthoritiesConstants.USER),
													 	new Authority(AuthoritiesConstants.USER_EXTENDED),
													 	new Authority(AuthoritiesConstants.ABM),
													 	new Authority(AuthoritiesConstants.MANAGER)
														));
		}

	},
	ADMINISTRADOR(){
		public Set<Authority> getAuthorities(){
			return new HashSet<Authority>(Arrays.asList(new Authority(AuthoritiesConstants.USER),
													 	new Authority(AuthoritiesConstants.USER_EXTENDED),
													 	new Authority(AuthoritiesConstants.ABM),
													 	new Authority(AuthoritiesConstants.MANAGER)
														));
		}

	},
	INVESTIGADOR(){
		public Set<Authority> getAuthorities(){
			return new HashSet<Authority>(Arrays.asList(new Authority(AuthoritiesConstants.USER),
													 	new Authority(AuthoritiesConstants.USER_EXTENDED),
													 	new Authority(AuthoritiesConstants.ABM)
														));
		}

	},
	INVITADO(){
		public Set<Authority> getAuthorities(){
			return new HashSet<Authority>(Arrays.asList(new Authority(AuthoritiesConstants.USER),
													 	new Authority(AuthoritiesConstants.USER_EXTENDED)
														));
		}

	},
	ANONIMO(){
		public Set<Authority> getAuthorities(){
			return new HashSet<Authority>(Arrays.asList(new Authority(AuthoritiesConstants.USER)
														));
		}

	};


	public abstract Set<Authority> getAuthorities();
}
