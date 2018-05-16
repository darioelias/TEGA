(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('Pregunta', Pregunta)
        .controller('PreguntaController',PreguntaController);

    PreguntaController.$inject = ['$uibModalInstance', 'parametros'];

    function PreguntaController($uibModalInstance, parametros) {
        var vm = this;

        vm.aceptar = aceptar;
		vm.cancelar = cancelar;
		vm.parametros = parametros;

        function cancelar () {
            $uibModalInstance.dismiss('cancel');
        }

        function aceptar () {
            $uibModalInstance.close(true);
        }
    }

	function Pregunta() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal'];

		function getService($uibModal){
		
			return {open: open};

			function open(parametros){

				if(!parametros.cancelarMsj)
					parametros.cancelarMsj = "entity.action.cancel";

				if(!parametros.aceptarMsj)
					parametros.aceptarMsj = "entity.action.accept";

				if(!parametros.preguntaParam)
					parametros.preguntaParam = {};

				return $uibModal.open({
						        templateUrl: '/pregunta.html',
						        controller: 'PreguntaController',
						        controllerAs: 'vm',
						        size: 'md',
						        resolve: {
						            parametros: function() {return parametros}
								}
                   	   }).result;
			}
		}
	}
})();
