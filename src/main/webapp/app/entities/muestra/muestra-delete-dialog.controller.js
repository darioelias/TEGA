(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('MuestraDelete', MuestraDelete)
        .controller('MuestraDeleteController',MuestraDeleteController);

    MuestraDeleteController.$inject = ['$uibModalInstance', 'entity', 'Muestra'];

    function MuestraDeleteController($uibModalInstance, entity, Muestra) {
        var vm = this;

        vm.muestra = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Muestra.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }


	function MuestraDelete() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'Muestra'];

		function getService($uibModal, Muestra){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						        templateUrl: 'app/entities/muestra/muestra-delete-dialog.html',
						        controller: 'MuestraDeleteController',
						        controllerAs: 'vm',
						        size: 'md',
						        resolve: {
						            entity: function() {return Muestra.get({id : id}).$promise;}
								}
                   	   }).result;
			}
		}
	}
})();
