(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('RegionDetailController', RegionDetailController);

    RegionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 
									  'entity', 'Region', 'RegionDialog'];

    function RegionDetailController($scope, $rootScope, $stateParams, previousState, 
									entity, Region, RegionDialog) {
        var vm = this;

        vm.previousState = previousState.name;
		vm.editar = editar;

		load(entity);

		function load(entity){
	        vm.region = entity;
		}

		function editar(){
			RegionDialog.open(vm.region.id)
					   .then(function(){Region.get({id : vm.region.id}, load)});
		}
    }
})();
