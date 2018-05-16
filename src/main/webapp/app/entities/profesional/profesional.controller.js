(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('ProfesionalController', ProfesionalController);

    ProfesionalController.$inject = ['$scope', '$state', 'Profesional', 'ParseLinks', 
									'AlertService', 'pagingParams', 'paginationConstants',
									'ProfesionalDialog','ProfesionalDelete'];

    function ProfesionalController ($scope, $state, Profesional, ParseLinks, 
									AlertService, pagingParams, paginationConstants,
									ProfesionalDialog, ProfesionalDelete) {
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
			Profesional.queryFiltered(parametros, onSuccess, onError);

            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.totalItems = headers('X-Total-Count');
                vm.profesionales = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
		function crearEditar(id){
			ProfesionalDialog.open(id).then(function(){vm.loadAll()});
		}

		function eliminar(id){
			ProfesionalDelete.open(id).then(function(){vm.loadAll()});
		}
    }
})();
