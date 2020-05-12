(function() {
  var __sections__ = {};
  (function() {
    for(var i = 0, s = document.getElementById('sections-script').getAttribute('data-sections').split(','); i < s.length; i++)
      __sections__[s[i]] = true;
  })();
  (function() {
  if (!__sections__["ss-facebook-message"] && !window.DesignMode) return;
  try {
    
	jQuery(document).ready(function($) {
		$(document).on('click', '.ss-fb-ms-bottom', function() {
			$('.ss-fb-ms-inner').show();
			$(this).hide();
		})

		$(document).on('click', '.offline_heading', function() {
			$('.ss-fb-ms-inner').hide();
			$('.ss-fb-ms-bottom').show();
		})
	})

  } catch(e) { console.error(e); }
})();

})();
