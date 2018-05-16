(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('alelo', {
            parent: 'entity',
            url: '/alelo?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoApp.alelo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/alelo/alelos.html',
                    controller: 'AleloController',
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
                    $translatePartialLoader.addPart('alelo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('alelo-detail', {
            parent: 'entity',
            url: '/alelo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoApp.alelo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/alelo/alelo-detail.html',
                    controller: 'AleloDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('alelo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Alelo', function($stateParams, Alelo) {
                    return Alelo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {

					var name = $state.current.name || 'alelo';
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
