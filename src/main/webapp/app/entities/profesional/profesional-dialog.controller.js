(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('ProfesionalDialog', ProfesionalDialog)
        .controller('ProfesionalDialogController', ProfesionalDialogController);

    ProfesionalDialogController.$inject = ['$timeout', '$scope', '$stateParams', 
											'$uibModalInstance', 'entity', 'Profesional', 
											'Institucion'];

    function ProfesionalDialogController ($timeout, $scope, $stateParams, 
											$uibModalInstance, entity, Profesional, 
											Institucion) {
        var vm = this;

        vm.profesional = entity;
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
            if (vm.profesional.id !== null) {
                Profesional.update(vm.profesional, onSaveSuccess, onSaveError);
            } else {
                Profesional.save(vm.profesional, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoApp:profesionalUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }


	function ProfesionalDialog() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'Profesional'];

		function getService($uibModal, Profesional){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						templateUrl: 'app/entities/profesional/profesional-dialog.html',
				        controller: 'ProfesionalDialogController',
				        controllerAs: 'vm',
				        backdrop: 'static',
				        size: 'lg',
				        resolve: {
				            entity: function() {return id != null ? Profesional.get({id : id}).$promise : {id : null};}
				        }
					}).result;
			}
		}
	}
})();
