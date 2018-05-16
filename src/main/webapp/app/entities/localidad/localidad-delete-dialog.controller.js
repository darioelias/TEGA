(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('LocalidadDelete', LocalidadDelete)
        .controller('LocalidadDeleteController',LocalidadDeleteController);

    LocalidadDeleteController.$inject = ['$uibModalInstance', 'entity', 'Localidad'];

    function LocalidadDeleteController($uibModalInstance, entity, Localidad) {
        var vm = this;

        vm.localidad = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Localidad.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }


	function LocalidadDelete() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'Localidad'];

		function getService($uibModal, Localidad){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						        templateUrl: 'app/entities/localidad/localidad-delete-dialog.html',
						        controller: 'LocalidadDeleteController',
						        controllerAs: 'vm',
						        size: 'md',
						        resolve: {
						            entity: function() {return Localidad.get({id : id}).$promise;}
								}
                   	   }).result;
			}
		}
	}
})();
