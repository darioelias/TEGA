(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('AtributoDetailController', AtributoDetailController);

    AtributoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 
									   'entity', 'Atributo', 'AtributoDialog'];

    function AtributoDetailController($scope, $rootScope, $stateParams, previousState, 
									 entity, Atributo, AtributoDialog) {
        var vm = this;

        vm.previousState = previousState.name;
		vm.editar = editar;

		load(entity);

		function load(entity){
			vm.atributo = entity;
		}

        function editar(){
			AtributoDialog.open(vm.atributo.id)
					   .then(function(){Atributo.get({id : vm.atributo.id}, load)});
		}
    }
})();
