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

library(adegenet)
library(ggplot2)
library(reshape2)

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

archivoResultados = "resultados.csv"

dtGenotipos = read.table(arcGenotipos,header=TRUE,stringsAsFactors = FALSE, sep='\t', comment.char="")
dtP = read.table(arcParametros,header=TRUE,stringsAsFactors = FALSE, sep='\t', comment.char="")


leyendaConjunto	= getParam(dtP,"DAPC_LEYENDA_CONJUNTO","detalle")

sepAlelos		= getParam(dtP,"EXP_PROC_SEP_ALELOS","/")
valorNulo		= getParam(dtP,"EXP_PROC_VALOR_NULO","-9")

tipoLoci		= getParam(dtP,"DAPC_TIPO_LOCI","codom") #codom, PA
scale 			= getParam(dtP,"DAPC_SCALE",FALSE)

fcEje	 		= getParam(dtP,"DAPC_FC_EJECUTAR",TRUE)
fcPCA 			= getParam(dtP,"DAPC_FC_N_PCA",0)
fcPercPCA 		= getParam(dtP,"DAPC_FC_PERC_PCA",0.95)
fcMethod 		= getParam(dtP,"DAPC_FC_METHOD","kmeans") #kmeans, ward
fcStat	 		= getParam(dtP,"DAPC_FC_STAT","BIC") #BIC, AIC, WSS
fcCriterion		= getParam(dtP,"DAPC_FC_CRITERION","diffNgroup") #diffNgroup, min, goesup, smoothNgoesup, goodfit
fcIter	 		= getParam(dtP,"DAPC_FC_N_ITER",1e5)
fcStart	 		= getParam(dtP,"DAPC_FC_N_STAR",10)
fcColBase 		= getParam(dtP,"DAPC_FC_PLOT_COL_BASE","#0099FF")
fcColSel 		= getParam(dtP,"DAPC_FC_PLOT_COL_SEL","red")
fcKMax			= getParam(dtP,"DAPC_FC_K_MAX",0)
fcK				= getParam(dtP,"DAPC_FC_K",0)
fcXLen			= getParam(dtP,"DAPC_FC_PLOT_XLEN",18)
fcYLen			= getParam(dtP,"DAPC_FC_PLOT_YLEN",18)

cvCantHilos 	= getParam(dtP,"DAPC_CV_NCPUS",0)
cvNA_method 	= getParam(dtP,"DAPC_CV_NA_METHOD","mean") #mean, zero
cvRep 			= getParam(dtP,"DAPC_CV_REP",10)
cvExp	 		= getParam(dtP,"DAPC_CV_EXP",5)
cvTS 			= getParam(dtP,"DAPC_CV_TRAINING_SET",0.9)
cvResult		= getParam(dtP,"DAPC_CV_RESULT","groupMean") #groupMean, overall

spLegend 		= getParam(dtP,"DAPC_SP_LEGEND",TRUE)
spClabel 		= getParam(dtP,"DAPC_SP_CLABEL",FALSE)
spScree.pca 	= getParam(dtP,"DAPC_SP_SCREE_PCA",TRUE)
spScree.da 		= getParam(dtP,"DAPC_SP_SCREE_DA",TRUE)
spPosi.leg 		= getParam(dtP,"DAPC_SP_POSI_LEG","bottomleft") #bottomleft, bottomright, topleft, topright
spPosi.pca 		= getParam(dtP,"DAPC_SP_POSI_PCA","topleft") #bottomleft, bottomright, topleft, topright
spPosi.da 		= getParam(dtP,"DAPC_SP_POSI_DA","bottomright") #bottomleft, bottomright, topleft, topright
spCleg 			= getParam(dtP,"DAPC_SP_POSI_CLEG",1)
sppImpMuestras	= getParam(dtP,"DAPC_SP_P_IND_IMP_MUESTRAS",0)

sppIndXLen		= getParam(dtP,"DAPC_SP_P_IND_XLEN",7)
sppIndYLen		= getParam(dtP,"DAPC_SP_P_IND_YLEN",12)
sppIndTLen		= getParam(dtP,"DAPC_SP_P_IND_TLEN",15)
sppIndNCol		= getParam(dtP,"DAPC_SP_P_IND_NCOL",1)

