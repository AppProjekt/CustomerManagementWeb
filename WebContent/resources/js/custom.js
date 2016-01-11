/**
 * 
 */
var serviceURI = "/CustomerManagementWeb/api/v1/customers/list";

var cmApp = angular.module("cmApp",[]);

cmApp.controller("cmStartViewController",['$scope','$http',function($scope,$http){
	
	$scope.customers = [];
	$scope.searchFilter = '';
	$scope.search = '';
	
	$scope.getAllCustomers = function($scope, $http) {
		
		$http.get(serviceURI) 
		     .success(function(data) {
		    	 $scope.customers = data; })
		     .error(function(error) {
		    	 console.log(error); });		
	}
	
	$scope.applySearchFilter = function() {
		$scope.searchFilter = $scope.search;
	}
	
	$scope.getRelationship = function(relationship) {
		if(relationship == 'Friend') {
			return "Freund";
		}
		if(relationship == 'Colleague') {
			return "Kollege";
		}
		if(relationship == 'Job') {
			return "Arbeitsbeziehung";
		}
		if(relationship == 'Family') {
			return "Familie";
		}

		return "Unbekannt";
	}
	
	$scope.getAllCustomers($scope, $http);
	
}]);