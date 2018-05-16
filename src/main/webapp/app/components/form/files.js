(function() {
    'use strict';

	angular
        .module('proyectoApp')
        .provider('FileUpload',FileUpload)
		.controller('FileUploadFormController',FileUploadFormController);

    FileUploadFormController.$inject = ['$uibModalInstance','AlertService','AlertaService',
										'Upload', 'idPadre', 'tipoPadre', 'listaArchivos'];

	function FileUploadFormController($uibModalInstance, AlertService, AlertaService, 
										Upload, idPadre, tipoPadre, listaArchivos) {
	    var vm = this;

		vm.idPadre = idPadre;
		vm.tipoPadre = tipoPadre;
		vm.listaArchivos = listaArchivos;
		vm.upload = upload;
		vm.clear = clear;

		vm.file = null;
		vm.detalleArchivo = "";
		vm.publico = false;

		vm.porcentaje = null;
		vm.subiendo = false;

		vm.alertas = [];


		function upload(){

			if(vm.subiendo)
				return;

			if(vm.file == null){
				AlertaService.error(vm.alertas,'general.archivo-obligatorio');
				return;	
			}


			var archivos = vm.listaArchivos.filter(function(archivo) {return getNombreArchivo(archivo) == vm.file.name});
			if(archivos.length > 0){
				AlertaService.error(vm.alertas,'general.archivo-repetido');
				return;			
			}
				
			if(!vm.detalleArchivo)
				vm.detalleArchivo = vm.file.name;

			vm.subiendo = true;

			Upload.upload({
					url: "api/archivo-upload",
					file: vm.file,
					fields: {'detalle': vm.detalleArchivo,
							 'idPadre': vm.idPadre,
							 'tipoPadre': vm.tipoPadre,
							 'publico': vm.publico}
				}).progress(function (evt) {
					vm.porcentaje =  parseInt(100.0 * evt.loaded / evt.total);
				}).success(function (data, status, headers, config) {
					vm.subiendo = false;
					if(typeof data.id === 'undefined'){

					}else{								
						$uibModalInstance.close(data);
					}
					
				});


		}

//		function setOrden(predicado){
//			if(predicado == "id")
//				campoOrden = "id";
//			else if(predicado == "codigo")
//				camposOrden = vm.camposCodigo;
//			else if(predicado == "detalle")
//				camposOrden = vm.camposDetalle;
//		}

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }
		function getNombreArchivo(archivo){
			return archivo.direccion.split('\\').pop().split('/').pop();
		}
	}

	function FileUpload() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', '$q'];

		function getService($uibModal, $q){
		
			return {open: open};

			function open(idPadre, tipoPadre, listaArchivos){

				var deferred = $q.defer();

				$uibModal.open({
						templateUrl: '/files-upload.html',
						controller: 'FileUploadFormController',
						controllerAs: 'vm',
						size: 'md',
						resolve: {
							idPadre: function(){return idPadre},
							tipoPadre: function(){return tipoPadre},
							listaArchivos: function(){return listaArchivos},
							mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
												$translatePartialLoader.addPart('archivo');
												return $translate.refresh();
											}]
						}
					}).result.then(function(archivo) {
						deferred.resolve(archivo);
					}, function() {deferred.reject()});

				return deferred.promise;
			}
		}
	}	

	angular
		.module('proyectoApp')
		.factory('FiltroArchivoService', function() {
		  return {
			  filtro : ''
		  };
		});

	angular
		.module('proyectoApp')
		.filter('filtroarchivos', ['FiltroArchivoService', function(FiltroArchivoService) {
			return function(items) {

				var filtro = FiltroArchivoService.filtro;

				if (!filtro){
					return items;
				}  
				var result = {};
				filtro = filtro.toLowerCase();
				angular.forEach(items, function(item, key) {
					
					if((item.direccion != null && item.direccion.split('\\').pop().split('/').pop().toLowerCase().indexOf(filtro) > -1) ||
					   (item.detalle != null && item.detalle.toLowerCase().indexOf(filtro) > -1)){
						result[key] = item;
					}

				});

				return result;
			};
		}]);



    angular
        .module('proyectoApp')
        .directive('files', function () {
		    return {
				restrict: 'E',
		        scope: {
					lista: '=',
					urlupload: '@',
					urldownload: '@',
					tipopadre: '@',
					idpadre: '=',
					readonly: '=?',
					noupload: '=?'
				},
				templateUrl: '/files.html',
				controller: ['$scope','Upload','FileUpload','FiltroArchivoService',
							'$uibModal','DownloadService','AlertaService', 'AlertService', 
							function filesController($scope, Upload, FileUpload, FiltroArchivoService,
													 $uibModal, DownloadService, AlertaService, AlertService) {

					if($scope.readonly === undefined)
						$scope.readonly = false;		
	
					if($scope.noupload === undefined)
						$scope.noupload = false;

					$scope.eliminar = eliminar;
					$scope.upload = upload;
					$scope.download = download;
					$scope.getNombreArchivo = getNombreArchivo;
					$scope.setAtributoOrden = setAtributoOrden;

					FiltroArchivoService.filtro = "";
					$scope.filtro = FiltroArchivoService.filtro;

					$scope.predicado = "id";
					$scope.ascendente = true;

					if($scope.lista === undefined){
						$scope.lista = [];			
					}

					function setAtributoOrden(archivo){
						if($scope.predicado == "id")
							return archivo.id;
						if($scope.predicado == "nombre")
							return getNombreArchivo(archivo);
						if($scope.predicado == "detalle")
							return archivo.detalle;
						if($scope.predicado == "publico")
							return archivo.publico;
						return "";
					}

					function getNombreArchivo(archivo){
						return archivo.direccion.split('\\').pop().split('/').pop();
					}

					function upload(){
						FileUpload.open($scope.idpadre,
										$scope.tipopadre, 
										$scope.lista)
								  .then(function(archivo){
								  		$scope.lista.push(archivo);
										$scope.predicado = $scope.predicado;
								  });
					}


					$scope.filtrar = function(filtro) {
						FiltroArchivoService.filtro = filtro;									
					}


					function download(archivo) {
						DownloadService.download("api/archivo-download",{id: archivo.id}).then(function(){},function(error){
							 AlertService.error(error.headers("X-proyectoApp-error"));
						});
					}

					function eliminar (archivo) {
						if (archivo != null){

							$uibModal.open({
						        templateUrl: 'app/entities/archivo/archivo-delete-dialog.html',
						        controller: 'ArchivoDeleteController',
						        controllerAs: 'vm',
						        size: 'md',
						        resolve: {
						            entity: archivo,
									translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
											$translatePartialLoader.addPart('archivo');
											return $translate.refresh();
										}]
						        }
						    }).result.then(function() {
						        $scope.lista.splice( $scope.lista.indexOf(archivo), 1);
						    }, function() {});

						}
	
					}
				}]
		    };
		});


	
})();
