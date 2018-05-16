(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('TejidoDialog', TejidoDialog)
        .controller('TejidoDialogController', TejidoDialogController);

    TejidoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tejido'];

    function TejidoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tejido) {
        var vm = this;

        vm.tejido = entity;
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
            if (vm.tejido.id !== null) {
                Tejido.update(vm.tejido, onSaveSuccess, onSaveError);
            } else {
                Tejido.save(vm.tejido, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoApp:tejidoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }


	function TejidoDialog() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'Tejido'];

		function getService($uibModal, Tejido){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						templateUrl: 'app/entities/tejido/tejido-dialog.html',
				        controller: 'TejidoDialogController',
				        controllerAs: 'vm',
				        backdrop: 'static',
				        size: 'lg',
				        resolve: {
				            entity: function() {return id != null ? Tejido.get({id : id}).$promise : {id : null};}
				        }
					}).result;
			}
		}
	}
})();