sppBoxXLen		= getParam(dtP,"DAPC_SP_P_BOX_XLEN",12)
sppBoxYLen		= getParam(dtP,"DAPC_SP_P_BOX_YLEN",12)
sppBoxTLen		= getParam(dtP,"DAPC_SP_P_BOX_TLEN",15)
sppBoxNCol		= getParam(dtP,"DAPC_SP_P_BOX_NCOL",1)

umbLoadingPlot 	= getParam(dtP,"DAPC_LP_UMBRAL",0.95)

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

grupos = NULL

if(fcEje){
	log("Determinación de K")

	if(fcPCA == 0 && fcPercPCA == 0){
		log("ERROR: Uno de los parámetros DAPC_FC_N_PCA y DAPC_FC_PERC_PCA debe ser distinto de 0")
		q()
	}else if(fcPCA != 0 && fcPercPCA != 0){
		log("ERROR: Uno de los parámetros DAPC_FC_N_PCA y DAPC_FC_PERC_PCA debe ser igual a 0")
		q()
	}

	dir.create("find_clusters")

	x <- scaleGen(objGI, center = TRUE, scale = scale,NA.method = "mean")
	maxRank <- min(dim(x))
	pcaX <- dudi.pca(x, center = TRUE, scale = scale, scannf = FALSE, nf=maxRank)
	cumVar <- 100 * cumsum(pcaX$eig)/sum(pcaX$eig)

	pcaSel = fcPCA
	if(fcPercPCA > 0){
		pcaSel = min(which(cumVar >= fcPercPCA))
		if(fcPercPCA > 99.999) pcaSel = length(pcaX$eig)
	}
	if(pcaSel<1) pcaSel = 1
	if(pcaSel>length(pcaX$eig)) pcaSel = length(pcaX$eig)
	varSel = cumVar[pcaSel]

	write.table(list("FC: Number of retained PCs",pcaSel),file=archivoResultados, append=TRUE, sep=",", row.names=FALSE,col.names=FALSE)
	write.table(list("FC: Cumulative variance (%)",varSel),file=archivoResultados, append=TRUE, sep=",", row.names=FALSE,col.names=FALSE)

	dt = as.data.frame(cumVar)
	dt$PCA = 1:length(cumVar)
	dt$col = rep(fcColBase,length(cumVar))
	dt[pcaSel,"col"] = fcColSel

	write.csv(dt[,c("PCA","cumVar")], file="find_clusters/pc_var.csv", row.names=FALSE)

	ggplot(dt, aes(x=PCA,y=cumVar)) +
		geom_point(size=3, col=dt$col)+
		geom_segment(aes(x = pcaSel, y = 0, xend = pcaSel, yend = varSel), color = fcColSel,linetype="dashed",size=1)+
		geom_segment(aes(x = 0, y = varSel, xend = pcaSel, yend = varSel), color = fcColSel,linetype="dashed",size=1)+
		ylab("Cumulative variance (%)")+
		xlab("Number of retained PCs")+
		theme(legend.position="none",
			axis.text=element_text(size=18),
			axis.title=element_text(size=20,face="bold"))+
		ggsave(file="find_clusters/pc_var.png")

	if(fcK == 0)
		fcK = NULL

	clust = NULL
	if(fcKMax > 0){
		clust = find.clusters(objGI, n.pca = pcaSel, choose.n.clust = FALSE, method=fcMethod, max.n.clust=fcKMax,
							  stat=fcStat, criterion=fcCriterion, n.iter=fcIter, n.start=fcStart, scale=scale, n.clust=fcK)
	}else{
		clust = find.clusters(objGI, n.pca = pcaSel, choose.n.clust = FALSE, method=fcMethod, 
							  stat=fcStat, criterion=fcCriterion, n.iter=fcIter, n.start=fcStart, scale=scale, n.clust=fcK)		
	}
	grupos = clust$grp
	kMax = length(clust$Kstat)
	kSel = length(unique(clust$grp))

	write.table(list("FC: Selected K",kSel),file=archivoResultados, append=TRUE, sep=",", row.names=FALSE,col.names=FALSE)

	if(is.null(fcK)){
		dt = as.data.frame(clust$Kstat)
		colnames(dt) = "Kstat"
		dt$K = 1:kMax
		dt$col = rep(fcColBase,kMax)
		dt[kSel,"col"] = fcColSel

		write.csv(dt[,c("K","Kstat")], file="find_clusters/k_stat.csv", row.names=FALSE)
		
		if(max(dt$K)-min(dt$K) > 10)
			limX = seq(min(dt$K),max(dt$K), ceiling((max(dt$K)-min(dt$K))/10))
		else
			limX = min(dt$K):max(dt$K)

		ggplot(dt, aes(x=K,y=Kstat)) +
			geom_line(colour=fcColBase)+
			geom_point(size=4,col=dt$col)+
			scale_y_continuous(name = fcStat)+
			scale_x_discrete(limits=limX) +
			theme(legend.position="none",
				axis.text.y=element_text(size=fcYLen),
				axis.text.x=element_text(size=fcXLen),
				axis.title=element_text(size=20,face="bold"))+
			ggsave(file="find_clusters/k_stat.png")
	}

}else{
	grupos = pop(objGI)
}

