(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .directive('filter', function () {
		    return {
				restrict: 'E',
		        scope: {
					valor: '=',
					tr: '@',
					tipo: '@'
				},
				templateUrl: '/filter.html',
				controller: ['$scope', function filterController($scope) {
					if($scope.tipo == null)
						$scope.tipo = "text";
					
				}]
		    };
		});
		
})();
