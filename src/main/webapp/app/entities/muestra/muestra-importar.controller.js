(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('MuestraImportarDialog', MuestraImportarDialog)
        .controller('MuestraImportarDialogController', MuestraImportarDialogController);

    MuestraImportarDialogController.$inject = ['$timeout', '$scope', '$uibModalInstance', 
									   'Muestra','Upload','AlertService','AlertaService',
										'ParametroTega'];

    function MuestraImportarDialogController ($timeout, $scope, $uibModalInstance, 
									  		  Muestra, Upload, AlertService, AlertaService,
											  ParametroTega) {
        var vm = this;

        
        vm.clear = clear;
		vm.subir = subir;
		vm.resultados = [];
		vm.alertas = [];
		vm.archivo = null;
		vm.subiendo = false;
		vm.datosAImportar = "MUESTRAS";

		ParametroTega.obtener({codigo: "SEPARADOR_IMP_EXP_ARCHIVOS"}, 
						 function(p){vm.separador = p.valor});

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

			if(vm.datosAImportar != "MUESTRAS" && vm.datosAImportar != "ATRIBUTOS"){
				AlertaService.error(vm.alertas,'proyectoApp.muestra.importacion.datosAImportar-obligatorio');
				return;	
			}

			var url = "api/muestras-importar";
			if(vm.datosAImportar == "ATRIBUTOS")
				url = "api/muestras-importar-atributos";

			vm.subiendo = true;
			vm.resultados = [];
			Upload.upload({
					url: url,
					file: vm.archivo,
					fields: {'sep': vm.separador}
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



	function MuestraImportarDialog() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal'];

		function getService($uibModal){
		
			return {open: open};

			function open(){

				return $uibModal.open({
		                templateUrl: 'app/entities/muestra/muestra-importar.html',
		                controller: 'MuestraImportarDialogController',
		                controllerAs: 'vm',
		                backdrop: 'static',
		                size: 'md'
		            }).result;
			}
		}
	}
})();
