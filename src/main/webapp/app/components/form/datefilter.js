(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .directive('datefilter', function () {
		    return {
				restrict: 'E',
		        scope: {
					desde: '=',
					hasta: '=',
					trdesde: '@',
					trhasta: '@'
				},
				templateUrl: '/datefilter.html',
				controller: ['$scope', function dateFilterController($scope) {
					if($scope.trdesde === undefined)
						$scope.trdesde = "general.desde";

					if($scope.trhasta === undefined)
						$scope.trhasta = "general.hasta";
					
				}]
		    };
		});
		
})();
