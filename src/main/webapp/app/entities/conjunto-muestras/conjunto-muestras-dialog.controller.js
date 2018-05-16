(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('ConjuntoMuestrasDialog', ConjuntoMuestrasDialog)
        .controller('ConjuntoMuestrasDialogController', ConjuntoMuestrasDialogController);

    ConjuntoMuestrasDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 
													'ConjuntoMuestras', 'Muestra'];

    function ConjuntoMuestrasDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, 
													ConjuntoMuestras, Muestra) {
        var vm = this;

        vm.conjuntoMuestras = entity;
        vm.clear = clear;
        vm.save = save;
		vm.conjuntoMuestras.atributos = [];

		if(vm.conjuntoMuestras.id == null){
			vm.conjuntoMuestras.muestras = [];
		}else{
			vm.conjuntoMuestras.muestras = Muestra.conjuntoMuestras({id:vm.conjuntoMuestras.id});
		}

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.conjuntoMuestras.id !== null) {
                ConjuntoMuestras.update(vm.conjuntoMuestras, onSaveSuccess, onSaveError);
            } else {
                ConjuntoMuestras.save(vm.conjuntoMuestras, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoApp:conjuntoMuestrasUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }

	function ConjuntoMuestrasDialog() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'ConjuntoMuestras'];

		function getService($uibModal, ConjuntoMuestras){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						templateUrl: 'app/entities/conjunto-muestras/conjunto-muestras-dialog.html',
				        controller: 'ConjuntoMuestrasDialogController',
				        controllerAs: 'vm',
				        backdrop: 'static',
				        size: 'lg',
				        resolve: {
				            entity: function() {return id != null ? ConjuntoMuestras.get({id : id}).$promise : {id : null};}
				        }
					}).result;
			}
		}
	}
})();
