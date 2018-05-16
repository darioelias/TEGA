(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .directive('modorecoleccionfiltros', function () {
		    return {
				restrict: 'E',
				transclude: true,
		        scope: {
					criterio: '='
				},
				templateUrl: 'app/entities/modo-recoleccion/modo-recoleccion-filtros.html',
				controller: ['$scope','$translate', '$translatePartialLoader',
							 function filterController($scope, $translate, $translatePartialLoader) {

					$translatePartialLoader.addPart('modoRecoleccion');
                    $translatePartialLoader.addPart('global');
                    $translate.refresh();

					if($scope.criterio == null || Object.keys($scope.criterio).length == 0)
						$scope.criterio = {codigo: null, nombre: null, id: null};				
				}]
		    };
		});
		
})();
