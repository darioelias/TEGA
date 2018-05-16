(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .directive('proyectofiltros', function () {
		    return {
				restrict: 'E',
				transclude: true,
		        scope: {
					criterio: '='
				},
				templateUrl: 'app/entities/proyecto/proyecto-filtros.html',
				controller: ['$scope','$translate', '$translatePartialLoader',
							 function filterController($scope, $translate, $translatePartialLoader) {

					$translatePartialLoader.addPart('proyecto');
                    $translatePartialLoader.addPart('global');
                    $translate.refresh();

					if($scope.criterio == null || Object.keys($scope.criterio).length == 0)
						$scope.criterio = {desde: null, hasta: null, codigo: null, publico: null, id: null};		

					$scope.cambioPublico = function(){
						if($scope.criterio.publico == "")
							$scope.criterio.publico = null;
					}	
				}]
		    };
		});
		
})();
