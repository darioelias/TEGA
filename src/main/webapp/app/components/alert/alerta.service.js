(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .provider('AlertaService', AlertaService);

    function AlertaService () {

        this.$get = getService;

		getService.$inject = ['$translate'];
        function getService ($translate) {

            return {agregar: agregar,
					error: error,
					info: info,
					warning: warning,
					success: success};

            function agregar(alertas, msj, tipo) {
				if(tipo == null)
					tipo = 'info';
		
				msj = $translate.instant(msj);

                alertas.push({msj: msj, tipo: tipo});
            }

			function error(alertas, msj){
				this.agregar(alertas, msj,'danger');
			}

			function info(alertas, msj){
				this.agregar(alertas, msj,'info');
			}

			function warning(alertas, msj){
				this.agregar(alertas, msj,'warning');
			}

			function success(alertas, msj){
				this.agregar(alertas, msj,'success');
			}

        }
    }
})();
