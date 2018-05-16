(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('ConjuntoMuestrasDelete', ConjuntoMuestrasDelete)
        .controller('ConjuntoMuestrasDeleteController',ConjuntoMuestrasDeleteController);

    ConjuntoMuestrasDeleteController.$inject = ['$uibModalInstance', 'entity', 'ConjuntoMuestras'];

    function ConjuntoMuestrasDeleteController($uibModalInstance, entity, ConjuntoMuestras) {
        var vm = this;

        vm.conjuntoMuestras = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ConjuntoMuestras.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }

	function ConjuntoMuestrasDelete() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'ConjuntoMuestras'];

		function getService($uibModal, ConjuntoMuestras){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						        templateUrl: 'app/entities/conjunto-muestras/conjunto-muestras-delete-dialog.html',
						        controller: 'ConjuntoMuestrasDeleteController',
						        controllerAs: 'vm',
						        size: 'md',
						        resolve: {
						            entity: function() {return ConjuntoMuestras.get({id : id}).$promise;}
								}
                   	   }).result;
			}
		}
	}
})();
