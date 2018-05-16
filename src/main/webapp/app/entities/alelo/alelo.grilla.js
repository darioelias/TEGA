(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .directive('alelogrilla', function () {
		    return {
				restrict: 'E',
				transclude: true,
		        scope: {
					lista: '=',
					readonly: '=?'
				},
				templateUrl: 'app/entities/alelo/alelo-grilla.html',
				controller: ['$scope','$translate', '$translatePartialLoader','AlertaService',
							 function filterController($scope, $translate, $translatePartialLoader, AlertaService) {

					if($scope.readonly === undefined)
						$scope.readonly = false;

					$translatePartialLoader.addPart('alelo');
                    $translatePartialLoader.addPart('global');
                    $translate.refresh();

					$scope.alertas = [];
					$scope.eliminarAlelo = eliminarAlelo;
					$scope.nuevoAlelo = nuevoAlelo;
					$scope.editarAlelo = editarAlelo;
					$scope.aplicarAlelo = aplicarAlelo;
					$scope.rangoIndices = rangoIndices;
					$scope.alelo = null;
					$scope.index = null;

					$scope.predicado = "locus.codigo";
					$scope.ascendente = true;

					nuevoAlelo();
					
					function eliminarAlelo(alelo){
						$scope.lista.splice($scope.lista.indexOf(alelo), 1);
					}

					function nuevoAlelo(){
						$scope.alelo = {id: null, locus: null, indice: null, valor: null, publico: false};
						$scope.index = null;					
					}

					function editarAlelo(alelo){
						
						nuevoAlelo();
						
						$scope.alelo.id 	= alelo.id;
						$scope.alelo.locus 	= alelo.locus;
						$scope.alelo.indice = alelo.indice;
						$scope.alelo.valor 	= alelo.valor;
						$scope.alelo.publico = alelo.publico;

						$scope.index = $scope.lista.indexOf(alelo);
					}

					function aplicarAlelo(){
						
						$scope.alertas = [];

						if($scope.alelo.locus == null){
							AlertaService.error($scope.alertas,'proyectoApp.alelo.locus-obligatorio');
							return;	
						}

						if($scope.alelo.indice == null){
							AlertaService.error($scope.alertas,'proyectoApp.alelo.indice-obligatorio');
							return;	
						}

						if($scope.alelo.indice > $scope.alelo.locus.ploidia){
							AlertaService.error($scope.alertas,'proyectoApp.alelo.indice-ploidia');
							return;	
						}

						var enc = false;
						$scope.lista.forEach(function(a, i){
							if(a.locus.id == $scope.alelo.locus.id && 
							   a.indice == $scope.alelo.indice && 
							   ($scope.index == null || $scope.index != i))							
								enc = true;
						});

						if(enc){							
							AlertaService.error($scope.alertas,'proyectoApp.alelo.locus-indice-repetido');
							return;	
						}

						if($scope.index != null)
							$scope.lista[$scope.index] = $scope.alelo;
						else
							$scope.lista.push($scope.alelo);

						nuevoAlelo();
					}

					function rangoIndices(locus){
						if(locus == null)
							return [];
						return _.range(1, locus.ploidia+1);
					}
									
				}]
		    };
		});
		
})();
