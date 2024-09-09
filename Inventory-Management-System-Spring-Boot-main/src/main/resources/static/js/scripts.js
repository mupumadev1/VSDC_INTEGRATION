/*!
    * Start Bootstrap - SB Admin v6.0.2 (https://startbootstrap.com/template/sb-admin)
    * Copyright 2013-2020 Start Bootstrap
    * Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-sb-admin/blob/master/LICENSE)
    */
    (function($) {
    "use strict";
        const popUp = document.getElementById('loadStandardCodes')
        if (popUp) {
            popUp.addEventListener('click', function (e) {
                e.preventDefault();
                var loadingModal = new bootstrap.Modal(document.getElementById('loadingModal'));
                loadingModal.show();
                fetch('http://localhost:8000/api/create-packaging-unit')
                    .then(response => response.json())
                    .then(data => {
                        // Handle the response (if needed)
                        console.log(data);

                        // Optionally, you can redirect or update the page
                        // window.location.href = data.redirectUrl;

                        // Hide the loading modal
                        loadingModal.hide();
                    })
                    .catch(error => {
                        // Handle errors (if needed)
                        console.error(error);

                        // Hide the loading modal
                        loadingModal.hide();
                    });

            })
        }

    // Add active state to sidbar nav links
    var path = window.location.href; // because the 'href' property of the DOM element is the absolute path
        $("#layoutSidenav_nav .sb-sidenav a.nav-link").each(function() {
            if (this.href === path) {
                $(this).addClass("active");
            }
        });

    // Toggle the side navigation
    $("#sidebarToggle").on("click", function(e) {
        e.preventDefault();
        $("body").toggleClass("sb-sidenav-toggled");
    });
})(jQuery);