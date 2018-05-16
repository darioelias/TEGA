(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('RegionDelete', RegionDelete)
        .controller('RegionDeleteController',RegionDeleteController);

    RegionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Region'];

    function RegionDeleteController($uibModalInstance, entity, Region) {
        var vm = this;

        vm.region = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Region.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }

	function RegionDelete() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'Region'];

		function getService($uibModal, Region){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						        templateUrl: 'app/entities/region/region-delete-dialog.html',
						        controller: 'RegionDeleteController',
						        controllerAs: 'vm',
						        size: 'md',
						        resolve: {
						            entity: function() {return Region.get({id : id}).$promise;}
								}
                   	   }).result;
			}
		}
	}
})();
