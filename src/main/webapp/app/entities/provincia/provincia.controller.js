(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('ProvinciaController', ProvinciaController);

    ProvinciaController.$inject = ['$scope', '$state', 'Provincia', 
									'ParseLinks', 'AlertService', 'pagingParams', 
									'paginationConstants','ProvinciaDialog','ProvinciaDelete'];

    function ProvinciaController ($scope, $state, Provincia, 
									ParseLinks, AlertService, pagingParams, 
									paginationConstants, ProvinciaDialog, ProvinciaDelete) {
        var vm = this;
        
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.itemsPerPage = paginationConstants.itemsPerPage;

		vm.criterio = {};
		vm.page = 1;
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
			Provincia.queryFiltered(parametros, onSuccess, onError);

            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.totalItems = headers('X-Total-Count');
                vm.provincias = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

		function crearEditar(id){
			ProvinciaDialog.open(id).then(function(){vm.loadAll()});
		}

		function eliminar(id){
			ProvinciaDelete.open(id).then(function(){vm.loadAll()});
		}
    }
})();
