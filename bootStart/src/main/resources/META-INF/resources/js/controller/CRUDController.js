app.controller('crudController',function($scope,crudService,$controller){
    $controller("mainController", {
        $scope : $scope
    });
    $scope.Message='';
    $scope.searchMap={'Pname':'','price_low':'','price_high':''};
    $scope.findOne=function(){
        crudService.findOne($scope.pid).success(
            function(response){
                $scope.currentProduct=response.data;
            }
        );
    }
    $scope.findAll=function(){

        crudService.findAll($scope.searchMap).success(
            function(response){
                $scope.currentProductList=response.data;
            }
        );
    }
    $scope.deleteOne=function(){
        crudService.deleteOne($scope.deletePid).success(
            function(response){
                $scope.findAll();
            }
        );
    }

    $scope.save=function(){
        crudService.save($scope.product).success(
            function(response){
                $scope.findAll();
            }
        )
    }
});