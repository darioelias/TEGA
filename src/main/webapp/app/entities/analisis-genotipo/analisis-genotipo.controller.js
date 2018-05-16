(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('AnalisisGenotipoController', AnalisisGenotipoController);

    AnalisisGenotipoController.$inject = ['$scope', '$state', 'AnalisisGenotipo', 
										'ParseLinks', 'AlertService', 'pagingParams', 
										'paginationConstants','DateUtils','$uibModal',
										'Principal', 'AnalisisGenotipoDialog', 'AnalisisGenotipoDelete',
										'AnalisisGenotipoProcDialog', 'AnalisisGenotiposAlelos',
										'AnalisisGenotipoVerLogDialog'];

    function AnalisisGenotipoController ($scope, $state, AnalisisGenotipo, 
										 ParseLinks, AlertService, pagingParams, 
										 paginationConstants, DateUtils, $uibModal,
										 Principal, AnalisisGenotipoDialog, AnalisisGenotipoDelete,
										 AnalisisGenotipoProcDialog, AnalisisGenotiposAlelos,
										 AnalisisGenotipoVerLogDialog) {
        var vm = this;
        

        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
		vm.page = 1;
		
		vm.criterio = {};

		vm.camposExp = "";
		if(Principal.hasAnyAuthority(['ROLE_ABM'])){
			vm.camposExp =  "id,codigo,estado,detalle,fecha,publico,proyecto.codigo";
		}else{
			vm.camposExp =  "id,codigo,detalle,fecha,proyecto.codigo";
		}

		vm.loadAll = loadAll;
		vm.disponible = disponible;
		vm.crearEditar = crearEditar;
		vm.eliminar = eliminar;
		vm.ejecutarProcedimiento = ejecutarProcedimiento;
		vm.verLog = verLog;
		vm.alelos = alelos;
		vm.detalleEjecucion = detalleEjecucion;
		vm.getParametrosQuery = getParametrosQuery;
        loadAll();

        function loadAll (itemsPerPage, page) {

			if(itemsPerPage != null)
				vm.itemsPerPage = itemsPerPage;

			if(page != null)
				vm.page = page;

			AnalisisGenotipo.queryFiltered(getParametrosQuery(), onSuccess, onError);

            function onSuccess(data, headers) {
                vm.totalItems = headers('X-Total-Count');
                vm.analisisGenotipos = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

		function getParametrosQuery(){
			return {page: vm.page - 1, size: vm.itemsPerPage, sort: sort(), criterio: vm.criterio};

			function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
		}

		function disponible(analisis){
			return analisis.estado == "DISPONIBLE";
		}

		function crearEditar(id){
			AnalisisGenotipoDialog.open(id).then(function(){vm.loadAll()});
		}

		function eliminar(id){
			AnalisisGenotipoDelete.open(id).then(function(){vm.loadAll()});
		}


		function ejecutarProcedimiento(id){
			AnalisisGenotipoProcDialog.open(id).then(
				function(){vm.loadAll()},
				function(detalleError){
					if(detalleError)						
						vm.loadAll();
				});
		}

		function verLog(id){
			AnalisisGenotipoVerLogDialog.open(id).then(
				function(){vm.loadAll()},
				function(){vm.loadAll()});
		}

		function alelos(id){
			AnalisisGenotiposAlelos.open(id).then(function(){vm.loadAll()});
		}

		function detalleEjecucion(analisis){
			if(analisis.estado != "DISPONIBLE" && analisis.procedimiento){
				return analisis.procedimiento.nombre;
			}
			return "";
		}

    }
})();
