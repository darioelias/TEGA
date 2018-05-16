(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('AnalisisGenotipoVerLogDialog', AnalisisGenotipoVerLogDialog)
        .controller('AnalisisGenotipoVerLogDialogController', AnalisisGenotipoVerLogDialogController);

    AnalisisGenotipoVerLogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 
												'AnalisisGenotipo', 'AlertService','analisisGenotipo', '$q'];

    function AnalisisGenotipoVerLogDialogController ($timeout, $scope, $stateParams, $uibModalInstance,
												AnalisisGenotipo, AlertService, analisisGenotipo, $q) {
        var vm = this;

        vm.clear = clear;
        vm.actualizar = actualizar;
		vm.enviando = false;
		vm.analisisGenotipo = analisisGenotipo;
		vm.logCmd = "";
		actualizar();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function actualizar () {
			vm.enviando = true;
			AnalisisGenotipo.verLog({id: vm.analisisGenotipo.id}, function(data){
				vm.logCmd = "";

				if(data.content == "error.sinProcedimientoEjec")
					AlertService.error(data.content);
				else
					vm.logCmd = data.content;

				vm.enviando = false;

			}, function onError(error){
                AlertService.error(error);
				vm.logCmd = "";
				vm.enviando = false;
            });
        }

    }


	function AnalisisGenotipoVerLogDialog() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'AnalisisGenotipo', 'AlertService', '$q'];

		function getService($uibModal, AnalisisGenotipo, AlertService, $q){
		
			return {open: open};

			function open(id){

				return $q(function(resolve, reject) {
					AnalisisGenotipo.get({id : id}, function(analisisGenotipo){
						if(analisisGenotipo.estado != "EJECUTANDO"){
							AlertService.error("proyectoApp.analisisGenotipo.error.no-ejecucion");
							reject('proyectoApp.analisisGenotipo.error.no-ejecucion');
						}else{
							$uibModal.open({
										templateUrl: 'app/entities/analisis-genotipo/analisis-genotipo-ver-log-dialog.html',
										controller: 'AnalisisGenotipoVerLogDialogController',
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
