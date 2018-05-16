(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('conjunto-muestras', {
            parent: 'entity',
            url: '/conjuntos-muestras?page&sort&search',
            data: {
                authorities: ['ROLE_USER_EXTENDED'],
                pageTitle: 'proyectoApp.conjuntoMuestras.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/conjunto-muestras/conjuntos-muestras.html',
                    controller: 'ConjuntoMuestrasController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('conjuntoMuestras');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('conjunto-muestras-detail', {
            parent: 'entity',
            url: '/conjunto-muestras/{id}',
            data: {
                authorities: ['ROLE_USER_EXTENDED'],
                pageTitle: 'proyectoApp.conjuntoMuestras.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/conjunto-muestras/conjunto-muestras-detail.html',
                    controller: 'ConjuntoMuestrasDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('conjuntoMuestras');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ConjuntoMuestras', function($stateParams, ConjuntoMuestras) {
                    return ConjuntoMuestras.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {

					var name = $state.current.name || 'conjuntoMuestras';
					if($state.params.id !== undefined)
						name = name+"({id:"+$state.params.id+"})";

                    var currentStateData = {
                        name: name,
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        });
    }

})();
