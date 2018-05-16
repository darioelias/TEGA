(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('parametro-tega', {
            parent: 'entity',
            url: '/parametro-tega?page&sort&search',
            data: {
                authorities: ['ROLE_MANAGER'],
                pageTitle: 'proyectoApp.parametroTega.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/parametro-tega/parametros-tega.html',
                    controller: 'ParametroTegaController',
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
                    $translatePartialLoader.addPart('parametroTega');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('parametro-tega-detail', {
            parent: 'entity',
            url: '/parametro-tega/{id}',
            data: {
                authorities: ['ROLE_MANAGER'],
                pageTitle: 'proyectoApp.parametroTega.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/parametro-tega/parametro-tega-detail.html',
                    controller: 'ParametroTegaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('parametroTega');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ParametroTega', function($stateParams, ParametroTega) {
                    return ParametroTega.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {

					var name = $state.current.name || 'parametro-tega';
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
