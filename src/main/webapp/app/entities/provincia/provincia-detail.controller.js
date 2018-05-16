(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('ProvinciaDetailController', ProvinciaDetailController);

    ProvinciaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 
										 'entity', 'Provincia', 'ProvinciaDialog'];

    function ProvinciaDetailController($scope, $rootScope, $stateParams, previousState, 
									   entity, Provincia, ProvinciaDialog) {
        var vm = this;

        vm.previousState = previousState.name;
		vm.editar = editar;

		load(entity);

		function load(entity){
	        vm.provincia = entity;
		}

		function editar(){
			ProvinciaDialog.open(vm.provincia.id)
					   .then(function(){Provincia.get({id : vm.provincia.id}, load)});
		}
    }
})();
