(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('AleloImportarDialog', AleloImportarDialog)
        .controller('AleloImportarDialogController', AleloImportarDialogController);

    AleloImportarDialogController.$inject = ['$timeout', '$scope', '$uibModalInstance', 
									   		 'Upload','AlertService','AlertaService',
											 'ParametroTega'];

    function AleloImportarDialogController ($timeout, $scope, $uibModalInstance, 
									  		Upload, AlertService, AlertaService,
											ParametroTega) {
        var vm = this;

        
        vm.clear = clear;
		vm.subir = subir;
		vm.resultados = [];
		vm.alertas = [];
		vm.archivo = null;
		vm.subiendo = false;
		vm.publico = false;
		vm.tipoImportacion = null;

		ParametroTega.obtener({codigo: "SEPARADOR_IMP_EXP_ARCHIVOS"}, 
						 function(p){vm.separador = p.valor});

		ParametroTega.obtener({codigo: "ALELOS_IMP_EXP_VALOR_NULO"}, 
						 function(p){vm.nulo = p.valor});

		ParametroTega.obtener({codigo: "ALELOS_IMP_TIPO_IMPORTACION"}, 
						 function(p){vm.tipoImportacion = p.valor});

		function subir (){

			if(vm.subiendo)
				return;

			vm.alertas = [];

			if(vm.archivo == null){
				AlertaService.error(vm.alertas,'general.archivo-obligatorio');
				return;	
			}

			if(!vm.separador){
				AlertaService.error(vm.alertas,'general.separador-obligatorio');
				return;	
			}

			if(vm.tipoImportacion != "MATRIZ" && vm.tipoImportacion != "LISTA"){
				AlertaService.error(vm.alertas,'proyectoApp.alelo.importacion.tipo-obligatorio');
				return;	
			}

			vm.subiendo = true;
			vm.resultados = [];
			Upload.upload({
					url: "api/alelos-importar",
					file: vm.archivo,
					fields: {'tipo':vm.tipoImportacion,
							 'sep': vm.separador,
							 'nulo': vm.nulo,
							 'publico': vm.publico}
				}).progress(function (evt) {
					vm.porcentaje =  parseInt(100.0 * evt.loaded / evt.total);
				}).success(function (data, status, headers, config) {
					for(var prop in data){
						var datos = "";

						if(angular.isArray(data[prop]))
							datos = data[prop].join(", ");
						else
							datos = data[prop];

						vm.resultados.push({codigo: prop, cantidad: datos});
					}
					vm.subiendo = false;
					AlertaService.info(vm.alertas,'general.archivo-importado');
				}).error(function (data, status, headers, config){
					AlertaService.error(vm.alertas, headers('X-proyectoApp-error-msj'));
					vm.subiendo = false;
				});
	
		}


        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.close(true);
        }

    }



	function AleloImportarDialog() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal'];

		function getService($uibModal){
		
			return {open: open};

			function open(){

				return $uibModal.open({
		                templateUrl: 'app/entities/alelo/alelo-importar.html',
		                controller: 'AleloImportarDialogController',
		                controllerAs: 'vm',
		                backdrop: 'static',
		                size: 'md'
		            }).result;
			}
		}
	}
})();
