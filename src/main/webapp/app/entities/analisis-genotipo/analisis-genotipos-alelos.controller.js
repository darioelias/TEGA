(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('AnalisisGenotiposAlelos', AnalisisGenotiposAlelos)
        .controller('AnalisisGenotiposAlelosController', AnalisisGenotiposAlelosController);

    AnalisisGenotiposAlelosController.$inject = ['$timeout', '$scope', '$stateParams', 'AlertService', 
												 '$uibModalInstance', 'entity', 'AnalisisGenotipo', 
												 'Locus', 'ConjuntoMuestras','Alelo', '$uibModal', 
												 'Muestra','$q','Principal', 'AnalisisGenotipoAlelosImportar'];

    function AnalisisGenotiposAlelosController ($timeout, $scope, $stateParams, AlertService,
												$uibModalInstance, entity, AnalisisGenotipo, 
												Locus, ConjuntoMuestras, Alelo, $uibModal, 
												Muestra, $q, Principal, AnalisisGenotipoAlelosImportar) {
        var vm = this;

        vm.analisisGenotipo = entity;
        vm.clear = clear;
        vm.save = save;

		vm.mapAlelos = {};

		vm.editable = Principal.hasAnyAuthority(['ROLE_ABM']);

		vm.rowspanConjuntoMuestras = rowspanConjuntoMuestras;
		vm.importarAlelos = importarAlelos;
		vm.formImportarExportar = formImportarExportar;
		vm.exportarAlelos = exportarAlelos;

		vm.setExpandedConjuntosMuestras = setExpandedConjuntosMuestras;
		vm.setExpandedMuestras = setExpandedMuestras;
		vm.expandedConjuntosMuestras = false;
		vm.expandedMuestras = false;

		vm.analisisGenotipo.conjuntosMuestras = ConjuntoMuestras.analisisGenotipo({id:vm.analisisGenotipo.id});
		vm.analisisGenotipo.loci = Locus.analisisGenotipo({id:vm.analisisGenotipo.id});
		
		vm.alelosBD = Alelo.analisisGenotipo({id:vm.analisisGenotipo.id});
		//vm.alelosBD.$promise.then(function(){crearAlelos()});

		$q.all([vm.analisisGenotipo.conjuntosMuestras.$promise, 
				vm.analisisGenotipo.loci.$promise,
				vm.alelosBD.$promise]).then(function(){
			setExpanded();
			ordenarListas();
			crearAlelos();

		});

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });


		function setExpanded(){
			vm.analisisGenotipo.conjuntosMuestras.forEach(function(p){
						p.expanded = false;
						p.muestras.forEach(function(e){e.expanded = false;});
					});
		}

		function setExpandedConjuntosMuestras(){
			vm.expandedConjuntosMuestras = !vm.expandedConjuntosMuestras;
			vm.analisisGenotipo.conjuntosMuestras.forEach(function(p){p.expanded = vm.expandedConjuntosMuestras});
		}

		function setExpandedMuestras(){
			vm.expandedMuestras = !vm.expandedMuestras;
			vm.analisisGenotipo.conjuntosMuestras.forEach(function(p){
						p.muestras.forEach(function(e){e.expanded = vm.expandedMuestras;});
					});
		}

		function rowspanConjuntoMuestras(conjuntoMuestras){
			//Siempre devolvemos el maximo. Como las conjuntosMuestras están en tablas distintas no infire, y facilita el renderizado. 				
						
			return conjuntoMuestras.muestras.length +1;					
		}

		function ordenarListas(){

			vm.analisisGenotipo.conjuntosMuestras.sort(function(a,b){
				if(a.codigo == b.codigo)
					return 0;

				if(a.codigo < b.codigo)
					return -1;
				
				return 1;
			});


			vm.analisisGenotipo.loci.sort(function(a,b){
				if(a.codigo == b.codigo)
					return 0;

				if(a.codigo < b.codigo)
					return -1;
				
				return 1;
			});


			vm.analisisGenotipo.conjuntosMuestras.forEach(function(p){
				p.muestras.sort(function(a,b){
					if(a.codigoInterno == b.codigoInterno)
						return 0;

					if(a.codigoInterno < b.codigoInterno)
						return -1;
				
					return 1;
				});
			});
		}



		function crearAlelos(){
				
			for(var p = 0; p < vm.analisisGenotipo.conjuntosMuestras.length; p++){				
				for(var e = 0; e < vm.analisisGenotipo.conjuntosMuestras[p].muestras.length; e++){
					var muestra = vm.analisisGenotipo.conjuntosMuestras[p].muestras[e];
					var alelosTmp = [];
					for(var l = 0; l < vm.analisisGenotipo.loci.length; l++){
						var ploidia = vm.analisisGenotipo.loci[l].ploidia;
						for(var c = 1; c <= ploidia; c++){

							var alelo = crearAlelo(null, null, c, vm.analisisGenotipo.loci[l], muestra, false);

							var index = vm.alelosBD.findIndex(function(a){
													return  a[1] == c && 
															a[3] == muestra.id && 
															a[4] == vm.analisisGenotipo.loci[l].id;
										});
							
							if(index != -1){	
								var fila = vm.alelosBD[index];
								vm.alelosBD.splice(index, 1);							
								alelo.id = fila[0];	
								alelo.valor = fila[2];	
								vm.mapAlelos[alelo.id] = alelo.valor; 		
							}


							alelosTmp.push(alelo);
						}													
					}
					muestra.alelos = alelosTmp;
				}

			}

		}

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {

			vm.isSaving = true;
			var alelosTmp = [];

			vm.analisisGenotipo.conjuntosMuestras.forEach(function(pob){
				pob.muestras.forEach(function(ext){
				Array.prototype.push.apply(alelosTmp, ext.alelos.filter(function(a){
						return (a.id != null && vm.mapAlelos[a.id] != a.valor) || (a.id == null && !isBlank(a.valor));
					}))
				})
			});

			var alelosObj = [];
			
			alelosTmp.forEach(function(a){alelosObj.push(crearAlelo(a.id,a.valor,a.indice,a.locus,a.muestra, true))});

			Alelo.updateCreateAlelos(alelosObj, onSaveSuccess, onSaveError);


        }

		function crearAlelo(id, valor, indice, locus, muestra, envio){
			if(envio){
				muestra.alelos = null;
				muestra.muestra = null;
				muestra.profesional = null;
			}
			return {id: id, valor: valor, indice: indice, locus: locus, muestra: muestra};
		}


		function exportarAlelos(){
			var contenido = {matriz: [], conNombres: true};
			var loci = [];
			loci.push("conjuntoMuestras");
			loci.push("codigoInterno");
			vm.analisisGenotipo.loci.forEach(function(l){
				for(var i = 1; i <= l.ploidia; i++)
					loci.push(l.codigo+"_"+i);
			});
			contenido.matriz.push(loci);

			vm.analisisGenotipo.conjuntosMuestras.forEach(function(p){
					p.muestras.forEach(function(e){
						var valores = [];
						valores.push(p.codigo);
						valores.push(e.codigoInterno);
						e.alelos.forEach(function(a){ valores.push(a.valor);});
						contenido.matriz.push(valores);
					});
			});

			vm.formImportarExportar(contenido, importarAlelos);

		}

		function importarAlelos(contenido){

			var i = 0;

			var loci = {};

			if(contenido.conNombres){
				vm.analisisGenotipo.loci.forEach(function(l){
					for(var i = 1; i <= l.ploidia; i++){
						var indice = contenido.matriz[0].findIndex(function(c) {return c == l.codigo+"_"+i });
						if(indice != -1)					
							loci[l.codigo+"_"+i] = indice;
					}
				});

				vm.analisisGenotipo.conjuntosMuestras.forEach(function(p){				
					p.muestras.forEach(function(e){
						var fila = contenido.matriz.findIndex(function(f){return f[1] == e.codigoInterno});
					
						if(fila != -1){
							e.alelos.forEach(function(a){
								if(loci[a.locus.codigo+"_"+a.indice] != undefined){
									a.valor = contenido.matriz[fila][loci[a.locus.codigo+"_"+a.indice]];
								}
							});
						}
					});
				});

			}else{
				vm.analisisGenotipo.conjuntosMuestras.forEach(function(p){				
					p.muestras.forEach(function(e){
						if(i < contenido.matriz.length){
							e.alelos.forEach(function(a, j){
								if(j < contenido.matriz[i].length){
									a.valor = contenido.matriz[i][j];
								}
							});
						}
						i++;
					});
				});
			}

			
		}


		function formImportarExportar(contenido, funcionImp){
			AnalisisGenotipoAlelosImportar.open(contenido).then(function(contAct) {funcionImp(contAct)});
		}

		function isBlank(str) {
			return (!str || /^\s*$/.test(str));
		}

        function onSaveSuccess (result) {			
			AlertService.success('proyectoApp.analisisGenotipo.updated',{param: vm.analisisGenotipo.id});
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

    }


	function AnalisisGenotiposAlelos() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'AnalisisGenotipo'];

		function getService($uibModal, AnalisisGenotipo){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						templateUrl: 'app/entities/analisis-genotipo/analisis-genotipos-alelos.html',
				        controller: 'AnalisisGenotiposAlelosController',
				        controllerAs: 'vm',
				        backdrop: 'static',
				        size: 'lg',
				        resolve: {
				            entity: function() {return AnalisisGenotipo.get({id : id}).$promise;}
				        }
					}).result;
			}
		}
	}
})();
