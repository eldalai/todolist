angular.module('taskServices', ['ngResource']).
    factory('Tasks', function($resource){
  return $resource('../rest/:taskId', {}, {
    query: {method:'GET', params:{taskId:'tasks'}, isArray:true}
  });
});