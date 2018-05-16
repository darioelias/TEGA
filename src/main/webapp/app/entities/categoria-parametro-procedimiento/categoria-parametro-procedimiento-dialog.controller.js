(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('CategoriaParametroProcedimientoDialog', CategoriaParametroProcedimientoDialog)
        .controller('CategoriaParametroProcedimientoDialogController', CategoriaParametroProcedimientoDialogController);

    CategoriaParametroProcedimientoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CategoriaParametroProcedimiento', 'Muestra'];

    function CategoriaParametroProcedimientoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CategoriaParametroProcedimiento, Muestra) {
        var vm = this;

        vm.categoriaParametroProcedimiento = entity;
        vm.clear = clear;
        vm.save = save;
        vm.muestras = Muestra.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.categoriaParametroProcedimiento.id !== null) {
                CategoriaParametroProcedimiento.update(vm.categoriaParametroProcedimiento, onSaveSuccess, onSaveError);
            } else {
                CategoriaParametroProcedimiento.save(vm.categoriaParametroProcedimiento, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoApp:categoriaparametroprocedimientoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }


	function CategoriaParametroProcedimientoDialog() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'CategoriaParametroProcedimiento'];

		function getService($uibModal, CategoriaParametroProcedimiento){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						templateUrl: 'app/entities/categoria-parametro-procedimiento/categoria-parametro-procedimiento-dialog.html',
				        controller: 'CategoriaParametroProcedimientoDialogController',
				        controllerAs: 'vm',
				        backdrop: 'static',
				        size: 'lg',
				        resolve: {
				            entity: function() {return id != null ? CategoriaParametroProcedimiento.get({id : id}).$promise : {id : null};}
				        }
					}).result;
			}
		}
	}
})();
