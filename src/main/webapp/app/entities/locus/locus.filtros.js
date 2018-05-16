(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .directive('locusfiltros', function () {
		    return {
				restrict: 'E',
				transclude: true,
		        scope: {
					criterio: '='
				},
				templateUrl: 'app/entities/locus/locus-filtros.html',
				controller: ['$scope','$translate', '$translatePartialLoader',
							 function filterController($scope, $translate, $translatePartialLoader) {

					$translatePartialLoader.addPart('locus');
                    $translatePartialLoader.addPart('global');
                    $translate.refresh();

					if($scope.criterio == null || Object.keys($scope.criterio).length == 0)
						$scope.criterio = {codigo: null, idAnalisisGenotipos: null, publico: null, id: null};	

					$scope.cambioPublico = function(){
						if($scope.criterio.publico == "")
							$scope.criterio.publico = null;
					}				
				}]
		    };
		});
		
})();
