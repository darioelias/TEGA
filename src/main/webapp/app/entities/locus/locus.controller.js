(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('LocusController', LocusController);

    LocusController.$inject = ['$scope', '$state', 'Locus', 'ParseLinks', 
								'AlertService', 'pagingParams', 'paginationConstants',
								'LocusDialog','LocusDelete'];

    function LocusController ($scope, $state, Locus, ParseLinks, 
							  AlertService, pagingParams, paginationConstants,
							  LocusDialog, LocusDelete) {
        var vm = this;
        
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
		vm.page = 1;
		vm.criterio = {};
		vm.crearEditar = crearEditar;
		vm.eliminar = eliminar;	
		vm.loadAll = loadAll;
		vm.getParametrosQuery = getParametrosQuery;
        loadAll();

        function loadAll (itemsPerPage, page) {

			if(itemsPerPage != null)
				vm.itemsPerPage = itemsPerPage;

			if(page != null)
				vm.page = page;

			Locus.queryFiltered(getParametrosQuery(), onSuccess, onError);

            function onSuccess(data, headers) {
                vm.totalItems = headers('X-Total-Count');
                vm.loci = data;
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
			LocusDialog.open(id).then(function(){vm.loadAll()});
		}

		function eliminar(id){
			LocusDelete.open(id).then(function(){vm.loadAll()});
		}
    }
})();
