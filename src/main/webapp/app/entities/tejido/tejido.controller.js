(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('TejidoController', TejidoController);

    TejidoController.$inject = ['$scope', '$state', 'Tejido', 'ParseLinks', 'AlertService', 
								'pagingParams', 'paginationConstants',
								'TejidoDialog','TejidoDelete'];

    function TejidoController ($scope, $state, Tejido, ParseLinks, AlertService, 
								pagingParams, paginationConstants,
								TejidoDialog, TejidoDelete) {
        var vm = this;
        
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
		vm.page = 1;
        vm.criterio = {};
		
		vm.loadAll = loadAll;
		vm.crearEditar = crearEditar;
		vm.eliminar = eliminar;
        loadAll();

        function loadAll (itemsPerPage, page) {

			if(itemsPerPage != null)
				vm.itemsPerPage = itemsPerPage;

			if(page != null)
				vm.page = page;

            var parametros = {page: vm.page - 1, size: vm.itemsPerPage, sort: sort(), criterio: vm.criterio};
			Tejido.queryFiltered(parametros, onSuccess, onError);

            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.totalItems = headers('X-Total-Count');
                vm.tejidos = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

		function crearEditar(id){
			TejidoDialog.open(id).then(function(){vm.loadAll()});
		}

		function eliminar(id){
			TejidoDelete.open(id).then(function(){vm.loadAll()});
		}

    }
})();
