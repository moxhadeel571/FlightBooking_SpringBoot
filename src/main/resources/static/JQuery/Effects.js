
$(document).ready(function() {
    var element=document.getElementById("#changed");
    $("#changed").on("click", ".btn-close", function() {
        $(this).closest(".added-content").remove();
    });


    $('#AddRoundTrip').on("click", function() {
        var change = $("<div class='added-content'>" +
            "<button class='btn-close' id='close_cross' style='position: absolute; right: 0' type='button' class='btn-close' aria-label='Close'>&times;</button>" +
            "<div class=\"row\">\n" +
            "\t\t\t\t\t\t\t\t\t<div class=\"col-md-6\">\n" +
            "\t\t\t\t\t\t\t\t\t\t<div class=\"form-group\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<span class=\"form-label\">Flying from</span>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<input class=\"form-control\" type=\"text\" placeholder=\"City or airport\">\n" +
            "\t\t\t\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t\t\t\t<div class=\"col-md-6\">\n" +
            "\t\t\t\t\t\t\t\t\t\t<div class=\"form-group\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<span class=\"form-label\">Flyning to</span>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<input class=\"form-control\" type=\"text\" placeholder=\"City or airport\">\n" +
            "\t\t\t\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t\t\t<div class=\"row\">\n" +
            "\t\t\t\t\t\t\t\t\t<div class=\"col-md-6\">\n" +
            "\t\t\t\t\t\t\t\t\t\t<div class=\"form-group\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<span class=\"form-label\">Departing</span>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<input class=\"form-control\" type=\"date\" required>\n" +
            "\t\t\t\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t\t\t\t<div class=\"col-md-6\">\n" +
            "\t\t\t\t\t\t\t\t\t\t<div class=\"form-group\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<span class=\"form-label\">Returning</span>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<input class=\"form-control\"  type=\"date\" required>\n" +
            "\t\t\t\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t\t\t\t<div class=\"col-md-6\">\n" +
            "\t\t\t\t\t\t\t\t\t\t<div class=\"form-group\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<span class=\"form-label\">Add Other Place</span>\n" +
            "\t\t\t\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t\t\t</div>")

       $("#changed").append(change);
        console.log(change);
    })

    $('#one-way').on("change", function() {
        if ($(this).prop('checked')) {
            $('#AddRoundTrip').hide();
            $('#one_way').hide();
        }
    });

    $('#roundtrip').on("change", function() {
        if ($(this).prop('checked')) {
            $('#AddRoundTrip').hide();
            $('#one_way').show();

        }
    });
    $('#multi-city').on("change", function() {
        if ($(this).prop('checked')) {
            $('#AddRoundTrip').show();
            $('#one_way').show();

        }
    });












});