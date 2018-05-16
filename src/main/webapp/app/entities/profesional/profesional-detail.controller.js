(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('ProfesionalDetailController', ProfesionalDetailController);

    ProfesionalDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 
										   'entity', 'Profesional', 'ProfesionalDialog'];

    function ProfesionalDetailController($scope, $rootScope, $stateParams, previousState, 
										 entity, Profesional, ProfesionalDialog) {
        var vm = this;

        vm.previousState = previousState.name;
		vm.editar = editar;

		load(entity);

		function load(entity){
	        vm.profesional = entity;
		}

		function editar(){
			ProfesionalDialog.open(vm.profesional.id)
					   .then(function(){Profesional.get({id : vm.profesional.id}, load)});
		}
    }
})();
