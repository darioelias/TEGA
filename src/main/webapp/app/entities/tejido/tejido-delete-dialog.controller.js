(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('TejidoDelete', TejidoDelete)
        .controller('TejidoDeleteController',TejidoDeleteController);

    TejidoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tejido'];

    function TejidoDeleteController($uibModalInstance, entity, Tejido) {
        var vm = this;

        vm.tejido = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tejido.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }


	function TejidoDelete() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'Tejido'];

		function getService($uibModal, Tejido){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						        templateUrl: 'app/entities/tejido/tejido-delete-dialog.html',
						        controller: 'TejidoDeleteController',
						        controllerAs: 'vm',
						        size: 'md',
						        resolve: {
						            entity: function() {return Tejido.get({id : id}).$promise;}
								}
                   	   }).result;
			}
		}
	}
})();
