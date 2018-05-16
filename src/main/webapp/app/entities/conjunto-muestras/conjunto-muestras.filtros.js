(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .directive('conjuntomuestrasfiltros', function () {
		    return {
				restrict: 'E',
				transclude: true,
		        scope: {
					criterio: '='
				},
				templateUrl: 'app/entities/conjunto-muestras/conjunto-muestras-filtros.html',
				controller: ['$scope','$translate', '$translatePartialLoader',
							 function filterController($scope, $translate, $translatePartialLoader) {

					$translatePartialLoader.addPart('conjuntoMuestras');
                    $translatePartialLoader.addPart('global');
                    $translate.refresh();

					if($scope.criterio == null || Object.keys($scope.criterio).length == 0)
						$scope.criterio = {codigo: null, idAnalisisGenotipos: null, 
										  id: null, publico: null};	

					$scope.cambioPublico = function(){
						if($scope.criterio.publico == "")
							$scope.criterio.publico = null;
					}		
				}]
		    };
		});
		
})();
