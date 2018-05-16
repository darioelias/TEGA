(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('InstitucionDialog', InstitucionDialog)
        .controller('InstitucionDialogController', InstitucionDialogController);

    InstitucionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Institucion'];

    function InstitucionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Institucion) {
        var vm = this;

        vm.institucion = entity;
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
            if (vm.institucion.id !== null) {
                Institucion.update(vm.institucion, onSaveSuccess, onSaveError);
            } else {
                Institucion.save(vm.institucion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoApp:institucionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }


	function InstitucionDialog() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'Institucion'];

		function getService($uibModal, Institucion){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						templateUrl: 'app/entities/institucion/institucion-dialog.html',
				        controller: 'InstitucionDialogController',
				        controllerAs: 'vm',
				        backdrop: 'static',
				        size: 'lg',
				        resolve: {
				            entity: function() {return id != null ? Institucion.get({id : id}).$promise : {id : null};}
				        }
					}).result;
			}
		}
	}
})();
