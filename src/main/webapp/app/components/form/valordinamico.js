(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .directive('validvalordinamico', function () {

			return {
				require: 'ngModel',
				link: function (scope, elem, attr, ngModel) {

					ngModel.$parsers.unshift(function(value) {
						var valido = false;

						if(value){
							valido = true;

							if(scope.tipo == 'NUMERICO'){
								valido = Number(parseFloat(value))== value;
							}

							if(scope.tipo == 'ENTERO'){
								valido = Number(parseInt(value))== value && value.indexOf(".") == -1;
							}

						}else{
							 if(scope.tipo == 'CARACTER'){
								valido = true;
							}
						}
						ngModel.$setValidity('valorDinamicoInvalido', valido);
						return valido ? value : undefined;
					});

				}
			}

		});


    angular
        .module('proyectoApp')
        .directive('valordinamico', function () {
		    return {
				restrict: 'E',
		        scope: {
					valor: '=',
					tipo: '=',
					readonly: '=?',
					setvalordefecto: '=?'
				},
				templateUrl: '/valordinamico.html',
				controller: ['$scope','DateUtils','$timeout', function valorDinamicoController($scope,DateUtils,$timeout) {
	
					$scope.openCalendar = openCalendar;
					$scope.dateformat = "yyyy-MM-dd";
					$scope.selectDate = selectDate;
					$scope.popupCalendar = {opened: false};

					if($scope.tipo == "FECHA"){
						$scope.valorFecha = DateUtils.convertLocalDateFromServer($scope.valor);
					}else{
						$scope.valorFecha = new Date();
					}

					if($scope.readonly === undefined)
						$scope.readonly = false;

					if($scope.setvalordefecto === undefined)
						$scope.setvalordefecto = false;

					if($scope.setvalordefecto){
						$scope.$watch('tipo', function(newValue, oldValue) {
							if(newValue != oldValue){
								$scope.valorFecha = new Date();
								
								if($scope.tipo == "LOGICO"){
									$scope.valor = "0";
								}else if($scope.tipo == "CARACTER"){
									$scope.valor = "";
								}else if($scope.tipo == "NUMERICO"){
									$scope.valor = "0.00";
								}else if($scope.tipo == "ENTERO"){
									$scope.valor = "0";
								}else{
									selectDate($scope.valorFecha);
								}
							}
						});	
					}

					function selectDate(dt) {
						if($scope.tipo == "FECHA")
							$scope.valor = DateUtils.convertLocalDateToServer(dt);
					}


					function openCalendar(){
						$scope.popupCalendar.opened = true;				
					}
			
				}]
		    };
		});
		
})();
