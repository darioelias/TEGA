(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('ParametroTegaController', ParametroTegaController);

    ParametroTegaController.$inject = ['$scope', '$state', 'ParametroTega', 'ParseLinks', 
								   'AlertService', 'pagingParams', 'paginationConstants',
								   'ParametroTegaDialog','ParametroTegaDelete'];

    function ParametroTegaController ($scope, $state, ParametroTega, ParseLinks, 
								  AlertService, pagingParams, paginationConstants,
								  ParametroTegaDialog, ParametroTegaDelete) {
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
			ParametroTega.queryFiltered(parametros, onSuccess, onError);

            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.totalItems = headers('X-Total-Count');
                vm.parametros = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

		function crearEditar(id){
			ParametroTegaDialog.open(id).then(function(){vm.loadAll()});
		}

		function eliminar(id){
			ParametroTegaDelete.open(id).then(function(){vm.loadAll()});
		}
    }
})();
