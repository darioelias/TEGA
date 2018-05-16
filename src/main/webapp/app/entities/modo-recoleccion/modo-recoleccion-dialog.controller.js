(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('ModoRecoleccionDialog', ModoRecoleccionDialog)
        .controller('ModoRecoleccionDialogController', ModoRecoleccionDialogController);

    ModoRecoleccionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ModoRecoleccion', 'Muestra'];

    function ModoRecoleccionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ModoRecoleccion, Muestra) {
        var vm = this;

        vm.modoRecoleccion = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.modoRecoleccion.id !== null) {
                ModoRecoleccion.update(vm.modoRecoleccion, onSaveSuccess, onSaveError);
            } else {
                ModoRecoleccion.save(vm.modoRecoleccion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoApp:modoRecoleccionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }

	function ModoRecoleccionDialog() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'ModoRecoleccion'];

		function getService($uibModal, ModoRecoleccion){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						templateUrl: 'app/entities/modo-recoleccion/modo-recoleccion-dialog.html',
				        controller: 'ModoRecoleccionDialogController',
				        controllerAs: 'vm',
				        backdrop: 'static',
				        size: 'lg',
				        resolve: {
				            entity: function() {return id != null ? ModoRecoleccion.get({id : id}).$promise : {id : null};}
				        }
					}).result;
			}
		}
	}
})();
