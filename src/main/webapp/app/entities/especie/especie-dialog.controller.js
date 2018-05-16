(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('EspecieDialog', EspecieDialog)
        .controller('EspecieDialogController', EspecieDialogController);

    EspecieDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Especie'];

    function EspecieDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Especie) {
        var vm = this;

        vm.especie = entity;
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
            if (vm.especie.id !== null) {
                Especie.update(vm.especie, onSaveSuccess, onSaveError);
            } else {
                Especie.save(vm.especie, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoApp:especieUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }


	function EspecieDialog() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'Especie'];

		function getService($uibModal, Especie){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						templateUrl: 'app/entities/especie/especie-dialog.html',
				        controller: 'EspecieDialogController',
				        controllerAs: 'vm',
				        backdrop: 'static',
				        size: 'lg',
				        resolve: {
				            entity: function() {return id != null ? Especie.get({id : id}).$promise : {id : null};}
				        }
					}).result;
			}
		}
	}
})();
