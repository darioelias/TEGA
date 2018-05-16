(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('ProyectoDelete', ProyectoDelete)
        .controller('ProyectoDeleteController',ProyectoDeleteController);

    ProyectoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Proyecto'];

    function ProyectoDeleteController($uibModalInstance, entity, Proyecto) {
        var vm = this;

        vm.proyecto = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Proyecto.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }


	function ProyectoDelete() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'Proyecto'];

		function getService($uibModal, Proyecto){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						        templateUrl: 'app/entities/proyecto/proyecto-delete-dialog.html',
						        controller: 'ProyectoDeleteController',
						        controllerAs: 'vm',
						        size: 'md',
						        resolve: {
						            entity: function() {return Proyecto.get({id : id}).$promise;}
								}
                   	   }).result;
			}
		}
	}
})();
