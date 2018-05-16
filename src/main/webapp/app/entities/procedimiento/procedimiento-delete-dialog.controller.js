(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('ProcedimientoDelete', ProcedimientoDelete)
        .controller('ProcedimientoDeleteController',ProcedimientoDeleteController);

    ProcedimientoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Procedimiento'];

    function ProcedimientoDeleteController($uibModalInstance, entity, Procedimiento) {
        var vm = this;

        vm.procedimiento = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Procedimiento.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }

	function ProcedimientoDelete() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'Procedimiento'];

		function getService($uibModal, Procedimiento){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						        templateUrl: 'app/entities/procedimiento/procedimiento-delete-dialog.html',
						        controller: 'ProcedimientoDeleteController',
						        controllerAs: 'vm',
						        size: 'md',
						        resolve: {
						            entity: function() {return Procedimiento.get({id : id}).$promise;}
								}
                   	   }).result;
			}
		}
	}
})();
