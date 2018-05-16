(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('EspecieDelete', EspecieDelete)
        .controller('EspecieDeleteController',EspecieDeleteController);

    EspecieDeleteController.$inject = ['$uibModalInstance', 'entity', 'Especie'];

    function EspecieDeleteController($uibModalInstance, entity, Especie) {
        var vm = this;

        vm.especie = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Especie.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }

	function EspecieDelete() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'Especie'];

		function getService($uibModal, Especie){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						        templateUrl: 'app/entities/especie/especie-delete-dialog.html',
						        controller: 'EspecieDeleteController',
						        controllerAs: 'vm',
						        size: 'md',
						        resolve: {
						            entity: function() {return Especie.get({id : id}).$promise;}
								}
                   	   }).result;
			}
		}
	}
})();