log("Determinación de PCs (1)")

dir.create("cv_dapc")

png(file="cv_dapc/cv1.png", width = 8, height = 8, units = 'in', res = 300, bg = "transparent")
oCV = xvalDapc(tab(objGI, NA.method = cvNA_method), grupos,
				  scale=scale, training.set=cvTS, result=cvResult, n.rep = cvRep,
				  parallel = "multicore", ncpus = cvCantHilos)
dev.off()

mPCA = as.integer(oCV$`Number of PCs Achieving Lowest MSE`)

write.csv(oCV$`Cross-Validation Results`, file="cv_dapc/cv1.cvr.csv")
write.csv(oCV$`Root Mean Squared Error by Number of PCs of PCA`, file="cv_dapc/cv1.rmsenpp.csv")
write.table(list("CV 1: Number of PCs Achieving Lowest MSE",mPCA),file=archivoResultados, sep=",", append=TRUE, row.names=FALSE,, col.names=FALSE)

log("Determinación de PCs (2)")

seqPca = seq(max(1,mPCA-cvExp), mPCA+cvExp, 1)

png(file="cv_dapc/cv2.png", width = 8, height = 8, units = 'in', res = 300, bg = "transparent")
oCV = xvalDapc(tab(objGI, NA.method = cvNA_method), grupos, 
				  scale=scale, training.set=cvTS, result=cvResult, n.rep = cvRep, n.pca = seqPca,
				  parallel = "multicore", ncpus = cvCantHilos)
dev.off()

n.pca = as.integer(oCV$`Number of PCs Achieving Lowest MSE`)
rms = oCV$`Root Mean Squared Error by Number of PCs of PCA`

write.csv(oCV$`Cross-Validation Results`, file="cv_dapc/cv2.cvr.csv")
write.csv(rms, file="cv_dapc/cv2.rmsenpp.csv")
write.table(list("CV 2: Number of PCs Achieving Lowest MSE",n.pca),file=archivoResultados, sep=",", append=TRUE, row.names=FALSE,col.names=FALSE)

rmsV = as.vector(rms)
write.table(list("CV 2: MSE of PC Sel.",rms[[oCV$`Number of PCs Achieving Lowest MSE`]]),file=archivoResultados, sep=",", append=TRUE, row.names=FALSE,col.names=FALSE)

log("Generación de gráficos")

dir.create("dapc")

oDapc = oCV$DAPC

colores = rainbow(length(unique(oDapc$grp)), s=0.5, v=0.8)

write.table(list("DAPC: Proportion of conserved variance",oDapc$var),file=archivoResultados, sep=",", append=TRUE, row.names=FALSE, col.names=FALSE)

#write.csv(oDapc$tab, 		file="dapc/rpcpca.csv", row.names=FALSE)
#write.csv(oDapc$means, 		file="dapc/gm.csv", row.names=FALSE)
#write.csv(oDapc$loadings, 	file="dapc/loadings.csv", row.names=FALSE)
#write.csv(oDapc$ind.coord, 	file="dapc/ind.coord.csv", row.names=FALSE)
#write.csv(oDapc$grp.coord, 	file="dapc/grp.coord.csv", row.names=FALSE)
#write.csv(oDapc$pca.loadings,file="dapc/pca.loadings.csv", row.names=FALSE)
write.csv(oDapc$eig, 			file="dapc/da.eig.csv")
write.csv(oDapc$pca.eig, 		file="dapc/pca.eig.csv")
write.csv(oDapc$var.contr, 	file="dapc/var.contr.csv")
write.csv(oDapc$posterior, 	file="dapc/posterior.csv")

