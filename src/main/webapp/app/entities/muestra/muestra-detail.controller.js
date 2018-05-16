(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('MuestraDetailController', MuestraDetailController);

    MuestraDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'Principal',
										'entity', 'Muestra', 'Proyecto', 'Archivo','Alelo','ConjuntoMuestras',
										'MuestraDialog'];

    function MuestraDetailController($scope, $rootScope, $stateParams, previousState, Principal,
									entity, Muestra, Proyecto, Archivo, Alelo, ConjuntoMuestras,
									MuestraDialog) {
        var vm = this;

        vm.previousState = previousState.name;
		vm.editar = editar;

		load(entity);

		function load(entity){
			vm.muestra = entity;
			vm.muestra.atributos = [];
			vm.muestra.proyectos = Proyecto.muestra({id: vm.muestra.id});
			vm.muestra.archivos = Archivo.muestra({id: vm.muestra.id});

			if(Principal.hasAnyAuthority(['ROLE_USER_EXTENDED'])){
				vm.muestra.alelos = Alelo.muestra({id:vm.muestra.id});
				vm.muestra.conjuntosMuestras = ConjuntoMuestras.muestra({id:vm.muestra.id});
			}
		}

		function editar(){
			MuestraDialog.open(vm.muestra.id)
					   .then(function(){Muestra.get({id : vm.muestra.id}, load)});
		}
    }
})();
