(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('ModoRecoleccionDelete', ModoRecoleccionDelete)
        .controller('ModoRecoleccionDeleteController',ModoRecoleccionDeleteController);

    ModoRecoleccionDeleteController.$inject = ['$uibModalInstance', 'entity', 'ModoRecoleccion'];

    function ModoRecoleccionDeleteController($uibModalInstance, entity, ModoRecoleccion) {
        var vm = this;

        vm.modoRecoleccion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ModoRecoleccion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }

	function ModoRecoleccionDelete() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'ModoRecoleccion'];

		function getService($uibModal, ModoRecoleccion){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						        templateUrl: 'app/entities/modo-recoleccion/modo-recoleccion-delete-dialog.html',
						        controller: 'ModoRecoleccionDeleteController',
						        controllerAs: 'vm',
						        size: 'md',
						        resolve: {
						            entity: function() {return ModoRecoleccion.get({id : id}).$promise;}
								}
                   	   }).result;
			}
		}
	}
})();
