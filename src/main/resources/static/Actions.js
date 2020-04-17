$(document).ready(function() {
    let elevation, withinX, starting, destination;
    $('#submit').on('click', function() {
        elevation = $('#elevation').val()
        withinX = $('#within-x').val()
        starting = $('#starting').val()
        destination = $('#destination').val()
        console.log(`elevationPref:${elevation}\nwithinX:${withinX} \nstarting: ${starting} \ndestination: ${destination}`);
        postToSpring();

        initMapRoute();
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
                    'coordinates': [
                        [-77.0366048812866, 38.89873175227713],
                        [-77.03364372253417, 38.89876515143842],
                        [-77.03364372253417, 38.89549195896866],
                        [-77.02982425689697, 38.89549195896866],
                        [-77.02400922775269, 38.89387200688839],
                        [-77.01519012451172, 38.891416957534204],
                        [-77.01521158218382, 38.892068305429156],
                        [-77.00813055038452, 38.892051604275686],
                        [-77.00832366943358, 38.89143365883688],
                        [-77.00818419456482, 38.89082405874451],
                        [-77.00815200805664, 38.88989712255097]
                    ]
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

    function initMapRoute() {

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

    map.addControl(
        new MapboxGeocoder({
            accessToken: mapboxgl.accessToken,

            countries: 'us',

            // further limit results to the geographic bounds representing the region of
            // New South Wales
            bbox: [139.965, -38.03, 155.258, -27.839],

            // apply a client side filter to further limit results to those strictly within
            // the New South Wales region
            filter: function(item) {
                // returns true if item contains New South Wales region
                return item.context
                    .map(function(i) {
                        // id is in the form {index}.{id} per https://github.com/mapbox/carmen/blob/master/carmen-geojson.md
                        // this example attempts to find the `region` named `New South Wales`
                        return (
                            i.id.split('.').shift() === 'region' &&
                            i.text === 'New South Wales'
                        );
                    })
                    .reduce(function(acc, cur) {
                        return acc || cur;
                    });
            },
            mapboxgl: mapboxgl
        })
    );


})
