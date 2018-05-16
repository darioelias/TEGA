(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('ProcedimientoDialog', ProcedimientoDialog)
        .controller('ProcedimientoDialogController', ProcedimientoDialogController);

    ProcedimientoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 
											'entity', 'Procedimiento', 'Archivo'];

    function ProcedimientoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, 
											entity, Procedimiento, Archivo) {
        var vm = this;

        vm.procedimiento = entity;
        vm.clear = clear;
        vm.save = save;

		if(vm.procedimiento.id == null){
			vm.procedimiento.archivos = []
		}else{
			vm.procedimiento.archivos = Archivo.procedimiento({id: vm.procedimiento.id});
		}

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.procedimiento.id !== null) {
                Procedimiento.update(vm.procedimiento, onSaveSuccess, onSaveError);
            } else {
                Procedimiento.save(vm.procedimiento, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoApp:procedimientoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }


	function ProcedimientoDialog() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'Procedimiento'];

		function getService($uibModal, Procedimiento){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						templateUrl: 'app/entities/procedimiento/procedimiento-dialog.html',
				        controller: 'ProcedimientoDialogController',
				        controllerAs: 'vm',
				        backdrop: 'static',
				        size: 'lg',
				        resolve: {
				            entity: function() {return id != null ? Procedimiento.get({id : id}).$promise : {id : null};}
				        }
					}).result;
			}
		}
	}
})();
