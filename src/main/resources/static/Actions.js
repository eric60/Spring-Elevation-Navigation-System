$(document).ready(function() {

    $('#submit').on('click', function() {
        let elevation = $('#elevation').val()
        let withinX = $('#within-x').val()
        let starting = $('#starting').val()
        let destination = $('#destination').val()
        console.log(`elevation:${elevation}\nwithinX:${withinX} \nstarting: ${starting} \ndestination: ${destination}`);
    });

    var mymap = L.map('mapid').setView([51.505, -0.09], 13);

})
