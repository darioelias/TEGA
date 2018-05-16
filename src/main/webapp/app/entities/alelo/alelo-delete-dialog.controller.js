(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('AleloDelete', AleloDelete)
        .controller('AleloDeleteController',AleloDeleteController);

    AleloDeleteController.$inject = ['$uibModalInstance', 'entity', 'Alelo'];

    function AleloDeleteController($uibModalInstance, entity, Alelo) {
        var vm = this;

        vm.alelo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Alelo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }

	function AleloDelete() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'Alelo'];

		function getService($uibModal, Alelo){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						        templateUrl: 'app/entities/alelo/alelo-delete-dialog.html',
						        controller: 'AleloDeleteController',
						        controllerAs: 'vm',
						        size: 'md',
						        resolve: {
						            entity: function() {return Alelo.get({id : id}).$promise;}
								}
                   	   }).result;
			}
		}
	}
})();
