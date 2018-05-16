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

library(ggplot2)

args = commandArgs(trailingOnly=TRUE)

arcEvanno 	 = args[1]
arcSalidaPNG = args[2]
arcSalidaMK  = args[3]
color 		 = args[4]

if(is.na(color))
	color = "black"

tabla = read.table(arcEvanno,header=FALSE,stringsAsFactors = FALSE, sep='\t')
colnames(tabla) = c("K","Reps","Mean_LnP","Stdev_LnP","Ln","Ln_abs","Delta_K")

mDelta_K = max(tabla$Delta_K, na.rm=TRUE)
write(tabla[which(tabla$Delta_K == mDelta_K),"K"][1],arcSalidaMK)


oldw = getOption("warn")
options(warn = -1)

if(max(tabla$K)-min(tabla$K) > 10){
	limX = seq(min(tabla$K),max(tabla$K), ceiling((max(tabla$K)-min(tabla$K))/10))
}else{
	limX = min(tabla$K):max(tabla$K)
}

ggplot(tabla, aes(x=K,y=Delta_K)) +
	geom_point(size=4,colour=color)+
	geom_line(colour=color)+
	scale_x_discrete(limits=limX) +
	scale_y_continuous(name = "Delta K")+
	theme(legend.position="none",
		axis.text=element_text(size=25),
		axis.title=element_text(size=28,face="bold"))+
	ggsave(file=arcSalidaPNG)

options(warn = oldw)
