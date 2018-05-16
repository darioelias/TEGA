(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('BackupDialog', BackupDialog)
        .controller('BackupDialogController', BackupDialogController);

    BackupDialogController.$inject = ['$uibModalInstance', 'AlertaService',
									'DownloadService', 'Backup'];

    function BackupDialogController ($uibModalInstance, AlertaService, 
									DownloadService, Backup) {
        var vm = this;

        vm.clear = clear;
        vm.generarBackup = generarBackup;
		vm.generando = false;
		vm.resultado = {};
		vm.alertas = [];

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function generarBackup() {
			vm.generando = true;
			vm.resultado = {};
			Backup.crear(null, onSuccess, onError);

            function onSuccess(data) {
				vm.resultado = data;

				if(vm.resultado.stdErr){
					AlertaService.warning(vm.alertas,"backup.creado-error");
				}else{
					AlertaService.success(vm.alertas,"backup.creado");
				}

                DownloadService.download("api/backup-descargar").then(function() {
					vm.generando = false;
                }, function(error) {
		            AlertaService.error(vm.alertas,error.headers('X-proyectoApp-error'));
		            AlertaService.error(vm.alertas,error.headers('X-proyectoApp-error-msj'));
					vm.generando = false;
                });
            }
            function onError(error) {
                AlertaService.error(vm.alertas,error.headers('X-proyectoApp-error'));
                AlertaService.error(vm.alertas,error.headers('X-proyectoApp-error-msj'));
				vm.generando = false;
            }
        }
    }

	function BackupDialog() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal'];

		function getService($uibModal){
		
			return {open: open};

			function open(){

				return $uibModal.open({
						        templateUrl: 'app/admin/backup/backup.html',
						        controller: 'BackupDialogController',
						        controllerAs: 'vm',
				        		backdrop: 'static',
						        size: 'md',
								resolve: {
									translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
										$translatePartialLoader.addPart('backup');
										$translatePartialLoader.addPart('global');
										return $translate.refresh();
									}]
								}
                   	   }).result;
			}
		}
	}
})();
