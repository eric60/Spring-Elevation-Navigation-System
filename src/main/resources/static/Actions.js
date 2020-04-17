$(document).ready(function() {
    let elevation, withinX, starting, destination;
    $('#submit').on('click', function() {
        elevation = $('#elevation').val()
        withinX = $('#within-x').val()
        starting = $('#starting').val()
        destination = $('#destination').val()
        console.log(`elevationPref:${elevation}\nwithinX:${withinX} \nstarting: ${starting} \ndestination: ${destination}`);
        postToSpring();
    });

    function postToSpring() {
        let elenaData = {
            elevationPref: elevation, withinX: withinX,
            starting: starting, destination: destination
        }
        $.ajax({
            method: "POST",
            url: "http://localhost:8080/submit",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(elenaData)
        })
        .done(function( msg ) {
            console.log(msg);
        });
    }


    // -----------------------  Map methods -------------------------
    var map = L.map('map')
        // .setView([51.505, -0.09], 13);

    let mapBoxToken = "sk.eyJ1IjoiZXJpY3M5OCIsImEiOiJjazkzbHFvZ28wMWhpM25tbTRjMnFtdW54In0.Ew68k87MaxIBw7wA9xIrVQ"
    let mapUrl  = `https://api.mapbox.com/styles/v1/mapbox/emerald-v8/tiles/{z}/{x}/{y}?access_token=${mapBoxToken}`

    L.tileLayer(mapUrl, {
        attribution: '© OpenStreetMap contributors'
    }).addTo(map);

    L.Routing.control({
        waypoints: [
            L.latLng(57.74, 11.94),
            L.latLng(57.6792, 11.949)
        ]
    }).addTo(map);

})
