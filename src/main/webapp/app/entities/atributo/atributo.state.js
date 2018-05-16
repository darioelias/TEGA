(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('atributo', {
            parent: 'entity',
            url: '/atributo?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoApp.atributo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/atributo/atributos.html',
                    controller: 'AtributoController',
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
                    $translatePartialLoader.addPart('atributo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('atributo-detail', {
            parent: 'entity',
            url: '/atributo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoApp.atributo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/atributo/atributo-detail.html',
                    controller: 'AtributoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('atributo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Atributo', function($stateParams, Atributo) {
                    return Atributo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {

					var name = $state.current.name || 'atributo';
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
