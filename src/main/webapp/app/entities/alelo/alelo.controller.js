(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('AleloController', AleloController);

    AleloController.$inject = ['$scope', '$state', 'Alelo', 'ParseLinks', 
								'AlertService', 'pagingParams', 'paginationConstants',
								'AleloDialog', 'AleloDelete', 'AleloImportarDialog'];

    function AleloController ($scope, $state, Alelo, ParseLinks, 
							AlertService, pagingParams, paginationConstants,
							AleloDialog, AleloDelete, AleloImportarDialog) {
        var vm = this;
        
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
		vm.page = 1;

		vm.criterio = {};
		
		vm.loadAll = loadAll;
		vm.crearEditar = crearEditar;
		vm.eliminar = eliminar;
		vm.importar = importar;
        loadAll();

        function loadAll (itemsPerPage, page) {

			if(itemsPerPage != null)
				vm.itemsPerPage = itemsPerPage;

			if(page != null)
				vm.page = page;

            var parametros = {page: vm.page - 1, size: vm.itemsPerPage, sort: sort(), criterio: vm.criterio};
			Alelo.queryFiltered(parametros, onSuccess, onError);

            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.totalItems = headers('X-Total-Count');
                vm.alelos = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

		function crearEditar(id){
			AleloDialog.open(id).then(function(){vm.loadAll()});
		}

		function eliminar(id){
			AleloDelete.open(id).then(function(){vm.loadAll()});
		}

		function importar(){
			AleloImportarDialog.open().then(function(){vm.loadAll()});			
		}

    }
})();
