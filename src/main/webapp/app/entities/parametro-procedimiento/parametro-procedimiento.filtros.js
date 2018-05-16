(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .directive('parametroprocedimientofiltros', function () {
		    return {
				restrict: 'E',
				transclude: true,
		        scope: {
					criterio: '='
				},
				templateUrl: 'app/entities/parametro-procedimiento/parametro-procedimiento-filtros.html',
				controller: ['$scope','$translate', '$translatePartialLoader',
							 function filterController($scope, $translate, $translatePartialLoader) {

					$translatePartialLoader.addPart('parametroProcedimiento');
                    $translatePartialLoader.addPart('global');
                    $translate.refresh();

					if($scope.criterio == null || Object.keys($scope.criterio).length == 0)
						$scope.criterio = {codigo: null, detalle: null, valor: null, id: null,
										  idCategoria: null, idProcedimiento: null};	
		
				}]
		    };
		});
		
})();
