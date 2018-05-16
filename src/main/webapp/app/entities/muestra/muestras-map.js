(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('MuestraMapController', MuestraMapController);

    MuestraMapController.$inject = ['$scope', '$state', '$translate', 'Muestra', 'ParseLinks', 'AlertService', 
								 'paginationConstants','Region','Localidad',
								 'Profesional','Institucion', 'Especie', 'Tejido', 'ModoRecoleccion',
								 'DateUtils','ParametroTega','$q'];

    function MuestraMapController ($scope, $state, $translate, Muestra, ParseLinks, AlertService, 
								paginationConstants, Region, Localidad,
								Profesional, Institucion, Especie, Tejido, ModoRecoleccion,
								DateUtils, ParametroTega, $q) {
        var vm = this;
        

        vm.itemsPerPage = 1000;

		vm.page = 0;
		vm.color = "#FE7569";

		vm.iconMap = "";

		ParametroTega.obtenerPublico({codigo: "MAPA_MUESTRAS_ICONO"},function(p){vm.iconMap = p.valor});

		vm.options = {swatchOnly: true, format: 'hex'};	

		vm.map = {
				center: {
					lat: 0,
					lon: 0,
					zoom: 4
				},
				defaults: {
					controls: {
					    zoom: true,
					    rotate: true,
					    attribution: true,
						scaleline: true
					}
				},
				config: {
				  interactions: {
					 mouseWheelZoom: true
				  }
			   	},
				markers: [],
				controls: [
		            { name: 'zoom', active: true },
		            { name: 'attribution', active: true },
		            { name: 'rotate', active: true, autoHide: false},
		            { name: 'scaleline', active: true }
		        ]
			};

		vm.pLati = ParametroTega.obtenerPublico({codigo: "MAPA_MUESTRAS_LATITUD"});
		vm.pLong = ParametroTega.obtenerPublico({codigo: "MAPA_MUESTRAS_LONGITUD"});

		$q.all([vm.pLati.$promise, 
				vm.pLong.$promise]).then(function(){

			vm.map.center.lat = vm.pLati.valor;
			vm.map.center.lon = vm.pLong.valor;
		});


		vm.criterio = {};

		vm.loadAll = loadAll;
		vm.clearAll = clearAll;

		function clearAll(){
			vm.map.markers = [];
		}

        function loadAll () {

			var parametros = {page: vm.page, size: vm.itemsPerPage, sort: null, criterio: vm.criterio};
			Muestra.queryFiltered(parametros, onSuccess, onError);
        
            function onSuccess(data, headers) {
                if(data != null && data.length > 0){

					for (var index = 0; index < data.length; ++index) {
					
						var muestra = angular.fromJson(data[index]);
						if(muestra.latitud != null && muestra.longitud != null ){

							var marker = {
								lat: muestra.latitud,
								lon: muestra.longitud,
								id: muestra.id,
								label: {
									message: getLabelMessage(muestra.id, muestra.codigoInterno, muestra.codigoExterno),
									show: false,
                                	showOnMouseClick: true
								},
								
    							style:{
									image: {
										icon: {
											src: vm.iconMap,
											color: vm.color
										}
									}
								}

							};

							vm.map.markers.push(marker);
						}
					}

					vm.page = vm.page + 1; 
					loadAll();
				
				}else{
					vm.page =  0;
				}
            }
			function onError(error) {
				vm.page =  0;
				AlertService.error(error.data.message);
			}
		}

		function getLabelMessage(id, codigoInterno, codigoExterno) {
			var msjCodInt = $translate.instant("proyectoApp.muestra.codigoInterno");
			var msjCodExt = $translate.instant("proyectoApp.muestra.codigoExterno");
			var urlMuestra = $state.href("muestra-detail", {id:id});

			 var html = "<div>"+
							"<td><label class='control-label'>ID:&nbsp;</label></td>"+
			 				"<td><a href='"+urlMuestra+"'>"+id+"</a></td>"+
							"<br/>"+
							"<td><label class='control-label'>"+msjCodInt+":&nbsp;</label></td>"+
			 				"<td><a href='"+urlMuestra+"'>"+codigoInterno+"</a></td>"+
							"<br/>"+
							"<td><label class='control-label'>"+msjCodExt+":&nbsp;</label></td>"+
			 				"<td><a href='"+urlMuestra+"'>"+codigoExterno+"</a></td>"+
						"</div>";
		
		    return html;
		}
    }
})();
