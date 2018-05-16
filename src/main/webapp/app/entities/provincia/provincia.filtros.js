(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .directive('provinciafiltros', function () {
		    return {
				restrict: 'E',
				transclude: true,
		        scope: {
					criterio: '='
				},
				templateUrl: 'app/entities/provincia/provincia-filtros.html',
				controller: ['$scope','$translate', '$translatePartialLoader',
							 function filterController($scope, $translate, $translatePartialLoader) {

					$translatePartialLoader.addPart('provincia');
                    $translatePartialLoader.addPart('global');
                    $translate.refresh();

					if($scope.criterio == null || Object.keys($scope.criterio).length == 0)
						$scope.criterio = {codigo: null, idPais: null, nombre: null, id: null};			
				}]
		    };
		});
		
})();
