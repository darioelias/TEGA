(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('AnalisisGenotipoDelete', AnalisisGenotipoDelete)
        .controller('AnalisisGenotipoDeleteController',AnalisisGenotipoDeleteController);

    AnalisisGenotipoDeleteController.$inject = ['$uibModalInstance', 'entity', 'AnalisisGenotipo'];

    function AnalisisGenotipoDeleteController($uibModalInstance, entity, AnalisisGenotipo) {
        var vm = this;

        vm.analisisGenotipo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AnalisisGenotipo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }

	function AnalisisGenotipoDelete() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'AnalisisGenotipo'];

		function getService($uibModal, AnalisisGenotipo){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						        templateUrl: 'app/entities/analisis-genotipo/analisis-genotipo-delete-dialog.html',
						        controller: 'AnalisisGenotipoDeleteController',
						        controllerAs: 'vm',
						        size: 'md',
						        resolve: {
						            entity: function() {return AnalisisGenotipo.get({id : id}).$promise;}
								}
                   	   }).result;
			}
		}
	}
})();
