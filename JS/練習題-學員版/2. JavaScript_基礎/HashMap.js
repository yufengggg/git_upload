
var HashMap = function() {
    let map = {};
    return {
        put: function(key, value) {
            map[key] = value;
        },
        keys: function() {
            let keyArr = Object.keys(map);
            return keyArr;
        },
        contains: function(key) {
            return key in map;
        },
        get: function(key) {
            return map[key];
        },
        clear: function() {
           map = {}; 
        }
    };
};