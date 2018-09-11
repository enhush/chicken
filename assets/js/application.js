jQuery(document).ready(function () {

    $('[data-toggle="tooltip"]').tooltip();

    if (parseInt("${controllers.Users.passLength()}") < 6) {
        setTimeout(function () {
            $('div#diaPassLength').modal('show');
        }, 1000);
    }
    if ($('[data-rel="tooltip"]').length && $.fn.tooltip) {
        $('[data-rel="tooltip"]').tooltip();
    }
});