document.addEventListener("DOMContentLoaded", function () {
    const painterUtils = window.painterUtils;
    painterUtils.init();
    painterUtils.spinner.createSpinner();
    painterUtils.spinner.hideSpinner();
    painterUtils.ajax.getStudios();
    painterUtils.ajax.getGalleries();
    painterUtils.ajax.getPaintings();
    painterUtils.painter.refreshTable();
});
