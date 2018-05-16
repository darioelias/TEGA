(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .directive('muestrafiltros', function () {
		    return {
				restrict: 'E',
				transclude: true,
		        scope: {
					criterio: '='
				},
				templateUrl: 'app/entities/muestra/muestra-filtros.html',
				controller: ['$scope','Region','Localidad','Profesional',
							 'Institucion', 'Especie', 'Tejido', 'ModoRecoleccion', 
							 'ConjuntoMuestras','$translate', '$translatePartialLoader',
							 function filterController($scope, Region, Localidad,
								Profesional, Institucion, Especie, Tejido, ModoRecoleccion,
							    ConjuntoMuestras,$translate, $translatePartialLoader) {

					$translatePartialLoader.addPart('muestra');
                    $translatePartialLoader.addPart('global');
					$translatePartialLoader.addPart('sexo');
                    $translate.refresh();


					if($scope.criterio == null || Object.keys($scope.criterio).length == 0)
						$scope.criterio = {codigoInterno: null, codigoExterno: null,
										   ubicacion: null, idRegion: null, idLocalidad: null, idProfesional: null,
										   idInstitucion: null, idEspecie: null, idTejido: null, idModoRecoleccion: null,
										   idConjuntoMuestras: null, desdeRecoleccion: null, hastaRecoleccion: null,
										   idProyecto: null, publico: null, id: null};	

					$scope.cambioPublico = function(){
						if($scope.criterio.publico == "")
							$scope.criterio.publico = null;
					}				
				}]
		    };
		});
		
})();
