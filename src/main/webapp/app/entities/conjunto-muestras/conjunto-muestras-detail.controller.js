(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('ConjuntoMuestrasDetailController', ConjuntoMuestrasDetailController);

    ConjuntoMuestrasDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 
													'ConjuntoMuestras', 'Muestra', 'ConjuntoMuestrasDialog'];

    function ConjuntoMuestrasDetailController($scope, $rootScope, $stateParams, previousState, entity, 
												  ConjuntoMuestras, Muestra, ConjuntoMuestrasDialog) {
        var vm = this;

        vm.previousState = previousState.name;
	    vm.editar = editar;

		load(entity);

		function load(entity){
        	vm.conjuntoMuestras = entity;
			vm.conjuntoMuestras.atributos = [];
			vm.conjuntoMuestras.muestras = Muestra.conjuntoMuestras({id:vm.conjuntoMuestras.id});
		}

		function editar(){
			ConjuntoMuestrasDialog.open(vm.conjuntoMuestras.id)
					   .then(function(){ConjuntoMuestras.get({id : vm.conjuntoMuestras.id}, load)});
		}
    }
})();
