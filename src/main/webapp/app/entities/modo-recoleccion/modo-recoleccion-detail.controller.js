(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('ModoRecoleccionDetailController', ModoRecoleccionDetailController);

    ModoRecoleccionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 
											   'entity', 'ModoRecoleccion', 'ModoRecoleccionDialog'];

    function ModoRecoleccionDetailController($scope, $rootScope, $stateParams, previousState, 
											 entity, ModoRecoleccion, ModoRecoleccionDialog) {
        var vm = this;


        vm.previousState = previousState.name;
		vm.editar = editar;

		load(entity);

		function load(entity){
	        vm.modoRecoleccion = entity;
		}

		function editar(){
			ModoRecoleccionDialog.open(vm.modoRecoleccion.id)
					   .then(function(){ModoRecoleccion.get({id : vm.modoRecoleccion.id}, load)});
		}
    }
})();
