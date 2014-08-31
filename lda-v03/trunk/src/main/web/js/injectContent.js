/**
 * Created by thomassecondary on 25/08/2014.
 */
$(document).ready(function() {
    $.ajax({
        url: "/mistress/get/lucina.json",
        accepts: "application/json",
        success: function( data ) {
            $("#status").attr("class", "info");
            $("#status").html("Success");
            $("#sContent").html( data.mistressContentDecoded );
        },
        error: function( xhr, textStatus, errorThrown) {
            var msg = textStatus + " : " + errorThrown + " : " +  xhr.getAllResponseHeaders().toString();
            $("#error").html(msg);
        }
    });
});