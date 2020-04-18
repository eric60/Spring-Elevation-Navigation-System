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
             let edges = msg;
             console.log(edges)
             initMapRoute(edges)
        });
    }


    // -----------------------  Map methods -------------------------
    let secretMapBoxToken = "sk.eyJ1IjoiZXJpY3M5OCIsImEiOiJjazkzbHFvZ28" + "wMWhpM25tbTRjMnFtdW54In0.Ew68k87MaxIBw7wA9xIrVQ"
    let publicKey = "pk.eyJ1IjoiZXJpY3M5OCIsImEiOiJjazkzbGN5YXcwMWpkM25xbW1tcXp6aWdoIn0.71PxNNyzWdjMqzXXZcoTHg"
    mapboxgl.accessToken = publicKey;


    // A GeoJSON object with a LineString route from the White House to Capitol Hill
    var geojson = {
        'type': 'FeatureCollection',
        'features': [
            {
                'type': 'Feature',
                'geometry': {
                    'type': 'LineString',
                    'properties': {},
                    'coordinates': []
                }
            }
        ]
    };

    var map = new mapboxgl.Map({
        container: 'map',
        style: 'mapbox://styles/mapbox/light-v10',
        center: [-77.0214, 38.897],
        zoom: 12
    });

    var geocoder = new MapboxGeocoder({
        accessToken: mapboxgl.accessToken,
        mapboxgl: mapboxgl
    });

    function initMapRoute(edges) {
            geojson.features[0].geometry.coordinates = edges;

            map.addSource('LineString', {
                'type': 'geojson',
                'data': geojson
            });
            map.addLayer({
                'id': 'LineString',
                'type': 'line',
                'source': 'LineString',
                'layout': {
                    'line-join': 'round',
                    'line-cap': 'round'
                },
                'paint': {
                    'line-color': '#BF93E4',
                    'line-width': 5
                }
            });

    }



})
