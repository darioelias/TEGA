(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('ConjuntoMuestrasController', ConjuntoMuestrasController);

    ConjuntoMuestrasController.$inject = ['$scope', '$state', 'ConjuntoMuestras', 
										  'ParseLinks', 'AlertService', 'pagingParams', 
										  'paginationConstants', 'ConjuntoMuestrasDialog',
									      'ConjuntoMuestrasDelete'];

    function ConjuntoMuestrasController ($scope, $state, ConjuntoMuestras, 
										 ParseLinks, AlertService, pagingParams, 
										 paginationConstants, ConjuntoMuestrasDialog,
									     ConjuntoMuestrasDelete) {
        var vm = this;
        
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
		vm.page = 1;
		vm.criterio = {};
		vm.loadAll = loadAll;
		vm.crearEditar = crearEditar;
		vm.eliminar = eliminar;
		vm.getParametrosQuery = getParametrosQuery;
        loadAll();

        function loadAll (itemsPerPage, page) {

			if(itemsPerPage != null)
				vm.itemsPerPage = itemsPerPage;

			if(page != null)
				vm.page = page;

			ConjuntoMuestras.queryFiltered(getParametrosQuery(), onSuccess, onError);

            function onSuccess(data, headers) {
                vm.totalItems = headers('X-Total-Count');
                vm.conjuntosMuestras = data;
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

		function crearEditar(id){
			ConjuntoMuestrasDialog.open(id).then(function(){vm.loadAll()});
		}

		function eliminar(id){
			ConjuntoMuestrasDelete.open(id).then(function(){vm.loadAll()});
		}
    }
})();
