(function() {
    'use strict';

    angular
		.module('proyectoApp')
		.factory('DownloadService', ['$q', '$http', function($q, $http) {

				return {
		            download: function(url, params) {

								var defer = $q.defer();

								if(params === undefined)
									params = null;

								$http({
									url: url, 
									params: params,
									responseType: 'arraybuffer'
								}).then(function(res) {
									var blob = new Blob(
											[res.data], { type: res.headers('Content-Type') }
										);								

									var nomArchivo = res.headers('filename');
									if(!nomArchivo || nomArchivo.length == 0)
										nomArchivo = "file.txt";

									var a = document.createElement('a');
									a.href = URL.createObjectURL(blob);
									a.download = nomArchivo;
									a.target = '_blank';
									a.click();

									defer.resolve('success');
								},function(error){
				                    defer.reject(error);
				                });

								return defer.promise;
							}
					};
		    }
		]);
})();
