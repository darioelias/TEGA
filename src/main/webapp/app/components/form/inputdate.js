(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .directive('inputdate', function () {
		    return {
				restrict: 'E',
		        scope: {
					valor: '=',
					label: '@',
					tr: '@',
					readonly: '=?',
					validar: '=?'
				},
				templateUrl: '/inputdate.html',
				controller: ['$scope', function inputDateController($scope) {
					
					$scope.open = false;
					$scope.openCalendar = openCalendar;
					$scope.dateformat = "yyyy-MM-dd";

					if($scope.valor === undefined){
						$scope.valor = {};			
					}

					if($scope.readonly === undefined){
						$scope.readonly = false;			
					}

					if($scope.validar === undefined){
						$scope.validar = true;			
					}

					if($scope.label == null){
						$scope.label = "Fecha";
					}


					$scope.opcionesDate = { allowInvalid: $scope.validar };


					function openCalendar(){
						$scope.open = true;					
					}

				}]
		    };
		});
		
})();
