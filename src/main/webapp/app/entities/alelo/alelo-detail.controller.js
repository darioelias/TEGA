(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('AleloDetailController', AleloDetailController);

    AleloDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 
									'entity', 'Alelo','AleloDialog'];

    function AleloDetailController($scope, $rootScope, $stateParams, previousState, 
									entity, Alelo, AleloDialog) {
        var vm = this;

        vm.previousState = previousState.name;
		vm.editar = editar;

        load(entity);

		function load(entity){
			vm.alelo = entity;
		}

        function editar(){
			AleloDialog.open(vm.alelo.id)
					   .then(function(){Alelo.get({id : vm.alelo.id}, load)});
		}
    }
})();
