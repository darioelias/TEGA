(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .directive('headeraccordion', function () {
		    return {
				restrict: 'E',
		        scope: {
					tr: '@',
					disabled: '='
				},
				templateUrl: '/headeraccordion.html',
				controller: ['$scope', function headerAccordionController($scope) {
						if($scope.disable == null)
							$scope.disable = false;
					}]
		    };
		});
		
})();
