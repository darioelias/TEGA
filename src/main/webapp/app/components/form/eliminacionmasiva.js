(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .directive('eliminacionmasiva', function () {
		    return {
				restrict: 'E',
		        scope: {
					campoid: '@',
					campocodigo: '@',
					campodetalle: '@',
					tr: '@',
					solodetalle: '=?',
					servicio: '@',
					filtros: '@',
					actualizar: '=?',
					rol:'@'
				},
				templateUrl: '/eliminacionmasiva.html',
				controller: ['$scope','BusquedaItems', 'EliminacionMasivaDialog', 
							function eliminacionMasivaController($scope, BusquedaItems, EliminacionMasivaDialog) {

					$scope.eliminar = eliminar;


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

					if(!$scope.rol){
						$scope.rol = "ROLE_ABM";
					}

					function eliminar(){
						
						BusquedaItems.open([],
										$scope.servicio, 
										$scope.tr, 
										$scope.filtros, 
										$scope.campoid,
										$scope.campocodigo,
										$scope.campodetalle,
										$scope.solodetalle)
						.then(function(lista){
							if(lista && lista.length > 0){	
								var ids = [];
								lista.forEach(function(x){ids.push(x.id)});							
								EliminacionMasivaDialog.open($scope.servicio, ids)
													   .then(function(){
													   		if($scope.actualizar)
																$scope.actualizar();
													   });
							}							
						});

					}
				}]
		    };
		});



	angular
        .module('proyectoApp')
		.provider('EliminacionMasivaDialog', EliminacionMasivaDialog)
        .controller('EliminacionMasivaDialogController',EliminacionMasivaDialogController);

    EliminacionMasivaDialogController.$inject = ['$uibModalInstance','servicio','ids'];

    function EliminacionMasivaDialogController($uibModalInstance, servicio, ids) {
        var vm = this;

        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        vm.cant = ids.length;

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete() {
            servicio.deleteByIdIn(ids,
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }

	function EliminacionMasivaDialog() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal'];

		function getService($uibModal){
		
			return {open: open};

			function open(servicio, ids){

				return $uibModal.open({
						        templateUrl: '/eliminacionmasivadialog.html',
						        controller: 'EliminacionMasivaDialogController',
						        controllerAs: 'vm',
						        size: 'md',
						        resolve: {
						            servicio: [servicio, function (servicioAux) {return servicioAux;}],
									ids: function(){return ids}
								}
                   	   }).result;
			}
		}
	}
		
})();
