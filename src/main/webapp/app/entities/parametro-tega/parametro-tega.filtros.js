(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .directive('parametrotegafiltros', function () {
		    return {
				restrict: 'E',
				transclude: true,
		        scope: {
					criterio: '='
				},
				templateUrl: 'app/entities/parametro-tega/parametro-tega-filtros.html',
				controller: ['$scope','$translate', '$translatePartialLoader',
							 function filterController($scope, $translate, $translatePartialLoader) {

					$translatePartialLoader.addPart('parametroTega');
                    $translatePartialLoader.addPart('global');
                    $translate.refresh();

					if($scope.criterio == null || Object.keys($scope.criterio).length == 0)
						$scope.criterio = {codigo: null, detalle: null, valor: null, id: null};	
		
				}]
		    };
		});
		
})();
