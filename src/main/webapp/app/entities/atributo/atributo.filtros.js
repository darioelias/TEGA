(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .directive('atributofiltros', function () {
		    return {
				restrict: 'E',
				transclude: true,
		        scope: {
					criterio: '='
				},
				templateUrl: 'app/entities/atributo/atributo-filtros.html',
				controller: ['$scope','$translate', '$translatePartialLoader',
							 function filterController($scope, $translate, $translatePartialLoader) {

					$translatePartialLoader.addPart('atributo');
                    $translatePartialLoader.addPart('global');
                    $translate.refresh();

					if($scope.criterio == null || Object.keys($scope.criterio).length == 0)
						$scope.criterio = {codigo: null, detalle: null, id: null, entidad: null};	

					$scope.cambioEntidad = function(){
						if($scope.criterio.entidad == "")
							$scope.criterio.entidad = null;
					}					
				}]
		    };
		});
		
})();
