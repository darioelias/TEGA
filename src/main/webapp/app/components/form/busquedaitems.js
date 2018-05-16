(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .provider('BusquedaItems',BusquedaItems)
		.controller('BusquedaItemsFormController',BusquedaItemsFormController);

    BusquedaItemsFormController.$inject = ['$uibModalInstance','AlertService','servicio', 
											'campoid', 'campocodigo', 'campodetalle', 
											'listaDestino','solodetalle'];

    function BusquedaItemsFormController($uibModalInstance, AlertService, servicio, 
											campoid, campocodigo, campodetalle, 
											listaDestino, solodetalle) {
        var vm = this;

        vm.predicadoBD = "id";
        vm.ascendenteBD = true;
		vm.camposOrdenBD = "id";

        vm.predicadoSel = "id";
        vm.ascendenteSel = true;
		vm.camposOrdenSel = "id";

		vm.campoid = campoid;
		vm.campocodigo = campocodigo;
		vm.campodetalle = campodetalle;
		vm.listaDestino = listaDestino;
		vm.solodetalle = solodetalle;

		vm.servicio = servicio;
        
		vm.criterio = null;
		vm.filas = [];

		vm.load = load;
		vm.clear = clear;
        vm.aceptar = aceptar;
		vm.agregar = agregar;
		vm.eliminar = eliminar;
		vm.seleccion = seleccion;
		vm.expresion = expresion;
		vm.setOrdenBD = setOrdenBD;
		vm.setOrdenSel = setOrdenSel;

		vm.usarExprDetalle = vm.campodetalle.indexOf(".") > 0 || vm.campodetalle.indexOf(",") > 0;
		vm.camposDetalle = [];
		vm.campodetalle.split(",").forEach(function(c){
			vm.camposDetalle.push(c.replace(/\s/g,"").trim());
		});	

		vm.usarExprCodigo = vm.campocodigo.indexOf(".") > 0 || vm.campocodigo.indexOf(",") > 0;
		vm.camposCodigo = [];
		vm.campocodigo.split(",").forEach(function(c){
			vm.camposCodigo.push(c.replace(/\s/g,"").trim());
		});	
		

		function load () {

			vm.servicio.items({criterio: vm.criterio}, onSuccess, onError);

            function onSuccess(data, headers) {

				data.forEach(function(d){d._seleccionado = false});

                vm.filas = data;
				setOrdenBD(vm.predicadoBD);
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
        
		function agregar () {
            vm.filas.forEach(function(f){
				if(f._seleccionado){
					var index = vm.listaDestino.findIndex(function(d){return d.id == f.id});
					if(index == -1){
						var aux = angular.copy(f);
						//delete aux._seleccionado;
						aux._seleccionado = false;
						vm.listaDestino.push(aux);
					}		
				}		
			});
			setOrdenSel(vm.predicadoSel);
        }

		function seleccion(lista, valor) {
            lista.forEach(function(f){
				f._seleccionado = valor;			
			});
        }

		function eliminar() {
			var sel = vm.listaDestino.filter(function(a){return a._seleccionado});
            sel.forEach(function(f){
				if(f._seleccionado){
					vm.listaDestino.splice(vm.listaDestino.indexOf(f), 1);
				}		
			});
        }


		function expresion(obj, campos){			
			var expr = "";		
			campos.forEach(function(c){
					try{
						expr += eval("obj."+c);						
					}catch(err){}	
					expr += " / ";				
				});
			expr = expr.replace(/\s\/\s$/, '');
			return expr;
		}

		function setOrdenBD(predicado){
			vm.camposOrdenBD = getCamposOrden(predicado);	
		}

		function setOrdenSel(predicado){
			vm.camposOrdenSel = getCamposOrden(predicado);		
		}

		function getCamposOrden(predicado){
			var camposOrden = "";
			if(predicado == "id")
				camposOrden = "id";
			else if(predicado == "codigo")
				camposOrden = vm.camposCodigo;
			else if(predicado == "detalle")
				camposOrden = vm.camposDetalle;
			return camposOrden;
		}

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function aceptar () {			
			vm.listaDestino.forEach(function(f){delete f._seleccionado;});
            $uibModalInstance.close(vm.listaDestino);
        }
    }



	function BusquedaItems() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', '$q'];

		function getService($uibModal, $q){
		
			return {open: open};

			function open(listaDestino, servicio, tr, filtros, campoid, 
						  campocodigo, campodetalle, solodetalle){

				var plantilla = crearPlantilla(tr, filtros);

				var deferred = $q.defer();

				$uibModal.open({
						template: plantilla,
						controller: 'BusquedaItemsFormController',
						controllerAs: 'vm',
						size: 'lg',
						resolve: {
							campoid: function(){return campoid},
							campodetalle: function(){return campodetalle},
							campocodigo: function(){return campocodigo},
							listaDestino: function(){return listaDestino},
							solodetalle: function(){return solodetalle},
							servicio: [servicio, function (servicioAux) {return servicioAux;}]
						}
					}).result.then(function(lista) {
						deferred.resolve(lista);
					}, function() {deferred.reject()});

				return deferred.promise;
			}

			function crearPlantilla(tr, filtros){
				return '<form name="buscarItemsForm" ng-submit="vm.aceptar()">'+
							'<div class="modal-header">'+
								'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"'+
                						'ng-click="vm.clear()">&times;</button>'+
								'<h4 class="modal-title">'+
									'<span data-translate="general.busquedaItems"></span>&nbsp;'+
									'<span data-translate="'+tr+'"></span>'+
								'</h4>'+
							'</div>'+
							'<jhi-alert></jhi-alert>'+
							'<div class="container-fluid">'+
								'<'+filtros+' criterio="vm.criterio">'+									
								'</'+filtros+'>'+
							'</div>'+
							'<div class="row">'+
								'<div class="col-md-2 pull-right">'+
									'<button type="button" class="btn btn-primary" ng-click="vm.load()" >'+
										'<span class="glyphicon glyphicon-refresh"></span>&nbsp;'+
										'<span class="hidden-xs hidden-sm" data-translate="general.actualizar"></span>'+
									'</button>'+
								'</div>'+
							'</div>'+
							'<div class="container-fluid">'+
								'<div class="table-responsive table-scroll-items">'+
									'<table class="jh-table table table-striped">'+
										'<thead>'+
											'<tr jh-sort="vm.predicadoBD" ascending="vm.ascendenteBD" funcionextra="vm.setOrdenBD">'+
												'<th>'+
												'</th>'+
												'<th jh-sort-by="id"><span data-translate="general.id"></span><span class="glyphicon glyphicon-sort"></th>'+
												'<th jh-sort-by="codigo" ng-hide="vm.solodetalle"><span data-translate="general.codigo"></span><span class="glyphicon glyphicon-sort"></th>'+
												'<th jh-sort-by="detalle" ><span data-translate="general.detalle"></span><span class="glyphicon glyphicon-sort"></th>'+
											'</tr>'+
										'</thead>'+
										'<tbody>'+
											'<tr ng-repeat="f in vm.filas | orderBy:vm.camposOrdenBD:!vm.ascendenteBD track by f[vm.campoid]">'+
												'<td>'+
													'<input type="checkbox" class="checkbox" name="sel" id="sel" '+
						   								'ng-model="f._seleccionado"/>'+
												'</td>'+
												'<td>{{f[vm.campoid]}}</td>'+
												'<td ng-if="!vm.solodetalle && !vm.usarExprCodigo">{{f[vm.campocodigo]}}</td>'+
												'<td ng-if="!vm.solodetalle && vm.usarExprCodigo">{{vm.expresion(f,vm.camposCodigo)}}</td>'+
												'<td ng-if="!vm.usarExprDetalle">{{f[vm.campodetalle]}}</td>'+
												'<td ng-if="vm.usarExprDetalle">{{vm.expresion(f,vm.camposDetalle)}}</td>'+
											'</tr>'+
										'</tbody>'+
									'</table>'+
								'</div>'+
								'<button type="button" class="btn btn-sm" ng-click="vm.seleccion(vm.filas, true)" >'+
									'<span class="glyphicon glyphicon-check"></span>'+
								'</button>'+
								'<button type="button" class="btn btn-sm" ng-click="vm.seleccion(vm.filas, false)" >'+
									'<span class="glyphicon glyphicon-unchecked"></span>'+
								'</button>'+
								'<button type="button" class="btn btn-sm" ng-click="vm.agregar()" >'+
									'<span class="glyphicon glyphicon-plus"></span>'+
								'</button>'+
							'</div>'+
							'<br/>'+
							'<div class="container-fluid">'+
								'<div class="table-responsive table-scroll-items">'+
									'<table class="jh-table table table-striped">'+
										'<thead>'+
											'<tr jh-sort="vm.predicadoSel" ascending="vm.ascendenteSel" funcionextra="vm.setOrdenSel">'+
												'<th>'+
												'</th>'+
												'<th jh-sort-by="id"><span data-translate="general.id"></span><span class="glyphicon glyphicon-sort"></th>'+
												'<th jh-sort-by="codigo" ng-hide="vm.solodetalle"><span data-translate="general.codigo"></span><span class="glyphicon glyphicon-sort"></th>'+
												'<th jh-sort-by="detalle" ><span data-translate="general.detalle"></span><span class="glyphicon glyphicon-sort"></th>'+
											'</tr>'+
										'</thead>'+
										'<tbody>'+
											'<tr ng-repeat="f in vm.listaDestino | orderBy:vm.camposOrdenSel:!vm.ascendenteSel track by f[vm.campoid]">'+
												'<td>'+
													'<input type="checkbox" class="checkbox" name="sel" id="sel" '+
						   								'ng-model="f._seleccionado"/>'+
												'</td>'+
												'<td>{{f[vm.campoid]}}</td>'+
												'<td ng-if="!vm.solodetalle && !vm.usarExprCodigo">{{f[vm.campocodigo]}}</td>'+
												'<td ng-if="!vm.solodetalle && vm.usarExprCodigo">{{vm.expresion(f,vm.camposCodigo)}}</td>'+
												'<td ng-if="!vm.usarExprDetalle">{{f[vm.campodetalle]}}</td>'+
												'<td ng-if="vm.usarExprDetalle">{{vm.expresion(f,vm.camposDetalle)}}</td>'+
											'</tr>'+
										'</tbody>'+
									'</table>'+
								'</div>'+
								'<button type="button" class="btn btn-sm" ng-click="vm.seleccion(vm.listaDestino, true)" >'+
									'<span class="glyphicon glyphicon-check"></span>'+
								'</button>'+
								'<button type="button" class="btn btn-sm" ng-click="vm.seleccion(vm.listaDestino, false)" >'+
									'<span class="glyphicon glyphicon-unchecked"></span>'+
								'</button>'+
								'<button type="button" class="btn btn-sm" ng-click="vm.eliminar()" >'+
									'<span class="glyphicon glyphicon-remove"></span>'+
								'</button>'+
							'</div>'+
							'<div class="modal-footer">'+
								'<button type="button" class="btn btn-default" data-dismiss="modal" ng-click="vm.clear()">'+
									'<span class="glyphicon glyphicon-ban-circle"></span>&nbsp;<span data-translate="entity.action.cancel"></span>'+
								'</button>'+
								'<button type="submit" class="btn btn-primary">'+
									'<span class="glyphicon glyphicon-plus"></span>&nbsp;<span data-translate="entity.action.seleccionar"></span>'+
								'</button>'+
							'</div>'+
						'</form>'
			}

		}
	}


})();
