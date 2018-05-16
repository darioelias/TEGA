(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('ProyectoDialog', ProyectoDialog)
        .controller('ProyectoDialogController', ProyectoDialogController);

    ProyectoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 
										'entity', 'Proyecto', 'Archivo'];

    function ProyectoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, 
										entity, Proyecto, Archivo) {
        var vm = this;

        vm.proyecto = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
		vm.proyecto.atributos = [];

		if(vm.proyecto.id == null){
			vm.proyecto.fecha = new Date();
			vm.proyecto.archivos = []
		}else{
			vm.proyecto.archivos = Archivo.proyecto({id: vm.proyecto.id});
		}


        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.proyecto.id !== null) {
                Proyecto.update(vm.proyecto, onSaveSuccess, onSaveError);
            } else {
                Proyecto.save(vm.proyecto, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoApp:proyectoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fecha = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }


	function ProyectoDialog() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'Proyecto'];

		function getService($uibModal, Proyecto){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						templateUrl: 'app/entities/proyecto/proyecto-dialog.html',
				        controller: 'ProyectoDialogController',
				        controllerAs: 'vm',
				        backdrop: 'static',
				        size: 'lg',
				        resolve: {
				            entity: function() {return id != null ? Proyecto.get({id : id}).$promise : {id : null};}
				        }
					}).result;
			}
		}
	}
})();
