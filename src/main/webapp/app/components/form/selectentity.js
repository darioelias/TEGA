(function() {
    'use strict';

	angular
        .module('proyectoApp')
		.filter('filtroSelectorArray', [function($filter) {
			return function(inputArray, criterio){         
				if(!angular.isDefined(criterio) || criterio == ''){
					return inputArray;
				}         
				var data=[];
				criterio = criterio.toLowerCase();
				angular.forEach(inputArray, function(item){ 				           
					if((item[1] != null && item[1].toLowerCase().includes(criterio)) || 
					   (item.length < 3 || (item[2] != null && item[2].toLowerCase().includes(criterio)))){
						data.push(item);
					}
				});      
				return data;
				};
			}]);

	angular
        .module('proyectoApp')
		.filter('filtroSelectorListaObj', [function($filter) {
			return function(inputArray, criterio, campocodigo, campodetalle){         
				if(!angular.isDefined(criterio) || criterio == ''){
					return inputArray;
				}         
				var data=[];
				criterio = criterio.toLowerCase();
				angular.forEach(inputArray, function(item){ 				           
					if((item[campocodigo] != null && item[campocodigo].toLowerCase().includes(criterio)) || 
					   (item[campodetalle] != null && item[campodetalle].toLowerCase().includes(criterio))){
						data.push(item);
					}
				});      
				return data;
				};
			}]);

    angular
        .module('proyectoApp')
        .directive('selectentity', function () {
		    return {

		        scope: {
					destino: '=',
					campoid: '@',
					campocodigo: '@',
					campodetalle: '@',
					tr: '@',
					retornarid: '=?',
					opcional: '=?',
					servicio: '@',
					style:'@',
					sinlabel: '=?',
					singroup: '=?',
					retornarcompleto: '=?',
					watch: '=?'
				},
				templateUrl: '/selectentity.html',
				controller: ['$scope','$injector', function selectEntityController($scope, $injector) {
					
					$scope.foco = foco;
					$scope.cambio = cambio;
					$scope.refresh = refresh;
					$scope.iniciado = false;
					$scope.listaorigen = [];
					
					if($scope.style === undefined)
						$scope.style = 'width:100%';

					if($scope.retornarid === undefined){
						$scope.retornarid = false;			
					}					

					if($scope.opcional === undefined){
						$scope.opcional = true;			
					}
		
					if($scope.destino === undefined){
						$scope.destino = null;			
					}

					if($scope.campoid == null){
						$scope.campoid = "id";
					}

					if($scope.campocodigo == null){
						$scope.campocodigo = "codigo";
					}

					if($scope.campodetalle == null){
						$scope.campodetalle = "detalle";
					}

					if($scope.sinlabel == null)
						$scope.sinlabel = false;
					
					if($scope.singroup == null)
						$scope.singroup = false;

					if($scope.retornarcompleto == null)
						$scope.retornarcompleto = false;

					if($scope.watch === undefined)
						$scope.watch = false;	

									
					$scope.select = {obj: $scope.destino};

					if($scope.watch){
						$scope.$watch('destino', function() {
							$scope.select = {obj: $scope.destino};
						});
					}

					function refresh(texto){
						if($scope.iniciado){	
							$injector.invoke([$scope.servicio, function(servicio){ 
								if($scope.retornarcompleto)
									servicio.selectorCompleto({texto: texto}, onSuccess);
								else
									if($scope.retornarid)
										servicio.selectorMin({texto: texto}, onSuccess);
									else
										servicio.selector({texto: texto}, onSuccess);

								function onSuccess(data, headers){$scope.listaorigen = data}
							}]);
						}
					}

					function foco(isOpen){						
						if(isOpen && !$scope.iniciado){
							$scope.iniciado = true;
							refresh('');
						}
					}

					function cambio(obj){
						if(obj === undefined)
							$scope.destino = null;
						else
							if($scope.retornarid)
								$scope.destino = obj[0];
							else
								$scope.destino = obj;
					}


				}]
		    };
		});
		
})();
