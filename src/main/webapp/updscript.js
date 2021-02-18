var wsocket;
function connect() {
    //wsocket = new WebSocket("ws://" + document.location.host + document.location.pathname + "dukeetf");
    wsocket = new WebSocket("ws://localhost:8085/billboard/dukeetf");
    wsocket.onmessage = onMessage;
}
function onMessage(evt) {
    $('table tbody').empty();
    let meetup = JSON.parse(evt.data);
    meetup.forEach(function (r) {
        var html = '<tr>' +
            '<td>'+r.trainNumber+'</td>' +
            '<td>'+r.arrivalTime+'</td>' +
            '<td>'+r.departureTime+'</td>' +
            '</tr>';
        $('table tbody').append(html);
    });
}
window.addEventListener("load", connect, false);