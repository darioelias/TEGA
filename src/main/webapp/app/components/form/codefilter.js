(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .directive('codefilter', function () {
		    return {
				restrict: 'E',
		        scope: {
					codigo: '=',
					tr: '@'
				},
				templateUrl: '/codefilter.html',
				controller: ['$scope', function codeFilterController($scope) {
					if($scope.tr == null)
						$scope.tr = "general.codigo";
					
				}]
		    };
		});
		
})();
