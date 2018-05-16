(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('InstitucionDelete', InstitucionDelete)
        .controller('InstitucionDeleteController',InstitucionDeleteController);

    InstitucionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Institucion'];

    function InstitucionDeleteController($uibModalInstance, entity, Institucion) {
        var vm = this;

        vm.institucion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Institucion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }

	function InstitucionDelete() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'Institucion'];

		function getService($uibModal, Institucion){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						        templateUrl: 'app/entities/institucion/institucion-delete-dialog.html',
						        controller: 'InstitucionDeleteController',
						        controllerAs: 'vm',
						        size: 'md',
						        resolve: {
						            entity: function() {return Institucion.get({id : id}).$promise;}
								}
                   	   }).result;
			}
		}
	}
})();
