(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('AnalisisGenotipoProcDialog', AnalisisGenotipoProcDialog)
        .controller('AnalisisGenotipoProcDialogController', AnalisisGenotipoProcDialogController);

    AnalisisGenotipoProcDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 
												'AnalisisGenotipo', 'AlertService','analisisGenotipo', '$q',
												'ConjuntoMuestras', 'Locus', 'AlertaService', 'Procedimiento',
												'ParametroProcedimiento', 'Pregunta', 'ParametroProcedimientoProcesarParametros'];

    function AnalisisGenotipoProcDialogController ($timeout, $scope, $stateParams, $uibModalInstance,
												AnalisisGenotipo, AlertService, analisisGenotipo, $q,
												ConjuntoMuestras, Locus, AlertaService, Procedimiento,
												ParametroProcedimiento, Pregunta, ParametroProcedimientoProcesarParametros) {
        var vm = this;

        vm.clear = clear;
        vm.enviar = enviar;
		vm.tieneCategorias = tieneCategorias;
		vm.categoriasNoCargadas = categoriasNoCargadas;
		vm.enviando = false;
		vm.analisisGenotipo = analisisGenotipo;
		vm.alertas = [];
		vm.activeForm = 0;
		vm.procedimientos = [];

		Procedimiento.query(null,function(procedimientos){
			
			if(procedimientos.length > 0){
				procedimientos.sort(function(a,b){
					if(a.nombre > b.nombre){return 1;}
					else if(a.nombre < b.nombre){return -1;}
					return 0;
				});

				vm.procedimientos = procedimientos;
				vm.activeForm = vm.procedimientos[0].id;
			}else{
				AlertaService.error(vm.alertas, "proyectoApp.analisisGenotipo.error.sin-procedimientos");
				vm.enviando = true;
			}		
		});

		$scope.$watch("vm.activeForm",function(idNuevo,idViejo){

			var proc = vm.procedimientos.find(function(p){return p.id == idNuevo});
			if(proc && !proc.categorias){

				ParametroProcedimiento.procedimiento({id:proc.id}, function(parametros){
					
					var categorias = ParametroProcedimientoProcesarParametros.procesar(parametros);
					if(categorias.length == 1)
						categorias[0]._open = true;

					proc.categorias = categorias; 

				});
			}

		});
	

		var conjuntosMuestras = ConjuntoMuestras.analisisGenotipo({id:analisisGenotipo.id});
		var loci = Locus.analisisGenotipo({id:analisisGenotipo.id});

		$q.all([conjuntosMuestras.$promise, loci.$promise]).then(function(){

			if(loci.length == 0)
				AlertaService.warning(vm.alertas, "proyectoApp.analisisGenotipo.error.sin-loci");

			if(conjuntosMuestras.length == 0){
				AlertaService.warning(vm.alertas, "proyectoApp.analisisGenotipo.error.sin-conjuntos-muestras");
			}else{
				var conjuntosVacios = conjuntosMuestras.find(function(c){return c.muestras.length == 0});
				if(conjuntosVacios)		
					AlertaService.warning(vm.alertas, "proyectoApp.analisisGenotipo.error.conjuntos-muestras-vacio");
			}
		});

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

		function tieneCategorias(procedimiento){
			return procedimiento.categorias && procedimiento.categorias.length > 0;
		}

		function categoriasNoCargadas(proc){
			return !proc.categorias;
		}

        function enviar () {


			var procedimiento = vm.procedimientos.find(function(p){return p.id == vm.activeForm});
			
			Pregunta.open({titulo: "proyectoApp.analisisGenotipo.proc.tituloPregunta",
						   preguntaMsj: "proyectoApp.analisisGenotipo.proc.pregunta",
						   preguntaParam: {nomProc: procedimiento.nombre,
										   codAnalisis: vm.analisisGenotipo.codigo}})
					.then(function(){
						
						vm.enviando = true;

						var categorias = angular.copy(procedimiento.categorias);
						categorias.forEach(function(c){delete c._open});

						var parametros = {idAnalisis: vm.analisisGenotipo.id,
										  idProcedimiento: procedimiento.id,
										  categorias: categorias}

						AnalisisGenotipo.ejecutarProcedimiento({parametros: parametros},
															    onSuccess, onError);

						function onSuccess(data, headers) {
							$uibModalInstance.close(true); 
						    AlertService.info('proyectoApp.analisisGenotipo.proc.envio', 
											  {nomProc: procedimiento.nombre,
											   codAnalisis: vm.analisisGenotipo.codigo});             
						}
						function onError(error) {
							vm.enviando = false;
						    AlertService.error(error.data.message);
						}

					});
        }

    }


	function AnalisisGenotipoProcDialog() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'AnalisisGenotipo', 'AlertService', '$q'];

		function getService($uibModal, AnalisisGenotipo, AlertService, $q){
		
			return {open: open};

			function open(id){

				return $q(function(resolve, reject) {
					AnalisisGenotipo.get({id : id}, function(analisisGenotipo){
						if(analisisGenotipo.estado != "DISPONIBLE"){
							AlertService.error("proyectoApp.analisisGenotipo.error.no-disponible");
							reject('proyectoApp.analisisGenotipo.error.no-disponible');
						}else{
							$uibModal.open({
										templateUrl: 'app/entities/analisis-genotipo/analisis-genotipo-proc-dialog.html',
										controller: 'AnalisisGenotipoProcDialogController',
										controllerAs: 'vm',
										backdrop: 'static',
										size: 'lg',
										resolve: {
											analisisGenotipo: function() {return analisisGenotipo}
										}
									}).result.then(
										function() {resolve();}, 
										function() {reject();}
									);
						}
					});			
				});
			}
		}
	}

})();
