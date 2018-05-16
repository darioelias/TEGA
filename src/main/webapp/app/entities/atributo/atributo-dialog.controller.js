(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('AtributoDialog', AtributoDialog)
        .controller('AtributoDialogController', AtributoDialogController);

    AtributoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Atributo'];

    function AtributoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Atributo) {
        var vm = this;

        vm.atributo = entity;
        vm.clear = clear;
        vm.save = save;
		
		if(vm.atributo.id == null){
			vm.atributo.tipo = "CARACTER";
			vm.atributo.valor = "";
		}

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.atributo.id !== null) {
                Atributo.update(vm.atributo, onSaveSuccess, onSaveError);
            } else {
                Atributo.save(vm.atributo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoApp:atributoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }


	function AtributoDialog() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'Atributo'];

		function getService($uibModal, Atributo){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						templateUrl: 'app/entities/atributo/atributo-dialog.html',
				        controller: 'AtributoDialogController',
				        controllerAs: 'vm',
				        backdrop: 'static',
				        size: 'lg',
				        resolve: {
				            entity: function() {return id != null ? Atributo.get({id : id}).$promise : {id : null};}
				        }
					}).result;
			}
		}
	}
})();
