(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('ParametroProcedimientoDetailController', ParametroProcedimientoDetailController);

    ParametroProcedimientoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 
										 'entity', 'ParametroProcedimiento', 'ParametroProcedimientoDialog'];

    function ParametroProcedimientoDetailController($scope, $rootScope, $stateParams, previousState, 
									   entity, ParametroProcedimiento, ParametroProcedimientoDialog) {
        var vm = this;

        vm.previousState = previousState.name;
		vm.editar = editar;

		load(entity);

		function load(entity){
	        vm.parametro = entity;
		}

		function editar(){
			ParametroProcedimientoDialog.open(vm.parametro.id)
					   .then(function(){ParametroProcedimiento.get({id : vm.parametro.id}, load)});
		}
    }
})();
