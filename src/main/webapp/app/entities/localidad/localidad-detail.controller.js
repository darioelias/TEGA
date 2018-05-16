(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('LocalidadDetailController', LocalidadDetailController);

    LocalidadDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 
										 'entity', 'Localidad', 'LocalidadDialog'];

    function LocalidadDetailController($scope, $rootScope, $stateParams, previousState, 
									   entity, Localidad, LocalidadDialog) {
        var vm = this;

        vm.previousState = previousState.name;
		vm.editar = editar;

		load(entity);

		function load(entity){
        	vm.localidad = entity;
		}

		function editar(){
			LocalidadDialog.open(vm.localidad.id)
					   .then(function(){Localidad.get({id : vm.localidad.id}, load)});
		}
    }
})();
