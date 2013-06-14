function CtrlTask($scope, Tasks) {
	$scope.tasks = Tasks.query();
	$scope.add = function (){
		$scope.tasks.push({title:'gaston'});
	};
}


// manera directa x $http
// function CtrlTask($scope,$http){
//	$http.get("../rest/tasks").
//	  	success(function(data, status, headers, config) {
//	  		$scope.tasks = data;
//		  });
//	$scope.add = function (){
//		$scope.tasks.push({title:'gaston'});
//	};
//}