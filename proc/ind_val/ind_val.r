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

library(poppr)
library(ggplot2)
library(reshape2)
library(pegas)
library(vegan)
library(hierfstat)

log = function(msj){
	cat(format(Sys.time(), "%Y-%m-%d %H:%M:%S"),": ",msj,"\n",sep="")
}

getParam = function(dt,p,valor=NULL){
	
	param = dt[which(dt$parametro == p),]

	if(nrow(param) > 0){
		valor = param[1,"valor"]
		if(param[1,"tipo"] == "LOGICO")
			valor = "1" == valor
		else if(param[1,"tipo"] == "NUMERICO")
			valor = as.numeric(valor)
		else if(param[1,"tipo"] == "ENTERO")
			valor = as.integer(valor)
		else if(param[1,"tipo"] == "FECHA")
			valor = as.Date(valor)
	}else{
		log(paste(c("No se encontró el parámetro: ",p,", valor por defecto: ",valor),collapse=""))
	}

	return (valor)
}



args = commandArgs(trailingOnly=TRUE)

log("Inicio")

arcGenotipos = args[1]
arcParametros = args[2]

dtGenotipos = read.table(arcGenotipos,header=TRUE,stringsAsFactors = FALSE, sep='\t', comment.char="")
dtP = read.table(arcParametros,header=TRUE,stringsAsFactors = FALSE, sep='\t', comment.char="")

leyendaConjunto	= getParam(dtP,"IND_VAL_LEYENDA_CONJUNTO","detalle")

sepAlelos		= getParam(dtP,"EXP_PROC_SEP_ALELOS","/")
valorNulo		= getParam(dtP,"EXP_PROC_VALOR_NULO","-9")

tipoLoci		= getParam(dtP,"IND_VAL_TIPO_LOCI","codom") #codom, PA

lGenCurv		= getParam(dtP,"IND_VAL_GEN_CURV",TRUE)
genCurvSample	= getParam(dtP,"IND_VAL_GEN_CURV_SAMPLE",1000)

lPloidy			= getParam(dtP,"IND_VAL_PLOIDY",TRUE)
lMissing		= getParam(dtP,"IND_VAL_MISSING",TRUE)

lDiversity		= getParam(dtP,"IND_VAL_DIVERSITY",TRUE)
divSample		= getParam(dtP,"IND_VAL_DIVERSITY_SAMPLE",1000)
divIndex		= getParam(dtP,"IND_VAL_DIVERSITY_INDEX","rbarD")

lMLG			= getParam(dtP,"IND_VAL_MLG",TRUE)

lHW				= getParam(dtP,"IND_VAL_HW",TRUE)
hwStat			= getParam(dtP,"IND_VAL_HW_STAT","chi2") #chi2, MC
hwMCSample		= getParam(dtP,"IND_VAL_HW_STAT_MC_SAMPLE",1000)
hwAlfa			= getParam(dtP,"IND_VAL_HW_ALFA",0.05)

lLocus			= getParam(dtP,"IND_VAL_LOCUS_TABLE",TRUE)
locIndex		= getParam(dtP,"IND_VAL_LOCUS_TABLE_INDEX","simpson") #simpson, shannon, invsimpson
locLev			= getParam(dtP,"IND_VAL_LOCUS_TABLE_LEV","allele") #allele, genotype

lAlelos			= getParam(dtP,"IND_VAL_ALELOS_EJECUTAR",TRUE)
alNA_Method		= getParam(dtP,"IND_VAL_ALELOS_NA_METHOD","mean") #mean, zero
alFrec			= getParam(dtP,"IND_VAL_ALELOS_FREC",TRUE) 
alProm			= getParam(dtP,"IND_VAL_ALELOS_PROM",TRUE) 

lBasicSt		= getParam(dtP,"IND_VAL_BASIC_STATS",TRUE)
lAllRich		= getParam(dtP,"IND_VAL_ALLELIC_RICHNESS",TRUE)

lDistPop		= getParam(dtP,"IND_VAL_DIST_POP",TRUE)
dpMethod		= getParam(dtP,"IND_VAL_DIST_POP_METHOD",1)#1:Nei, 2:Edwards, 3:Reynolds, 4:Rogers, 5:Provesti

leyendaConjunto = tolower(leyendaConjunto)

popFact = NULL

if(leyendaConjunto == "detalle"){
	popFact = as.factor(dtGenotipos[,"conjunto_det"])
}else if(leyendaConjunto == "codigo"){
	popFact = as.factor(dtGenotipos[,"conjunto_cod"])
}else if(leyendaConjunto == "id"){
	popFact = as.factor(dtGenotipos[,"conjunto_id"])
}

colsLoci = colnames(dtGenotipos)[6:length(colnames(dtGenotipos))]
dtAlelos = dtGenotipos[,colsLoci]

colnames(dtAlelos) = colsLoci
rownames(dtAlelos) = dtGenotipos[,"muestra_id"]

