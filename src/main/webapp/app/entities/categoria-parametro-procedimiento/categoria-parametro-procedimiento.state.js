(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('categoria-parametro-procedimiento', {
            parent: 'entity',
            url: '/categoria-parametro-procedimiento?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoApp.categoriaParametroProcedimiento.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/categoria-parametro-procedimiento/categorias-parametro-procedimiento.html',
                    controller: 'CategoriaParametroProcedimientoController',
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
                    $translatePartialLoader.addPart('categoriaParametroProcedimiento');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('categoria-parametro-procedimiento-detail', {
            parent: 'entity',
            url: '/categoria-parametro-procedimiento/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoApp.categoriaParametroProcedimiento.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/categoria-parametro-procedimiento/categoria-parametro-procedimiento-detail.html',
                    controller: 'CategoriaParametroProcedimientoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('categoriaParametroProcedimiento');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CategoriaParametroProcedimiento', function($stateParams, CategoriaParametroProcedimiento) {
                    return CategoriaParametroProcedimiento.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {

					var name = $state.current.name || 'categoria-parametro-procedimiento';
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
