(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .directive('btntabla', function () {
		    return {
				restrict: 'E',
		        scope: {
					actualizar: '=',
					lista: '=',
					camposexp: '@',
					titulo: '@',
					importar: '=',
					campoid: '@',
					campocodigo: '@',
					campodetalle: '@',
					trsel: '@',
					solodetalle: '=?',
					servicio: '@',
					filtros: '@',
					rol:'@',
					urlexpatributos: '@',
					getparametrosquery: '=?'
				},
				templateUrl: '/btntabla.html',
				controller: ['$scope', 'DownloadService',function BtnTablaController($scope, DownloadService) {
		
					if($scope.campoid == null){
						$scope.campoid = "id";
					}

					if($scope.campocodigo == null){
						$scope.campocodigo = "codigo";
					}

					if($scope.campodetalle == null){
						$scope.campodetalle = "detalle";
					}

					if($scope.solodetalle === undefined){
						$scope.solodetalle = false;			
					}

					$scope.expAtributos = true;	
					if($scope.urlexpatributos === undefined){
						$scope.expAtributos = false;			
					}

					$scope.exportarAtributos = exportarAtributos;

					function exportarAtributos(){
						var parametros = $scope.getparametrosquery();
						DownloadService.download($scope.urlexpatributos, parametros);
					}
					
					
				}]
		    };
		});
		
})();