objGI = df2genind(dtAlelos, pop = popFact, sep = sepAlelos, NA.char = valorNulo, type=tipoLoci)


if(lGenCurv){
	log("Curva de Genotipos")
	tGenCurve = genotype_curve(objGI, sample = genCurvSample, quiet = TRUE)

	p = last_plot()
	p + geom_smooth() +
		theme(text = element_text(size = 12, family = "serif"),
			  axis.text=element_text(size=18),
			  axis.title=element_text(size=20,face="bold")) +
		ggtitle("")+
		ggsave("genotype_curve.png")

	write.csv(tGenCurve, file="genotype_curve.csv")
}

if(lPloidy){
	log("Gráfico de ploidía")
	tPloidy = info_table(objGI, type = "ploidy", plot = TRUE, low = "black", high = "orange")
	p = last_plot()
	p + theme(axis.title=element_text(size=20,face="bold"),
			  axis.text.y=element_text(size=7)) +
		ggtitle("")+
		ggsave("ploidy.png")

	write.csv(tPloidy, file="ploidy.csv")
}

if(lMissing){
	log("Gráfico de alelos perdidos")
	tMissing=info_table(objGI, type = "missing", plot = TRUE)
	p = last_plot()
	p + theme(axis.title=element_text(size=20,face="bold")) +
		ggtitle("")+
		ggsave("missing.png")
	write.csv(tMissing, file="missing.csv")
}

if(lDiversity){
	log("Indices de diversidad")
	tDiversity = poppr(objGI, sample=divSample, index=divIndex)

	p = last_plot()
	p + theme(axis.title=element_text(size=20,face="bold"),
			  axis.text.x=element_text(size=8)) +
		ggtitle("")+
		ggsave("genotypic_diversity.png")

	dt = as.data.frame(tDiversity)
	dt[,"lambda_corrected"] = (dt$N/(dt$N - 1)) * dt$lambda  

	write.csv(dt, file="genotypic_diversity.csv")
}

if(lMLG){
	log("Tabla y gráficos de MLG")
	tMLG = mlg.table(objGI)
	p = last_plot()
	p + theme(axis.title=element_text(size=20,face="bold"),
			  axis.text.x=element_text(size=5),
			  axis.text.y=element_text(size=10)) +
		ggtitle("")+
		ggsave("mlg.png")

	write.csv(tMLG, file="mlg.csv")

	min_sample <- min(rowSums(tMLG))
	png(file="mlg_rarefaction.png", width = 8, height = 8, units = 'in', res = 300, bg = "transparent")
	rarecurve(tMLG, sample = min_sample, xlab = "Sample Size", ylab = "Expected MLGs")
	dev.off()
}

if(lHW){
	log("Hardy - Weinberg")
	tryCatch({

	   	tHW_Global = hw.test(objGI, B = hwMCSample)
		write.csv(tHW_Global, file="HW_Global.csv")

		tHW_Pop <- seppop(objGI) %>% lapply(hw.test, B = hwMCSample)
		dt = data.frame()
		for (name in names(tHW_Pop)) {
			dtAux = data.frame(tHW_Pop[[name]])
			dtAux[,"pop"]=rep(name,nrow(dtAux))
			dtAux[,"locus"]=rownames(dtAux)
			dt = rbind(dt,dtAux)
		}
		write.csv(dt, file="HW_Pop.csv", row.names=FALSE)

		if(hwStat == "chi2"){
			colSapply = 3
		}else{
			colSapply = 4
		}

		tHW_Pop.mat <- sapply(tHW_Pop, "[", i = TRUE, j = colSapply)

		tHW_Pop.mat.plot <- tHW_Pop.mat
		tHW_Pop.mat.plot[tHW_Pop.mat > alpha] <- paste(c("P-Value > ",hwAlfa),collapse="")
		tHW_Pop.mat.plot[tHW_Pop.mat <= alpha] <- paste(c("P-Value <= ",hwAlfa),collapse="")

		dt = melt(tHW_Pop.mat.plot)
		colnames(dt) = c("locus","pop","valor")

		ggplot(dt, aes(pop, locus, fill = factor(valor))) +
			geom_tile() +
			labs(fill = "")+
			xlab("Samples Set")+
			ylab("Locus")+
			theme(axis.text.x = element_text(hjust = 1, angle = 90),
				  axis.title=element_text(size=16,face="bold")) + 
		ggsave(paste(c("HW_",hwStat,".png"),collapse=""))

	},error=function(cond) {    
		message(cond)
		message("\nNOTA: La función hw.test del paquete pegas (v0.10) solo soporta loci diploides.")
	})
}


