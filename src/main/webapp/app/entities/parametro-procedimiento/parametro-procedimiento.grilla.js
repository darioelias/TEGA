(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .directive('parametroprocedimientogrilla', function () {
		    return {
				restrict: 'E',
				transclude: true,
		        scope: {
					parametros: '=',
					readonly: '=?'
				},
				templateUrl: 'app/entities/parametro-procedimiento/parametro-procedimiento-grilla.html',
				controller: ['$scope','$translate', '$translatePartialLoader',
							 function filterController($scope, $translate, $translatePartialLoader) {

					$translatePartialLoader.addPart('parametroProcedimiento');
                    $translatePartialLoader.addPart('global');
                    $translate.refresh();

					if($scope.readonly === undefined)
						$scope.readonly = false;			
					
		
				}]
		    };
		});
		
})();
