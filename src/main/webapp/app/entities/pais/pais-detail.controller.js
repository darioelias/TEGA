(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('PaisDetailController', PaisDetailController);

    PaisDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 
									'Pais', 'PaisDialog'];

    function PaisDetailController($scope, $rootScope, $stateParams, previousState, entity, 
								  Pais, PaisDialog) {
        var vm = this;

        vm.previousState = previousState.name;
		vm.editar = editar;

		load(entity);

		function load(entity){
	        vm.pais = entity;
		}

		function editar(){
			PaisDialog.open(vm.pais.id)
					   .then(function(){Pais.get({id : vm.pais.id}, load)});
		}
    }
})();
