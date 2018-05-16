(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('PaisDelete', PaisDelete)
        .controller('PaisDeleteController',PaisDeleteController);

    PaisDeleteController.$inject = ['$uibModalInstance', 'entity', 'Pais'];

    function PaisDeleteController($uibModalInstance, entity, Pais) {
        var vm = this;

        vm.pais = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Pais.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }

	function PaisDelete() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'Pais'];

		function getService($uibModal, Pais){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						        templateUrl: 'app/entities/pais/pais-delete-dialog.html',
						        controller: 'PaisDeleteController',
						        controllerAs: 'vm',
						        size: 'md',
						        resolve: {
						            entity: function() {return Pais.get({id : id}).$promise;}
								}
                   	   }).result;
			}
		}
	}
})();