png(file="dapc/pca_scatter.png", width = 8, height = 8, units = 'in', res = 300, bg = "transparent")
scatter(oDapc, cex = 2, col=colores,
        clabel = spClabel, 
		legend = spLegend, posi.leg = spPosi.leg, 
		scree.pca = spScree.pca, posi.pca = spPosi.pca, 
		scree.da = spScree.da, posi.da = spPosi.da,
		cleg = spCleg, solid = 0.85)
dev.off()


dt = as.data.frame(oDapc$posterior)
dt$pop = pop(objGI)
dt$indNames = rownames(dt)
dt = melt(dt, id.vars=c("pop", "indNames"))
colnames(dt) = c("pop_origen","muestra_id","pop_asignada","probabilidad") 

write.csv(dt, file="dapc/pmp.csv", row.names=FALSE)


estiloX = element_text(size=sppBoxXLen)
if(!fcEje)
	estiloX = element_text(angle = 90, hjust=1, size=sppBoxXLen)

ggplot(dt) +
  	geom_boxplot(aes(x=pop_asignada, y=probabilidad, fill=pop_asignada))+
	scale_fill_manual(values=colores)+
	scale_x_discrete(name = "DAPC assignment") +
    scale_y_continuous(name = "Posterior membership probability")+
	facet_wrap(~pop_origen, scales="free", ncol=sppBoxNCol)+
	theme(axis.text.y=element_text(size=sppBoxYLen),
		  axis.text.x=estiloX,
		  axis.title=element_text(size=17,face="bold"), 
		  text = element_text(size = sppBoxTLen),
		  legend.position = "none")+
	ggsave(file="dapc/pmp_boxplot_assig.png")


#ggplot(dt) +
#  	geom_boxplot(aes(x=pop_origen, y=probabilidad, fill=pop_asignada))+
#	scale_fill_manual(values=colores)+
#	scale_x_discrete(name = "Samples Set") +
#    scale_y_continuous(name = "Posterior membership probability")+
#	facet_grid(~pop_asignada, scales="free")+
#	theme(axis.text=element_text(size=15),
#		  axis.title=element_text(size=17,face="bold"), 
#		  text = element_text(size = 15),
#		  legend.position = "none")+
#	ggsave(file="dapc/pmp_boxplot_ss.png")



estiloX = element_blank()
if(sppImpMuestras)
	estiloX = element_text(angle = 90, hjust=1, size=sppIndXLen)

ggplot(dt,aes(x=muestra_id, y=probabilidad, fill=pop_asignada)) +
	geom_bar(stat="identity")+
	scale_fill_manual(values=colores)+
	scale_x_discrete(name = "Sample") +
    scale_y_continuous(name = "Posterior membership probability")+
	labs(fill = "DAPC assignment")+
	facet_wrap(~pop_origen, scales="free", ncol=sppIndNCol)+
	theme(axis.text.y=element_text(size=sppIndYLen),
		  axis.text.x=estiloX,
		  axis.title=element_text(size=17,face="bold"), 
		  text = element_text(size = sppIndTLen),
		  legend.title = element_text(size=sppIndTLen, face="bold"),
		  legend.position = "bottom")+
	ggsave(file="dapc/pmp_indplot.png")

cantCol = ncol(oDapc$var.contr)
for(i in 1:cantCol){
	umbral = quantile(oDapc$var.contr[,i],probs = umbLoadingPlot)
	png(file=paste(c("dapc/loadingplot_",i,".png"), collapse=""), width = 8, height = 8, units = 'in', res = 300, bg = "transparent")
	loadingplot(oDapc$var.contr, axis = i, thres = umbral, ylab="", xlab="", main="", cex.lab=1.2, cex.axis=1.2)
	mtext("Contribution", side=2, line=2.2, cex=1.5)
	mtext("Alleles", side=1, line=2.2, cex=1.5)
	dev.off()
}

log("Fin")
