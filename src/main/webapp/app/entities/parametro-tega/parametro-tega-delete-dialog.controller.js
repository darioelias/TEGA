(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('ParametroTegaDelete', ParametroTegaDelete)
        .controller('ParametroTegaDeleteController',ParametroTegaDeleteController);

    ParametroTegaDeleteController.$inject = ['$uibModalInstance', 'entity', 'ParametroTega'];

    function ParametroTegaDeleteController($uibModalInstance, entity, ParametroTega) {
        var vm = this;

        vm.parametro = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ParametroTega.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }

	function ParametroTegaDelete() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'ParametroTega'];

		function getService($uibModal, ParametroTega){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						        templateUrl: 'app/entities/parametro-tega/parametro-tega-delete-dialog.html',
						        controller: 'ParametroTegaDeleteController',
						        controllerAs: 'vm',
						        size: 'md',
						        resolve: {
						            entity: function() {return ParametroTega.get({id : id}).$promise;}
								}
                   	   }).result;
			}
		}
	}
})();
