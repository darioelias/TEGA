(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('parametro-procedimiento', {
            parent: 'entity',
            url: '/parametro-procedimiento?page&sort&search',
            data: {
                authorities: ['ROLE_MANAGER'],
                pageTitle: 'proyectoApp.parametroProcedimiento.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/parametro-procedimiento/parametros-procedimiento.html',
                    controller: 'ParametroProcedimientoController',
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
                    $translatePartialLoader.addPart('parametroProcedimiento');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('parametro-procedimiento-detail', {
            parent: 'entity',
            url: '/parametro-procedimiento/{id}',
            data: {
                authorities: ['ROLE_MANAGER'],
                pageTitle: 'proyectoApp.parametroProcedimiento.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/parametro-procedimiento/parametro-procedimiento-detail.html',
                    controller: 'ParametroProcedimientoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('parametroProcedimiento');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ParametroProcedimiento', function($stateParams, ParametroProcedimiento) {
                    return ParametroProcedimiento.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {

					var name = $state.current.name || 'parametro-procedimiento';
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
