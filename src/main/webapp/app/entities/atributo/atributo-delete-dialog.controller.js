(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('AtributoDelete', AtributoDelete)
        .controller('AtributoDeleteController',AtributoDeleteController);

    AtributoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Atributo'];

    function AtributoDeleteController($uibModalInstance, entity, Atributo) {
        var vm = this;

        vm.atributo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Atributo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }

	function AtributoDelete() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'Atributo'];

		function getService($uibModal, Atributo){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						        templateUrl: 'app/entities/atributo/atributo-delete-dialog.html',
						        controller: 'AtributoDeleteController',
						        controllerAs: 'vm',
						        size: 'md',
						        resolve: {
						            entity: function() {return Atributo.get({id : id}).$promise;}
								}
                   	   }).result;
			}
		}
	}
})();
