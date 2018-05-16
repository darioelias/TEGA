(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('CategoriaParametroProcedimientoDetailController', CategoriaParametroProcedimientoDetailController);

    CategoriaParametroProcedimientoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 
									   'entity', 'CategoriaParametroProcedimiento', 'CategoriaParametroProcedimientoDialog'];

    function CategoriaParametroProcedimientoDetailController($scope, $rootScope, $stateParams, previousState, 
									 entity, CategoriaParametroProcedimiento, CategoriaParametroProcedimientoDialog) {
        var vm = this;

        vm.previousState = previousState.name;
		vm.editar = editar;

		load(entity);

		function load(entity){
			vm.categoriaParametroProcedimiento = entity;
		}

        function editar(){
			CategoriaParametroProcedimientoDialog.open(vm.categoriaParametroProcedimiento.id)
					   .then(function(){CategoriaParametroProcedimiento.get({id : vm.categoriaParametroProcedimiento.id}, load)});
		}
    }
})();
