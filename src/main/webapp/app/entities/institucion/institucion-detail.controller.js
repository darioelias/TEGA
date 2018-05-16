(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('InstitucionDetailController', InstitucionDetailController);

    InstitucionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 
										   'entity', 'Institucion', 'InstitucionDialog'];

    function InstitucionDetailController($scope, $rootScope, $stateParams, previousState, 
										 entity, Institucion, InstitucionDialog) {
        var vm = this;

        
        vm.previousState = previousState.name;
		vm.editar = editar;

		load(entity);

		function load(entity){
			vm.institucion = entity;
		}

		function editar(){
			InstitucionDialog.open(vm.institucion.id)
					   .then(function(){Institucion.get({id : vm.institucion.id}, load)});
		}
    }
})();
