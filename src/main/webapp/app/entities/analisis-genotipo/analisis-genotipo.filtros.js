(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .directive('analisisgenotipofiltros', function () {
		    return {
				restrict: 'E',
				transclude: true,
		        scope: {
					criterio: '='
				},
				templateUrl: 'app/entities/analisis-genotipo/analisis-genotipo-filtros.html',
				controller: ['$scope','$translate', '$translatePartialLoader',
							 function filterController($scope, $translate, $translatePartialLoader) {

					$translatePartialLoader.addPart('analisisGenotipo');
                    $translatePartialLoader.addPart('global');
                    $translate.refresh();

					if($scope.criterio == null || Object.keys($scope.criterio).length == 0)
						$scope.criterio = {codigo: null, desde: null, hasta: null, 
											idProyecto: null, id: null, estado: null,
											publico: null, idProcedimiento: null};

					$scope.cambioEstado = function(){
						if($scope.criterio.estado == "")
							$scope.criterio.estado = null;
					}	

					$scope.cambioPublico = function(){
						if($scope.criterio.publico == "")
							$scope.criterio.publico = null;
					}				
				}]
		    };
		});
		
})();
