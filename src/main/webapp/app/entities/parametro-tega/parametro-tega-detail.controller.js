(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('ParametroTegaDetailController', ParametroTegaDetailController);

    ParametroTegaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 
										 'entity', 'ParametroTega', 'ParametroTegaDialog'];

    function ParametroTegaDetailController($scope, $rootScope, $stateParams, previousState, 
									   entity, ParametroTega, ParametroTegaDialog) {
        var vm = this;

        vm.previousState = previousState.name;
		vm.editar = editar;

		load(entity);

		function load(entity){
	        vm.parametro = entity;
		}

		function editar(){
			ParametroTegaDialog.open(vm.parametro.id)
					   .then(function(){ParametroTega.get({id : vm.parametro.id}, load)});
		}
    }
})();
