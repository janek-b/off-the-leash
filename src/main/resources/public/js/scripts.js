function initMap() {
  var myLatLng = {lat: 45.53325, lng: -122.70393};
  var portland = {lat: 45.5230622, lng: -122.6764816}

  var map = new google.maps.Map(document.getElementById('map'), {
    center: portland,
    scrollwheel: false,
    zoom: 11
  });

  var marker = new google.maps.Marker({
    map: map,
    position: myLatLng,
    title: 'test'
  });
}


$(function() {
})
