(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('ProcedimientoDetailController', ProcedimientoDetailController);

    ProcedimientoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 
									   'entity', 'Procedimiento', 'ProcedimientoDialog', 'Archivo'];

    function ProcedimientoDetailController($scope, $rootScope, $stateParams, previousState, 
									 entity, Procedimiento, ProcedimientoDialog, Archivo) {
        var vm = this;

        vm.previousState = previousState.name;
		vm.editar = editar;

		load(entity);

		function load(entity){
			vm.procedimiento = entity;
			vm.procedimiento.archivos = Archivo.procedimiento({id: vm.procedimiento.id});
		}

        function editar(){
			ProcedimientoDialog.open(vm.procedimiento.id)
					   .then(function(){Procedimiento.get({id : vm.procedimiento.id}, load)});
		}
    }
})();
