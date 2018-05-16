(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('ProvinciaDialog', ProvinciaDialog)
        .controller('ProvinciaDialogController', ProvinciaDialogController);

    ProvinciaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Provincia'];

    function ProvinciaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Provincia) {
        var vm = this;

        vm.provincia = entity;
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
            if (vm.provincia.id !== null) {
                Provincia.update(vm.provincia, onSaveSuccess, onSaveError);
            } else {
                Provincia.save(vm.provincia, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoApp:provinciaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }


	function ProvinciaDialog() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'Provincia'];

		function getService($uibModal, Provincia){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						templateUrl: 'app/entities/provincia/provincia-dialog.html',
				        controller: 'ProvinciaDialogController',
				        controllerAs: 'vm',
				        backdrop: 'static',
				        size: 'lg',
				        resolve: {
				            entity: function() {return id != null ? Provincia.get({id : id}).$promise : {id : null};}
				        }
					}).result;
			}
		}
	}
})();
