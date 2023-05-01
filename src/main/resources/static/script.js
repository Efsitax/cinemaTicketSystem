// Koltuk sayısı kontrolü
$(document).ready(function() {
	$('.seat').click(function() {
		if ($(this).hasClass('empty')) {
			$(this).removeClass('empty');
			$(this).addClass('full');
		} else if ($(this).hasClass('full')) {
			$(this).removeClass('full');
			$(this).addClass('empty');
		}
	});
});



$(document).ready(function() {
	$('.koltuk').click(function() {
		var salon = $(this).data('salon');
		var koltuk = $(this).data('koltuk');

		if ($(this).hasClass('satin-alindi')) {
			alert('Bu koltuk zaten satın alındı!');
		} else {
			$(this).addClass('satin-alindi');

			if (salon == 1) {
				var kalanKoltukSayisi = parseInt($('#salon1-count').text()) - 1;
				$('#salon1-count').text(kalanKoltukSayisi);
			} else if (salon == 2) {
				var kalanKoltukSayisi = parseInt($('#salon2-count').text()) - 1;
				$('#salon2-count').text(kalanKoltukSayisi);
			}
		}
	});
});
