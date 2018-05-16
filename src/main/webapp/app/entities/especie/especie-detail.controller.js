(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('EspecieDetailController', EspecieDetailController);

    EspecieDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 
									   'entity', 'Especie', 'EspecieDialog'];

    function EspecieDetailController($scope, $rootScope, $stateParams, previousState, 
									 entity, Especie, EspecieDialog) {
        var vm = this;

        vm.previousState = previousState.name;
		vm.editar = editar;

		load(entity);

		function load(entity){
			vm.especie = entity;
		}

        function editar(){
			EspecieDialog.open(vm.especie.id)
					   .then(function(){Especie.get({id : vm.especie.id}, load)});
		}
    }
})();
