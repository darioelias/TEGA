(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('ParametroTegaDialog', ParametroTegaDialog)
        .controller('ParametroTegaDialogController', ParametroTegaDialogController);

    ParametroTegaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ParametroTega'];

    function ParametroTegaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ParametroTega) {
        var vm = this;

        vm.parametro = entity;
        vm.clear = clear;
        vm.save = save;
        
		if(vm.parametro.id == null){
			vm.parametro.interno = false;
			vm.parametro.tipo = "CARACTER";
			vm.parametro.valor = "";
		}

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.parametro.id !== null) {
                ParametroTega.update(vm.parametro, onSaveSuccess, onSaveError);
            } else {
                ParametroTega.save(vm.parametro, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoApp:parametroTegaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }


	function ParametroTegaDialog() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'ParametroTega'];

		function getService($uibModal, ParametroTega){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						templateUrl: 'app/entities/parametro-tega/parametro-tega-dialog.html',
				        controller: 'ParametroTegaDialogController',
				        controllerAs: 'vm',
				        backdrop: 'static',
				        size: 'lg',
				        resolve: {
				            entity: function() {return id != null ? ParametroTega.get({id : id}).$promise : {id : null};}
				        }
					}).result;
			}
		}
	}
})();
