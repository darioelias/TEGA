(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .directive('alelofiltros', function () {
		    return {
				restrict: 'E',
				transclude: true,
		        scope: {
					criterio: '='
				},
				templateUrl: 'app/entities/alelo/alelo-filtros.html',
				controller: ['$scope','$translate', '$translatePartialLoader',
							 function filterController($scope, $translate, $translatePartialLoader) {

					$translatePartialLoader.addPart('alelo');
                    $translatePartialLoader.addPart('global');
                    $translate.refresh();

					if($scope.criterio == null || Object.keys($scope.criterio).length == 0)
						$scope.criterio = {idLocus: null, idMuestra: null, indice: null, valor: null, publico: null, id: null};	

					$scope.cambioPublico = function(){
						if($scope.criterio.publico == "")
							$scope.criterio.publico = null;
					}			
				}]
		    };
		});
		
})();
