(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('PaisDialog', PaisDialog)
        .controller('PaisDialogController', PaisDialogController);

    PaisDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Pais'];

    function PaisDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Pais) {
        var vm = this;

        vm.pais = entity;
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
            if (vm.pais.id !== null) {
                Pais.update(vm.pais, onSaveSuccess, onSaveError);
            } else {
                Pais.save(vm.pais, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoApp:paisUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }


	function PaisDialog() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'Pais'];

		function getService($uibModal, Pais){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						templateUrl: 'app/entities/pais/pais-dialog.html',
				        controller: 'PaisDialogController',
				        controllerAs: 'vm',
				        backdrop: 'static',
				        size: 'lg',
				        resolve: {
				            entity: function() {return id != null ? Pais.get({id : id}).$promise : {id : null};}
				        }
					}).result;
			}
		}
	}
})();
