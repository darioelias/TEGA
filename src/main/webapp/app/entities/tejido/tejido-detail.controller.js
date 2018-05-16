(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('TejidoDetailController', TejidoDetailController);

    TejidoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 
									  'entity', 'Tejido', 'TejidoDialog'];

    function TejidoDetailController($scope, $rootScope, $stateParams, previousState, 
								    entity, Tejido, TejidoDialog) {
        var vm = this;

        vm.previousState = previousState.name;
		vm.editar = editar;

		load(entity);

		function load(entity){
	        vm.tejido = entity;
		}

		function editar(){
			TejidoDialog.open(vm.tejido.id)
					   .then(function(){Tejido.get({id : vm.tejido.id}, load)});
		}
    }
})();
