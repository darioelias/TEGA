(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('AnalisisGenotipoAlelosImportar', AnalisisGenotipoAlelosImportar)
        .controller('AnalisisGenotipoAlelosImportarController',AnalisisGenotipoAlelosImportarController);

    AnalisisGenotipoAlelosImportarController.$inject = ['$uibModalInstance', 'contenido', '$q',
														'ParametroTega','Principal'];

    function AnalisisGenotipoAlelosImportarController($uibModalInstance, contenido, $q,
													  ParametroTega, Principal) {
        var vm = this;

		vm.contenido = contenido;

		vm.contenidoStr = "";

		vm.editable = Principal.hasAnyAuthority(['ROLE_ABM']);

        vm.clear = clear;
        vm.save = save;
		vm.actualizarContenido = actualizarContenido;
		vm.actualizarMatriz = actualizarMatriz;
		vm.vaciar = vaciar;

		var paramSep = ParametroTega.obtener({codigo: "SEPARADOR_IMP_EXP_ARCHIVOS"}, 
						 function(p){vm.separador = p.valor});

		var paramNul = ParametroTega.obtener({codigo: "ALELOS_IMP_EXP_VALOR_NULO"}, 
						 function(p){vm.nulo = p.valor});

		$q.all([paramSep.$promise, paramNul.$promise]).then(function(){
				actualizarContenido();
		});

		
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

		function vaciar(){
			vm.contenidoStr = "";
		}

		function actualizarContenido() {
			var cont = ""; 

			if(!vm.separador || vm.separador == "null")
				vm.separador = " ";

			if(vm.separador == vm.nulo)
				vm.nulo = "null";

			vm.contenido.matriz.forEach(function(f){ 
					f.forEach(function(c){
						if(c == null)
							cont += vm.nulo + vm.separador;
						else
							cont += c + vm.separador;
					}); 
					cont = cont.substr(0,cont.length-1);
					cont +="\n";
			});

			vm.contenidoStr = cont.substr(0,cont.length-1);
		}

		function actualizarMatriz() {
			vm.contenido.matriz = [];
	
			var filas = vm.contenidoStr.split("\n");
			filas.forEach(function(f){
				var valores = [];
				if(isBlank(vm.separador)){
					valores = f.split(/\s+/);
				}else{
			  		valores = f.split(vm.separador);
				}

				var valoresAux =[];
				valores.forEach(function(a){
					if(!a || a == vm.nulo)
						valoresAux.push(null);
					else
						valoresAux.push(a);	
				})

				vm.contenido.matriz.push(valoresAux);
			});
		}

        function save () {           
		   vm.actualizarMatriz();
           $uibModalInstance.close(vm.contenido);
        }

		function isBlank(str) {
			return (!str || /^\s*$/.test(str));
		}
    }


	function AnalisisGenotipoAlelosImportar() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal'];

		function getService($uibModal){
		
			return {open: open};

			function open(contenido){

				return $uibModal.open({
							templateUrl: 'app/entities/analisis-genotipo/analisis-genotipos-alelos-importar.html',
							controller: 'AnalisisGenotipoAlelosImportarController',
							controllerAs: 'vm',
							size: 'md',
							resolve: {
								contenido: contenido
							}
						}).result;
			}
		}
	}


})();
