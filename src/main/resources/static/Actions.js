$(document).ready(function() {
    let elevation, withinX, starting, destination;

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
        let startCoord = [], endCoord = [];
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

        let start = edges[0];
        let end = edges[edges.length - 1];
        console.log('---- in init map')
        console.log(start);
        console.log(end)
        addStartandStopPoints(start, end)
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
                            'title': 'Mapbox DC',
                            'icon': 'monument'
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
        addPointColor("start", start)
        addPointColor("end", end)
    }

    function addPointColor(type, coords) {
        let color;
        if (type == "end") { color = "#f30"}
        else { color = "#3887be"}
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
                'circle-radius': 10,
                'circle-color': color
            }
        });
    }

})