if(lLocus){
	log("Indices de diversidad por Loci")
	tLocus = locus_table(objGI, information=FALSE, index=locIndex, lev=locLev)
	write.csv(tLocus, file="locus_table_global.csv")

#	tLocus_Pop <- seppop(objGI) %>% lapply(locus_table, information=FALSE, index=locIndex, lev=locLev)
	dt = data.frame()
	listPop = seppop(objGI)
	for (name in names(listPop)) {
		tLocus_Pop = locus_table(listPop[[name]],information=FALSE, index=locIndex, lev=locLev)
		dtAux = data.frame(tLocus_Pop)
		dtAux[,"pop"]=rep(name,nrow(dtAux))
		dtAux[,"locus"]=rownames(dtAux)
		dt = rbind(dt,dtAux)
	}

	write.csv(dt, file="locus_table_pop.csv", row.names=FALSE)
}


if(lAlelos){
	log("Frecuencia y promedio de Alelos")
	dir.create("alelos")

	loci = locNames(objGI)
	objGILoc = seploc(objGI) 

	frecAlelosTot = NULL
	promAlelosTot = NULL

	for(i in 1:length(loci)){
		locus = loci[i]

		dt = as.data.frame(tab(objGILoc[[locus]], NA.method = alNA_Method))

		colsAlelos = 1:ncol(dt)
		dt[,"pop"] = pop(objGI)

		if(alFrec){

			frecAlelos = aggregate(dt[,colsAlelos], by=list(pop=dt$pop), FUN=sum)
			colsAlelosF = 2:ncol(frecAlelos)
			frecAlelos[,colsAlelosF] = frecAlelos[,colsAlelosF] / rowSums(frecAlelos[,colsAlelosF])

			frecAlelos <- melt(frecAlelos,id.var="pop")
			colnames(frecAlelos) = c("pop","alelo","frec")
			frecAlelos = frecAlelos[order(frecAlelos$alelo),]

			ggplot(frecAlelos, aes(x=pop,y=frec,group=alelo,colour=alelo)) +
				geom_point(size=3)+
				geom_line(linetype="dashed")+
				geom_text(aes(label=alelo),hjust=0, vjust=0,  size=5)+
				xlab("Samples Set")+
				ylab("Allele frequency")+
				theme(legend.position="none",
					axis.text.y=element_text(size=14),
					axis.text.x=element_text(size=12,hjust = 1, angle = 90),
					axis.title=element_text(size=16,face="bold"))+
				ggsave(paste(c("alelos/",locus,".frec.png"),collapse=""))	

			frecAlelos[,"locus"] = rep(locus,nrow(frecAlelos))
			if(is.null(frecAlelosTot)){
				frecAlelosTot = frecAlelos
			}else{
				frecAlelosTot = rbind(frecAlelosTot, frecAlelos)
			}
		}

		if(alProm){
			promAlelos = aggregate(dt[,colsAlelos], by=list(pop=dt$pop), FUN=mean)

			promAlelos <- melt(promAlelos,id.var="pop")
			colnames(promAlelos) = c("pop","alelo","prom")	
			promAlelos = promAlelos[order(promAlelos$alelo),]

			ggplot(promAlelos, aes(x=pop,y=prom,group=alelo,colour=alelo)) +
				geom_point(size=3)+
				geom_line(linetype="dashed")+
				geom_text(aes(label=alelo),hjust=0, vjust=0,  size=5)+
				xlab("Samples Set")+
				ylab("Average number")+
				theme(legend.position="none",
					axis.text.y=element_text(size=14),
					axis.text.x=element_text(size=12,hjust = 1, angle = 90),
					axis.title=element_text(size=16,face="bold"))+
				ggsave(paste(c("alelos/",locus,".prom.png"),collapse=""))	

			promAlelos[,"locus"] = rep(locus,nrow(promAlelos))
			if(is.null(promAlelosTot)){
				promAlelosTot = promAlelos
			}else{
				promAlelosTot = rbind(promAlelosTot, promAlelos)
			}
		}
	}
	if(alFrec){
		write.csv(frecAlelosTot, file="alelos/frec.csv", row.names=FALSE)
	}
	if(alProm){
		write.csv(promAlelosTot, file="alelos/prom.csv", row.names=FALSE)
	}
}

if(lDistPop){
	log("Distancia entre Conjuntos de Muestras")
	objGP = genind2genpop(objGI)

	disGen = dist.genpop(objGP, method=dpMethod)

	write.csv(as.matrix(disGen), file=paste(c("dist_pop_method_",dpMethod,".csv"),collapse=""))
}

if(lBasicSt){
	log("Tabla valores estadístico básicos")
	bs = basic.stats(genind2hierfstat(objGI))
	dfL = as.data.frame(bs$perloc)
	dfL[nrow(dfL)+1,] = bs$overall
	rownames(dfL)[nrow(dfL)] = "overall"
	write.csv(dfL, file="basic_stat.csv")
}

if(lAllRich){
	log("Tabla de riqueza alélica")
	alr = allelic.richness(genind2hierfstat(objGI))$Ar
	colnames(alr) = popNames(objGI)

	alro = allelic.richness(genind2hierfstat(objGI, pop=rep(1,nInd(objGI))))$Ar
	alr = cbind(alr,alro)
	colnames(alr)[ncol(alr)] = "overall" 

	write.csv(alr, file="allelic_richness.csv")
}

log("Fin")

