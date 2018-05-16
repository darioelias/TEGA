(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('CategoriaParametroProcedimientoDelete', CategoriaParametroProcedimientoDelete)
        .controller('CategoriaParametroProcedimientoDeleteController',CategoriaParametroProcedimientoDeleteController);

    CategoriaParametroProcedimientoDeleteController.$inject = ['$uibModalInstance', 'entity', 'CategoriaParametroProcedimiento'];

    function CategoriaParametroProcedimientoDeleteController($uibModalInstance, entity, CategoriaParametroProcedimiento) {
        var vm = this;

        vm.categoriaParametroProcedimiento = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CategoriaParametroProcedimiento.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }

	function CategoriaParametroProcedimientoDelete() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'CategoriaParametroProcedimiento'];

		function getService($uibModal, CategoriaParametroProcedimiento){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						        templateUrl: 'app/entities/categoria-parametro-procedimiento/categoria-parametro-procedimiento-delete-dialog.html',
						        controller: 'CategoriaParametroProcedimientoDeleteController',
						        controllerAs: 'vm',
						        size: 'md',
						        resolve: {
						            entity: function() {return CategoriaParametroProcedimiento.get({id : id}).$promise;}
								}
                   	   }).result;
			}
		}
	}
})();
