(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('ProvinciaDelete', ProvinciaDelete)
        .controller('ProvinciaDeleteController',ProvinciaDeleteController);

    ProvinciaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Provincia'];

    function ProvinciaDeleteController($uibModalInstance, entity, Provincia) {
        var vm = this;

        vm.provincia = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Provincia.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }

	function ProvinciaDelete() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'Provincia'];

		function getService($uibModal, Provincia){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						        templateUrl: 'app/entities/provincia/provincia-delete-dialog.html',
						        controller: 'ProvinciaDeleteController',
						        controllerAs: 'vm',
						        size: 'md',
						        resolve: {
						            entity: function() {return Provincia.get({id : id}).$promise;}
								}
                   	   }).result;
			}
		}
	}
})();
