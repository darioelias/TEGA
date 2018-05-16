(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('ParametroProcedimientoDialog', ParametroProcedimientoDialog)
        .controller('ParametroProcedimientoDialogController', ParametroProcedimientoDialogController);

    ParametroProcedimientoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ParametroProcedimiento'];

    function ParametroProcedimientoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ParametroProcedimiento) {
        var vm = this;

        vm.parametro = entity;
        vm.clear = clear;
        vm.save = save;
        

		if(vm.parametro.id == null){
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
                ParametroProcedimiento.update(vm.parametro, onSaveSuccess, onSaveError);
            } else {
                ParametroProcedimiento.save(vm.parametro, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoApp:parametroProcedimientoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }


	function ParametroProcedimientoDialog() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'ParametroProcedimiento'];

		function getService($uibModal, ParametroProcedimiento){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						templateUrl: 'app/entities/parametro-procedimiento/parametro-procedimiento-dialog.html',
				        controller: 'ParametroProcedimientoDialogController',
				        controllerAs: 'vm',
				        backdrop: 'static',
				        size: 'lg',
				        resolve: {
				            entity: function() {return id != null ? ParametroProcedimiento.get({id : id}).$promise : {id : null};}
				        }
					}).result;
			}
		}
	}
})();
