(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('region', {
            parent: 'entity',
            url: '/region?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoApp.region.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/region/regiones.html',
                    controller: 'RegionController',
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
                    $translatePartialLoader.addPart('region');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('region-detail', {
            parent: 'entity',
            url: '/region/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoApp.region.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/region/region-detail.html',
                    controller: 'RegionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('region');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Region', function($stateParams, Region) {
                    return Region.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {

					var name = $state.current.name || 'region';
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