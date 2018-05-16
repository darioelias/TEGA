(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('LocusDelete', LocusDelete)
        .controller('LocusDeleteController',LocusDeleteController);

    LocusDeleteController.$inject = ['$uibModalInstance', 'entity', 'Locus'];

    function LocusDeleteController($uibModalInstance, entity, Locus) {
        var vm = this;

        vm.locus = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Locus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }

	function LocusDelete() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'Locus'];

		function getService($uibModal, Locus){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						        templateUrl: 'app/entities/locus/locus-delete-dialog.html',
						        controller: 'LocusDeleteController',
						        controllerAs: 'vm',
						        size: 'md',
						        resolve: {
						            entity: function() {return Locus.get({id : id}).$promise;}
								}
                   	   }).result;
			}
		}
	}
})();
