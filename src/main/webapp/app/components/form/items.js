(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .directive('items', function () {
		    return {
				restrict: 'E',
		        scope: {
					listaorigen: '=',
					listadestino: '=',
					campoid: '@',
					campocodigo: '@',
					campodetalle: '@',
					tr: '@',
					readonly: '=?',
					solodetalle: '=?',
					change: '=?',
					servicio: '@',
					filtros: '@',
				},
				templateUrl: '/items.html',
				controller: ['$scope','$uibModal','BusquedaItems', function itemsController($scope, $uibModal, BusquedaItems) {

					$scope.sel = null;
				  	$scope.agregar = agregar;
					$scope.eliminar = eliminar;
					$scope.buscar = buscar;

					$scope.buscarVisible = $scope.servicio != undefined && $scope.servicio != null;
					
					if($scope.change === undefined)
						$scope.change = null;

					if($scope.readonly === undefined){
						$scope.readonly = false;			
					}

					if($scope.solodetalle === undefined){
						$scope.solodetalle = false;			
					}

					if($scope.listadestino === undefined){
						$scope.listadestino = [];			
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

					$scope.predicado = $scope.campoid;
					$scope.ascendente = true;
						

					function agregar () {
						if ($scope.sel != null){
							var selList = $scope.listadestino.filter(function(ele){return ele[$scope.campoid] == $scope.sel[$scope.campoid]});						
							if(selList.length == 0){
								$scope.listadestino.push($scope.sel);			
							}
							$scope.sel = null;

							if($scope.change != null)
								$scope.change();
						}

					}

					function eliminar (elemento) {
						if (elemento != null){
							$scope.listadestino.splice( $scope.listadestino.indexOf(elemento), 1);
							if($scope.change != null)
								$scope.change();
						}
	
					}

					function buscar(){

						BusquedaItems.open(	$scope.listadestino,
												$scope.servicio, 
												$scope.tr, 
												$scope.filtros, 
												$scope.campoid,
												$scope.campocodigo,
												$scope.campodetalle,
												$scope.solodetalle)
								.then(function(lista){$scope.listadestino = lista});

					}
				}]
		    };
		});
		
})();
