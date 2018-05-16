(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .directive('alerta', function () {
		    return {
				restrict: 'E',
		        scope: {
					lista: '=',
					tiempo: '@'
				},
				templateUrl: '/alerta.html',
				controller: ['$scope', function alertaController($scope) {
					
					if(!$scope.tiempo)
						$scope.tiempo = "5000";

					$scope.cerrarAlerta = function(index) {
						$scope.lista.splice(index, 1);
					  };
					
				}]
		    };
		});
		
})();
