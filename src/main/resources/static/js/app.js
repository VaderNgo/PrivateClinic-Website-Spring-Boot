
jQuery(document).ready(function($) {

	// Variables declarations
	var $wrapper = jQuery('.main-wrapper');
	var $pageWrapper = jQuery('.page-wrapper');
	var $slimScrolls = jQuery('.slimscroll');
	var $sidebarOverlay = jQuery('.sidebar-overlay');

	// Sidebar
	var Sidemenu = function() {
		this.$menuItem = jQuery('#sidebar-menu a');
	};

	// jQuery('#sidebar-menu a').on('click', function(e) {
	//
	// 	jQuery(this).addClass('active');
	// 	//jQuery(this).parent().addClass('active');
	// });

	function init() {
		var $this = Sidemenu;
		jQuery('#sidebar-menu a').on('click', function(e) {
			if(jQuery(this).parent().hasClass('submenu')) {
				e.preventDefault();
			}
		// 	if(!jQuery(this).hasClass('subdrop')) {
		// 		jQuery('ul', jQuery(this).parents('ul:first')).slideUp(350);
		// 		jQuery('a', jQuery(this).parents('ul:first')).removeClass('subdrop');
		// 		jQuery(this).next('ul').slideDown(350);
		// 		jQuery(this).addClass('subdrop');
		// 	} else if(jQuery(this).hasClass('subdrop')) {
		// 		jQuery(this).removeClass('subdrop');
		// 		jQuery(this).next('ul').slideUp(350);
		// 	}
		});
		jQuery('#sidebar-menu ul li.submenu a.active').parents('li:last').children('a:first').addClass('active').trigger('click');
	}
	// Sidebar Initiate
	init();

	// Sidebar overlay
	function sidebar_overlay($target) {
		if($target.length) {
			$target.toggleClass('opened');
			$sidebarOverlay.toggleClass('opened');
			jQuery('html').toggleClass('menu-opened');
			$sidebarOverlay.attr('data-reff', '#' + $target[0].id);
		}
	}

	// Mobile menu sidebar overlay
	jQuery(document).on('click', '#mobile_btn', function() {
		var $target = jQuery(jQuery(this).attr('href'));
		sidebar_overlay($target);
		$wrapper.toggleClass('slide-nav');
		jQuery('#chat_sidebar').removeClass('opened');
		return false;
	});

	// Chat sidebar overlay
	jQuery(document).on('click', '#task_chat', function() {
		var $target = jQuery(jQuery(this).attr('href'));
		console.log($target);
		sidebar_overlay($target);
		return false;
	});

	// Sidebar overlay reset
	$sidebarOverlay.on('click', function() {
		var $target = jQuery(jQuery(this).attr('data-reff'));
		if($target.length) {
			$target.removeClass('opened');
			jQuery('html').removeClass('menu-opened');
			jQuery(this).removeClass('opened');
			$wrapper.removeClass('slide-nav');
		}
		return false;
	});

	// Select 2
	if(jQuery('.select').length > 0) {
		jQuery('.select').select2({
			minimumResultsForSearch: -1,
			width: '100%'
		});
	}

	// Floating Label
	if(jQuery('.floating').length > 0) {
		jQuery('.floating').on('focus blur', function(e) {
			jQuery(this).parents('.form-focus').toggleClass('focused', (e.type === 'focus' || this.value.length > 0));
		}).trigger('blur');
	}

	// Right Sidebar Scroll
	if(jQuery('#msg_list').length > 0) {
		jQuery('#msg_list').slimscroll({
			height: '100%',
			color: '#878787',
			disableFadeOut: true,
			borderRadius: 0,
			size: '4px',
			alwaysVisible: false,
			touchScrollStep: 100
		});
		var msgHeight = jQuery(window).height() - 124;
		jQuery('#msg_list').height(msgHeight);
		jQuery('.msg-sidebar .slimScrollDiv').height(msgHeight);
		jQuery(window).resize(function() {
			var msgrHeight = jQuery(window).height() - 124;
			jQuery('#msg_list').height(msgrHeight);
			jQuery('.msg-sidebar .slimScrollDiv').height(msgrHeight);
		});
	}

	// Left Sidebar Scroll
	if($slimScrolls.length > 0) {
		$slimScrolls.slimScroll({
			height: 'auto',
			width: '100%',
			position: 'right',
			size: '7px',
			color: '#ccc',
			wheelStep: 10,
			touchScrollStep: 100
		});
		var wHeight = jQuery(window).height() - 60;
		$slimScrolls.height(wHeight);
		jQuery('.sidebar .slimScrollDiv').height(wHeight);
		jQuery(window).resize(function() {
			var rHeight = jQuery(window).height() - 60;
			$slimScrolls.height(rHeight);
			jQuery('.sidebar .slimScrollDiv').height(rHeight);
		});
	}

	// Page wrapper height
	var pHeight = jQuery(window).height();
	$pageWrapper.css('min-height', pHeight);
	jQuery(window).resize(function() {
		var prHeight = jQuery(window).height();
		$pageWrapper.css('min-height', prHeight);
	});

	// Datetimepicker
	if(jQuery('.datetimepicker').length > 0) {
		jQuery('.datetimepicker').datetimepicker({
			format: 'DD/MM/YYYY'
		});
	}

	// Datatable
	if(jQuery('.datatable').length > 0) {
		jQuery('.datatable').DataTable({
			"bFilter": false,
		});
	}

	// Bootstrap Tooltip
	if(jQuery('[data-toggle="tooltip"]').length > 0) {
		jQuery('[data-toggle="tooltip"]').tooltip();
	}

	// Mobile Menu
	jQuery(document).on('click', '#open_msg_box', function() {
		$wrapper.toggleClass('open-msg-box');
		return false;
	});

	// Lightgallery
	if(jQuery('#lightgallery').length > 0) {
		jQuery('#lightgallery').lightGallery({
			thumbnail: true,
			selector: 'a'
		});
	}

	// Incoming call popup
	if(jQuery('#incoming_call').length > 0) {
		jQuery('#incoming_call').modal('show');
	}

	// Summernote
	if(jQuery('.summernote').length > 0) {
		jQuery('.summernote').summernote({
			height: 200,
			minHeight: null,
			maxHeight: null,
			focus: false
		});
	}

	// Check all email
	jQuery(document).on('click', '#check_all', function() {
		jQuery('.checkmail').click();
		return false;
	});
	if(jQuery('.checkmail').length > 0) {
		jQuery('.checkmail').each(function() {
			jQuery(this).on('click', function() {
				if(jQuery(this).closest('tr').hasClass('checked')) {
					jQuery(this).closest('tr').removeClass('checked');
				} else {
					jQuery(this).closest('tr').addClass('checked');
				}
			});
		});
	}

	// Mail important
	jQuery(document).on('click', '.mail-important', function() {
		jQuery(this).find('i.fa').toggleClass('fa-star').toggleClass('fa-star-o');
	});

	// Dropfiles
	if(jQuery('#drop-zone').length > 0) {
		var dropZone = document.getElementById('drop-zone');
		var uploadForm = document.getElementById('js-upload-form');
		var startUpload = function(files) {
			console.log(files);
		};
		uploadForm.addEventListener('submit', function(e) {
			var uploadFiles = document.getElementById('js-upload-files').files;
			e.preventDefault();
			startUpload(uploadFiles);
		});
		dropZone.ondrop = function(e) {
			e.preventDefault();
			this.className = 'upload-drop-zone';
			startUpload(e.dataTransfer.files);
		};
		dropZone.ondragover = function() {
			this.className = 'upload-drop-zone drop';
			return false;
		};
		dropZone.ondragleave = function() {
			this.className = 'upload-drop-zone';
			return false;
		};
	}

	// Small Sidebar
	if(screen.width >= 992) {
		jQuery(document).on('click', '#toggle_btn', function() {
			if(jQuery('body').hasClass('mini-sidebar')) {
				jQuery('body').removeClass('mini-sidebar');
				jQuery('.subdrop + ul').slideDown();
			} else {
				jQuery('body').addClass('mini-sidebar');
				jQuery('.subdrop + ul').slideUp();
			}
			return false;
		});
		jQuery(document).on('mouseover', function(e) {
			e.stopPropagation();
			if(jQuery('body').hasClass('mini-sidebar') && jQuery('#toggle_btn').is(':visible')) {
				var targ = jQuery(e.target).closest('.sidebar').length;
				if(targ) {
					jQuery('body').addClass('expand-menu');
					jQuery('.subdrop + ul').slideDown();
				} else {
					jQuery('body').removeClass('expand-menu');
					jQuery('.subdrop + ul').slideUp();
				}
				return false;
			}
		});
	}
});
