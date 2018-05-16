(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('ParametroProcedimientoDelete', ParametroProcedimientoDelete)
        .controller('ParametroProcedimientoDeleteController',ParametroProcedimientoDeleteController);

    ParametroProcedimientoDeleteController.$inject = ['$uibModalInstance', 'entity', 'ParametroProcedimiento'];

    function ParametroProcedimientoDeleteController($uibModalInstance, entity, ParametroProcedimiento) {
        var vm = this;

        vm.parametro = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ParametroProcedimiento.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }

	function ParametroProcedimientoDelete() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'ParametroProcedimiento'];

		function getService($uibModal, ParametroProcedimiento){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						        templateUrl: 'app/entities/parametro-procedimiento/parametro-procedimiento-delete-dialog.html',
						        controller: 'ParametroProcedimientoDeleteController',
						        controllerAs: 'vm',
						        size: 'md',
						        resolve: {
						            entity: function() {return ParametroProcedimiento.get({id : id}).$promise;}
								}
                   	   }).result;
			}
		}
	}
})();
