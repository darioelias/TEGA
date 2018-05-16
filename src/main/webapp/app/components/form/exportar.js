(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .directive('exportar', function () {
		    return {
				restrict: 'E',
		        scope: {
					lista: '=',
					campos: '@',
					titulo: '@',
					matriz: '=?',
					separador: '=?',
					nulo: '=?'
				},
				templateUrl: '/exportar.html',
				controller: ['$scope','ParametroTega','Principal',
				function ExportarController($scope, ParametroTega, Principal) {
				
					if($scope.matriz === undefined)
						$scope.matriz = false;

					if($scope.separador === undefined){
						if(Principal.hasAnyAuthority(['ROLE_USER_EXTENDED'])){
							ParametroTega.obtener({codigo: "SEPARADOR_IMP_EXP_ARCHIVOS"}, 
							 				   function(p){$scope.separador = p.valor});
						}else{
							$scope.separador = ",";
						}
					}
						

					if($scope.nulo === undefined)
						$scope.nulo = null;

					$scope.procesarLista = function(){
						
						var listaFinal = [];

						if($scope.matriz){

							for(var i = 1; i < $scope.lista.length; i++){

								var obj = {};
								for(var j = 0; j < $scope.lista[i].length; j++){
									if($scope.lista[i][j] == null)
										obj[$scope.lista[0][j]] = $scope.nulo;
									else
										obj[$scope.lista[0][j]] = $scope.lista[i][j];
								}
								listaFinal.push(obj);
							}

						}else{

							var campos = [];
							$scope.campos.split(",").forEach(function(c){
								campos.push(c.replace(/\s/g,""));
							});			

							$scope.lista.forEach(function(i){
								var obj = {};
								campos.forEach(function(c){
									c = c.trim();
									try{
										obj[c.replace(/\./g,"_")] = eval("i."+c);
									}catch(err){
										obj[c.replace(/\./g,"_")] = "";
									}
								});
								listaFinal.push(obj);
							});

						}

						return listaFinal;

					}
					
				}]
		    };
		});
		
})();
