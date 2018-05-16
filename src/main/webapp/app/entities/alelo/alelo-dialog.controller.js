(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('AleloDialog', AleloDialog)
        .controller('AleloDialogController', AleloDialogController);

    AleloDialogController.$inject = ['$timeout', '$scope', '$stateParams', 
									'$uibModalInstance', 'entity', 'Alelo',
									'AlertService'];

    function AleloDialogController ($timeout, $scope, $stateParams, 
									$uibModalInstance, entity, Alelo, 
									AlertService) {
        var vm = this;

        vm.alelo = entity;
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
            if (vm.alelo.id !== null) {
                Alelo.update(vm.alelo, onSaveSuccess, onSaveError);
            } else {
                Alelo.save(vm.alelo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoApp:aleloUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }


	function AleloDialog() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'Alelo'];

		function getService($uibModal, Alelo){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						templateUrl: 'app/entities/alelo/alelo-dialog.html',
				        controller: 'AleloDialogController',
				        controllerAs: 'vm',
				        backdrop: 'static',
				        size: 'lg',
				        resolve: {
				            entity: function() {return id != null ? Alelo.get({id : id}).$promise : {id : null};}
				        }
					}).result;
			}
		}
	}
})();
