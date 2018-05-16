(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .directive('atributogrilla', function () {
		    return {
				restrict: 'E',
				transclude: true,
		        scope: {
					atributos: '=',
					servicio: '@',
					entidad: '@',
					idobj: '=',
					readonly: '=?',
					padre: '=?',
				},
				templateUrl: 'app/entities/atributo/atributo-grilla.html',
				controller: ['$scope','$injector', 'Atributo',
							 function filterController($scope, $injector, Atributo) {

					$scope.atributosGrd = [];
					$scope.predicado = "atributo.codigo";
					$scope.ascendente = true;

					if($scope.readonly === undefined)
						$scope.readonly = false;

					if($scope.readonly && $scope.padre){
						$scope.$watch('padre', function() {
							loadAll();
						});
					}else{
						loadAll();
					}

					function loadAll(){

						if($scope.idobj != null){
							$injector.invoke([$scope.servicio, function(servicio){ 
								return servicio.atributos({id: $scope.idobj}, function(data){cargarAtributos(data)});
							}]);
						}else{
							cargarAtributos([]);
						}
					}

					function cargarAtributos(atributosObj){
						var atributosEntidad = Atributo.entidad({entidad: $scope.entidad},function(atributosEntidad){
								var atributos = [];

								atributosEntidad.forEach(function(a){
									var at = {atributo:a, valor:a.valor, id:null};
									var aobj = atributosObj.find(function(ao){return ao.atributo.id == a.id});
									if(aobj){
										at.valor = aobj.valor;
										at.id = aobj.id;
									}
									atributos.push(at);
								});		
	
								$scope.atributos = atributos;

							});
					}
				}]
		    };
		});
		
})();
