$(document).ready(function() {
    // Input field and airport info container
    var airportInput = $('#airport_from');
    var airportInfoContainer = $('#airport-from');

    // Event listener for user input
    airportInput.on('input', function() {
        // Make an API call to get airport information based on the user input
        var name = airportInput.val();
        $.ajax({
            method: 'GET',
            url: 'https://api.api-ninjas.com/v1/airports?name=' + name,
            headers: { 'X-Api-Key': 'Qi3+CKG3ltx/hE8VMldMtQ==rRMZ1GIKyBX2Ph9O' },
            limit:10,
            contentType: 'application/json',
            success: function(result) {
                // Display suggestions
                displaySuggestions(result);
            },
            error: function ajaxError(jqXHR) {
                console.error('Error: ', jqXHR.responseText);
                // Handle errors if any
            }
        });
    });

    // Function to display suggestions
    function displaySuggestions(suggestions) {
        airportInfoContainer.empty(); // Clear previous suggestions
        if (suggestions.length > 0) {
            // Display each suggestion
            suggestions.forEach(function(airport) {
                var suggestionItem = $('<div class="suggestion-item card mb-2">' +
                    '<div class="card-body">' +
                    '<h5 class="card-title"> <i class="fas fa-plane" style="margin-right: 2px"></i> ' + airport.name + '</h5>' +
                    '<p class="card-text"> <i class="fas fa-location" style="margin-right: 2px;font-size: large"></i> ' + airport.city + ', ' + airport.country + '</p>' +
                    '</div>' +
                    '</div>');

                suggestionItem.on('click', function() {
                    // Handle user selection
                    handleSelection(airport);
                });
                airportInfoContainer.append(suggestionItem);
            });
        } else {
            airportInfoContainer.html('No matching airports found');
        }
    }



    // Function to handle user selection
    function handleSelection(airport) {
        // You can use the selected airport data here
        console.log('Selected Airport:', airport);
        // Fill the input field with the selected airport name
        airportInput.val(airport.name);
        // Clear suggestions
        airportInfoContainer.empty();
    }
});
$(document).ready(function() {
    // Input field and airport info container
    var airportInput = $('#airport_to');
    var airportInfoContainer = $('#airport-to');

    // Event listener for user input
    airportInput.on('input', function() {
        // Make an API call to get airport information based on the user input
        var name = airportInput.val();
        $.ajax({
            method: 'GET',
            url: 'https://api.api-ninjas.com/v1/airports?name=' + name,
            headers: { 'X-Api-Key': 'Qi3+CKG3ltx/hE8VMldMtQ==rRMZ1GIKyBX2Ph9O' },
            contentType: 'application/json',
            success: function(result) {
                // Display suggestions
                displaySuggestions(result);
            },
            error: function ajaxError(jqXHR) {
                console.error('Error: ', jqXHR.responseText);
                // Handle errors if any
            }
        });
    });

    // Function to display suggestions
    function displaySuggestions(suggestions) {
        airportInfoContainer.empty(); // Clear previous suggestions
        if (suggestions.length > 0) {
            // Display each suggestion
            suggestions.forEach(function(airport) {
                var suggestionItem = $('<div class="suggestion-item card mb-2">' +
                    '<div class="card-body">' +
                    '<h5 class="card-title"> <i class="fas fa-plane" style="margin-right: 2px"></i> ' + airport.name + '</h5>' +
                    '<p class="card-text"> <i class="fas fa-location" style="margin-right: 2px;font-size: large"></i> ' + airport.city + ', ' + airport.country + '</p>' +'</div>' +
                    '</div>');

                suggestionItem.on('click', function() {
                    // Handle user selection
                    handleSelection(airport);
                });
                airportInfoContainer.append(suggestionItem);
            });
        } else {
            airportInfoContainer.html('No matching airports found');
        }
    }

    // Function to handle user selection
    function handleSelection(airport) {
        // You can use the selected airport data here
        console.log('Selected Airport:', airport);
        // Fill the input field with the selected airport name
        airportInput.val(airport.name);
        // Clear suggestions
        airportInfoContainer.empty();
    }
});
