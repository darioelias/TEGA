(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('modo-recoleccion', {
            parent: 'entity',
            url: '/modo-recoleccion?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoApp.modoRecoleccion.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/modo-recoleccion/modos-recoleccion.html',
                    controller: 'ModoRecoleccionController',
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
                    $translatePartialLoader.addPart('modoRecoleccion');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('modo-recoleccion-detail', {
            parent: 'entity',
            url: '/modo-recoleccion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoApp.modoRecoleccion.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/modo-recoleccion/modo-recoleccion-detail.html',
                    controller: 'ModoRecoleccionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('modoRecoleccion');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ModoRecoleccion', function($stateParams, ModoRecoleccion) {
                    return ModoRecoleccion.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {

					var name = $state.current.name || 'modo-recoleccion';
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
