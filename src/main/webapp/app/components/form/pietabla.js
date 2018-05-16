(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .directive('pietabla', function () {
		    return {
				restrict: 'E',
		        scope: {
					page: '=',
					total: '=',
					itemspage: '=',
					change: '='
				},
				templateUrl: '/pietabla.html',
				controller: ['$scope', function pieTablaController($scope) {
					$scope.itemspageAux = $scope.itemspage;

					$scope.changeAux = function (){
						$scope.itemspage = $scope.itemspageAux;
						$scope.change($scope.itemspage, $scope.page);
					}

					$scope.keyPress = function (keyEvent){
						if (keyEvent.which === 13) //Enter
							$scope.changeAux();
					}
					
				}]
		    };
		});
		
})();
