$(function() {
  $('#editParkModal').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget)
    var parkId = button.data('id')
    var parkName = button.data('name')
    var parkLocation = button.data('location')
    var parkSize = button.data('size')
    var parkFenced = button.data('fenced')
    var parkSmall = button.data('small')

    var modal = $(this)
    modal.find('.modal-title').text('Edit ' + parkName)
    modal.find('#editParkForm').attr("action", "/parks/"+parkId+"/update")
    modal.find('#deleteParkForm').attr("action", "/parks/"+parkId+"/delete")
    modal.find('#name').val(parkName)
    modal.find('#location').val(parkLocation)
    modal.find('#size option[value="'+parkSize+'"]').prop('selected', true)
    modal.find('#fenced option[value="'+parkFenced+'"]').prop('selected', true)
    modal.find('#small option[value="'+parkSmall+'"]').prop('selected', true)
  })

})
