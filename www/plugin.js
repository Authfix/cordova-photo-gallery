var exec = require('cordova/exec');

exports.show = function(options) {
    
    if(typeof options == "undefined"){
        options = {};
    }

    exec(function(){}, function(){}, "PhotoGallery", "show", [options]);
};