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


package org.tega.service.proc;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class ResultadoComando implements Serializable {

	private String stdOut = "";
	private String stdErr = "";

	public ResultadoComando(){}

	public ResultadoComando(String stdOut, String stdErr){
		this.stdOut = stdOut;
		this.stdErr = stdErr;
	}

	public String getStdOut(){return stdOut;}
	public String getStdErr(){return stdErr;}

	public void setStdOut(String stdOut){this.stdOut = stdOut;}
	public void setStdErr(String stdErr){this.stdErr = stdErr;}

 	@Override
	public String toString(){
		return "ResultadoComando{" +
            "stdOut='" + stdOut + "'"+
            ", stdErr='" + stdErr + "'}";
	}

}
