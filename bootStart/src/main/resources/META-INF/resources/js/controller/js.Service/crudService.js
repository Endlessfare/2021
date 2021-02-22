app.service('crudService',function($http){
    this.save=function(entity){
        if(entity.pid==null){
            return $http.put('../testMe/save',entity);
        }
        return $http.post('../testMe/update',entity);
    }

    this.deleteOne=function(id){
        return $http.delete('../testMe/deleteOne?Pid='+id);
    }
    this.findOne=function(id){
        return $http.get('../testMe/findOne?Pid='+id);
    }
    this.findAll=function(entity){
        return $http.post('../testMe/getAll',entity);
    }
});