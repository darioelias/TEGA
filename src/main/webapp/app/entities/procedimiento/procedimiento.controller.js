(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('ProcedimientoController', ProcedimientoController);

    ProcedimientoController.$inject = ['$scope', '$state', 'Procedimiento', 
								'ParseLinks', 'AlertService', 'pagingParams', 
								'paginationConstants','ProcedimientoDialog','ProcedimientoDelete'];

    function ProcedimientoController ($scope, $state, Procedimiento, 
								ParseLinks, AlertService, pagingParams, 
								paginationConstants, ProcedimientoDialog, ProcedimientoDelete) {
        var vm = this;
        
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
		vm.page = 1;
        vm.criterio = {};
		vm.crearEditar = crearEditar;
		vm.eliminar = eliminar;
		vm.loadAll = loadAll;
        loadAll();

        function loadAll (itemsPerPage, page) {

			if(itemsPerPage != null)
				vm.itemsPerPage = itemsPerPage;

			if(page != null)
				vm.page = page;

            var parametros = {page: vm.page - 1, size: vm.itemsPerPage, sort: sort(), criterio: vm.criterio};
			Procedimiento.queryFiltered(parametros, onSuccess, onError);

            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.totalItems = headers('X-Total-Count');
                vm.procedimientos = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

		function crearEditar(id){
			ProcedimientoDialog.open(id).then(function(){vm.loadAll()});
		}

		function eliminar(id){
			ProcedimientoDelete.open(id).then(function(){vm.loadAll()});
		}
    }
})();
