(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('MuestraController', MuestraController);

    MuestraController.$inject = ['$scope', '$state', 'Muestra', 'ParseLinks', 'AlertService', 
								 'pagingParams', 'paginationConstants','Region','Localidad',
								 'Profesional','Institucion', 'Especie', 'Tejido', 'ModoRecoleccion',
								 'DateUtils','Principal','MuestraDialog','MuestraDelete', 'MuestraImportarDialog'];

    function MuestraController ($scope, $state, Muestra, ParseLinks, AlertService, 
								pagingParams, paginationConstants, Region, Localidad,
								Profesional, Institucion, Especie, Tejido, ModoRecoleccion,
								DateUtils, Principal,MuestraDialog, MuestraDelete, MuestraImportarDialog) {
        var vm = this;
     
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
		vm.page = 1;

		vm.criterio = {};


		vm.camposExp = "";
		if(Principal.hasAnyAuthority(['ROLE_ABM'])){
			vm.camposExp =  "id,codigoInterno,codigoExterno,fecha,fechaRecoleccion,region.nombre,"+
							"localidad.nombre,profesional.nombre,institucion.nombre,especie.nombre,"+
							"tejido.nombre,modoRecoleccion.nombre,peso,largo,sexo,temperaturaAmbiente,"+
							"conductividadAmbiente,altura,latitud,longitud,ubicacion,detalle";
		}else{
			vm.camposExp =  "id,codigoInterno,codigoExterno,fecha,fechaRecoleccion,region.nombre,"+
							"localidad.nombre,institucion.nombre,especie.nombre,"+
							"tejido.nombre,modoRecoleccion.nombre,peso,largo,sexo,temperaturaAmbiente,"+
							"conductividadAmbiente,altura,latitud,longitud,detalle";
		}

		vm.loadAll = loadAll;
		vm.crearEditar = crearEditar;
		vm.eliminar = eliminar;
		vm.importar = importar;
		vm.getParametrosQuery = getParametrosQuery;
        loadAll();

        function loadAll (itemsPerPage, page) {

			if(itemsPerPage != null)
				vm.itemsPerPage = itemsPerPage;

			if(page != null)
				vm.page = page;


			Muestra.queryFiltered(getParametrosQuery(), onSuccess, onError);

            
            function onSuccess(data, headers) {
                vm.totalItems = headers('X-Total-Count');
                vm.muestras = data;
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
			MuestraDialog.open(id).then(function(){vm.loadAll()});
		}

		function eliminar(id){
			MuestraDelete.open(id).then(function(){vm.loadAll()});
		}

		function importar(){
			MuestraImportarDialog.open().then(function(){vm.loadAll()});			
		}

    }
})();
