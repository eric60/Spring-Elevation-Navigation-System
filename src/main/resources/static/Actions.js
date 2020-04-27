$(document).ready(function() {
    let elevation, withinX, starting, destination;
    let test = [
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

    $('#submit').on('click', function() {
        elevation = $('#elevation').val()
        withinX = $('#within-x').val()
        starting = $('#starting').val()
        destination = $('#destination').val()
        console.log(`elevationPref:${elevation}\nwithinX:${withinX} \nstarting: ${starting} \ndestination: ${destination}`);

        let addresses = [starting, destination]
        getCoordinates(addresses)
    });

    function getCoordinates(addresses) {
        let promises = []

        for (let i = 0; i < addresses.length; i++) {
            let address = addresses[i];
            console.log("--" + address)
            promises[i] = getCoordinatesApi(address)
        }
        processPromises(promises)
    }

    function processPromises(promises) {
        Promise.all(promises).then(function(values) {
            console.log('------ Promises ------')
            console.log(values);
            let startCoord = [], endCoord = [];

            for (let i = 0; i < values.length; i++) {
                let json = values[i];
                let lat = json.results[0].locations[0].latLng.lat
                let long = json.results[0].locations[0].latLng.lng
                if (!lat || ! long) {
                    alert("Failed to get address. Please check and try again")
                    return;
                }
                if (i == 0) {
                    startCoord.push(lat);
                    startCoord.push(long);
                    console.log('----- Start coord ----')
                    console.log(startCoord)
                } else {
                    endCoord.push(lat);
                    endCoord.push(long);
                    console.log('----- End  coord ----')
                    console.log(endCoord)
                    postToSpring(startCoord, endCoord)
                }
            }
        })
    }

    async function getCoordinatesApi(address) {
        let KEY = "Gm02cmrNkRBukt" + "1wambjK5AmAnZGW0vr"
        try {
            const restAPI = `http://open.mapquestapi.com/geocoding/v1/address?key=${KEY}&location=${address}`
            const response = await fetch(restAPI);
            const json = await response.json();
            console.log(json);
            return json;
        } catch {

        }
    }

    function postToSpring(startCoord, endCoord) {
        console.log('----- In post to string ------')
        console.log(startCoord[0])

        let elenaData =
        {
            elevationPref: elevation,
            withinX: withinX,
            start: startCoord,
            end: endCoord
        }

        let data = JSON.stringify(elenaData);
        console.log(data)

        $.ajax({
            method: "POST",
            url: "http://localhost:8080/submit",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: data
        })
        .done(function( edges ) {
             console.log('------- Received edges --------')
             console.log(edges)
             if (edges.length == 0) {
                 handleEmptyRoute();
             }
             initMapRoute(edges)
        });
    }
    
    function handleEmptyRoute() {
        alert("Sorry, no route was found. Try increasing the within x% setting")
    }


    // -----------------------  Map methods -------------------------
    let secretMapBoxToken = "sk.eyJ1IjoiZXJpY3M5OCIsImEiOiJjazkzbHFvZ28" + "wMWhpM25tbTRjMnFtdW54In0.Ew68k87MaxIBw7wA9xIrVQ"
    let publicKey = "pk.eyJ1IjoiZXJpY3M5OCIsImEiOiJjazkzbGN5YXcwMWpkM25xbW1tcXp6aWdoIn0.71PxNNyzWdjMqzXXZcoTHg"
    mapboxgl.accessToken = publicKey;


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

    let umassCoord = [-72.526798, 42.39205];
    if (!map) {
        var map = new mapboxgl.Map({
            container: 'map',
            style: 'mapbox://styles/mapbox/light-v10',
            center: umassCoord,
            zoom: 12
        });
    }

    let lineStringId = 'LineString';
    let lineStringSourceId = 'LineString'
    let pointSourceId = 'points'
    let startLayerId = 'start'
    let endLayerId = 'end';

    function removeOld() {
        console.log("Trigger removing old")
        if (map.getLayer(lineStringId)){
            console.log("removing layer")
            map.removeLayer(lineStringId);
        }
        if (map.getSource(lineStringSourceId)){
            console.log("removing source")
            map.removeSource(lineStringSourceId);
        }
        if (map.getSource(pointSourceId)){
            console.log("removing points")
            map.removeSource(pointSourceId);
        }
        if (map.getLayer(startLayerId)){
            console.log("removing start layer point")
            map.removeLayer(startLayerId);
            map.removeSource(startLayerId);
        }
        if (map.getLayer(endLayerId)){
            console.log("removing end layer point")
            map.removeLayer(endLayerId);
            map.removeSource(endLayerId);
        }

    }

    function initMapRoute(edges) {
            removeOld();

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

        let start = edges[0];
        let end = edges[edges.length - 1];
        console.log('---- in init map')
        console.log(start);
        console.log(end)
        zoomToLine();
        addStartandStopPoints(start, end)
    }

    function zoomToLine() {
        var coordinates = geojson.features[0].geometry.coordinates;

        var bounds = coordinates.reduce(function(bounds, coord) {
            return bounds.extend(coord);
        }, new mapboxgl.LngLatBounds(coordinates[0], coordinates[0]));

        map.fitBounds(bounds, {
            padding: 20
        });
    }

    function addStartandStopPoints(start, end) {
        map.addSource('points', {
            'type': 'geojson',
            'data': {
                'type': 'FeatureCollection',
                'features': [
                    {
                        'type': 'Feature',
                        'geometry': {
                            'type': 'start',
                            'coordinates': start
                        },
                        'properties': {
                            'title': `<strong>${starting}</strong>`
                        }
                    },
                    {
                        'type': 'Feature',
                        'geometry': {
                            'type': 'end',
                            'coordinates': end
                        },
                        'properties': {
                            'title': 'Mapbox SF',
                            'icon': 'harbor'
                        }
                    }
                ]
            }
        });
        addPointColor(startLayerId, start)
        addPointColor(endLayerId, end)
        addPopup(startLayerId)
        addPopup(endLayerId)
    }

    function addPointColor(type, coords) {
        let color;
        if (type == "end") { color = "#f30"}
        else { color = "#32CD32"}
        map.addLayer({
            id: type,
            type: 'circle',
            source: {
                type: 'geojson',
                data: {
                    type: 'FeatureCollection',
                    features: [{
                        type: type,
                        properties: {},
                        geometry: {
                            type: 'Point',
                            coordinates: coords
                        }
                    }]
                }
            },
            paint: {
                'circle-radius': 15,
                'circle-color': color
            }
        });
    }

    function addPopup(type) {
        map.on('click', type, function(e) {
            var coordinates = e.features[0].geometry.coordinates.slice();
            let title;
            if (type == "start") {
                title = `<b>${starting}</b>`
            } else {
                title = `<b>${destination}</b>`
            }
           // var title = e.features[0].properties.title;

            // Ensure that if the map is zoomed out such that multiple
            // copies of the feature are visible, the popup appears
            // over the copy being pointed to.
            while (Math.abs(e.lngLat.lng - coordinates[0]) > 180) {
                coordinates[0] += e.lngLat.lng > coordinates[0] ? 360 : -360;
            }

            new mapboxgl.Popup()
                .setLngLat(coordinates)
                .setHTML(title)
                .addTo(map);
        });

        // Change the cursor to a pointer when the mouse is over the places layer.
        map.on('mouseenter', type, function() {
            map.getCanvas().style.cursor = 'pointer';
        });

        // Change it back to a pointer when it leaves.
        map.on('mouseleave', type, function() {
            map.getCanvas().style.cursor = '';
        });
    }

})
