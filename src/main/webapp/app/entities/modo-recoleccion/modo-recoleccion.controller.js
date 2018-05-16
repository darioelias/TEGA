(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('ModoRecoleccionController', ModoRecoleccionController);

    ModoRecoleccionController.$inject = ['$scope', '$state', 'ModoRecoleccion', 
										'ParseLinks', 'AlertService', 'pagingParams', 
										'paginationConstants','ModoRecoleccionDialog',
										'ModoRecoleccionDelete'];

    function ModoRecoleccionController ($scope, $state, ModoRecoleccion, 
										ParseLinks, AlertService, pagingParams, 
										paginationConstants,ModoRecoleccionDialog,
										ModoRecoleccionDelete) {
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
			ModoRecoleccion.queryFiltered(parametros, onSuccess, onError);

            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.totalItems = headers('X-Total-Count');
                vm.modosRecoleccion = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

		function crearEditar(id){
			ModoRecoleccionDialog.open(id).then(function(){vm.loadAll()});
		}

		function eliminar(id){
			ModoRecoleccionDelete.open(id).then(function(){vm.loadAll()});
		}
    }
})();
