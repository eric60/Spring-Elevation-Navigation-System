$(document).ready(function() {
    let elevation, withinX, starting, destination;
    $('#submit').on('click', function() {
        elevation = $('#elevation').val()
        withinX = $('#within-x').val()
        starting = $('#starting').val()
        destination = $('#destination').val()
        console.log(`elevation:${elevation}\nwithinX:${withinX} \nstarting: ${starting} \ndestination: ${destination}`);
    });

    function postToSpring() {
        $.ajax({
            method: "POST",
            url: "some.php",
            data: { elevation: elevation, withinX: withinX, starting: starting, destination: destination }
        })
        .done(function( msg ) {
            alert( "Data Saved: " + msg );
        });
    }


    var mymap = L.map('mapid').setView([42.3868, 72.5301], 13);

    L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(mymap);

})
