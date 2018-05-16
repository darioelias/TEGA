(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('ProfesionalDelete', ProfesionalDelete)
        .controller('ProfesionalDeleteController',ProfesionalDeleteController);

    ProfesionalDeleteController.$inject = ['$uibModalInstance', 'entity', 'Profesional'];

    function ProfesionalDeleteController($uibModalInstance, entity, Profesional) {
        var vm = this;

        vm.profesional = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Profesional.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }

	function ProfesionalDelete() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'Profesional'];

		function getService($uibModal, Profesional){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						        templateUrl: 'app/entities/profesional/profesional-delete-dialog.html',
						        controller: 'ProfesionalDeleteController',
						        controllerAs: 'vm',
						        size: 'md',
						        resolve: {
						            entity: function() {return Profesional.get({id : id}).$promise;}
								}
                   	   }).result;
			}
		}
	}
})();
