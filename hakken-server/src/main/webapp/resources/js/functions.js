// remap jQuery to $
(function($){})(window.jQuery);


/* trigger when page is ready */
$(document).ready(function (){

	// your functions go here
	prettyPrint();
	
	//TODO: implement a function that removes the selected value from the other
	//select boxes on the mapping screen
	//	$('.mapping').change(function() {
//		  
//		  var selectName = $(this).name();
//		  var selectedVal = $(this).val();
//		  $('.mapping').each(function(){
//			  if($(this).name() != selectName){
//				  $(this).find('[value="' + selectedVal + '"]').remove();
//			  }
//		  });
//	});

});


/* optional triggers

$(window).load(function() {
	
});

$(window).resize(function() {
	
});

*/