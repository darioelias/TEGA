(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('ProyectoDetailController', ProyectoDetailController);

    ProyectoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 
										'entity', 'Proyecto', 'Archivo', 'ProyectoDialog'];

    function ProyectoDetailController($scope, $rootScope, $stateParams, previousState, 
									  entity, Proyecto, Archivo, ProyectoDialog) {
        var vm = this;

        vm.previousState = previousState.name;
		vm.editar = editar;

		load(entity);

		function load(entity){
		    vm.proyecto = entity;
			vm.proyecto.atributos = [];
			vm.proyecto.archivos = Archivo.proyecto({id: vm.proyecto.id});
		}

		function editar(){
			ProyectoDialog.open(vm.proyecto.id)
					   .then(function(){Proyecto.get({id : vm.proyecto.id}, load)});
		}
    }
})();
