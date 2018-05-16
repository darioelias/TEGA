(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('LocusDetailController', LocusDetailController);

    LocusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 
									  'entity', 'Locus', 'LocusDialog'];

    function LocusDetailController($scope, $rootScope, $stateParams, previousState, 
								   entity, Locus, LocusDialog) {
        var vm = this;

        vm.previousState = previousState.name;
		vm.editar = editar;

		load(entity);

		function load(entity){
        	vm.locus = entity;
			vm.locus.atributos = [];
		}

		function editar(){
			LocusDialog.open(vm.locus.id)
					   .then(function(){Locus.get({id : vm.locus.id}, load)});
		}
    }
})();
