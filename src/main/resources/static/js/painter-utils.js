(function () {
    if (window.painterUtils !== undefined) {
        return;
    }

    const painterUtils = {};

    painterUtils.templates = {};
    painterUtils.init = function () {
        painterUtils.templates.painterRecord = document.querySelector('#template-painter').content;
        const createPainterView = document.body.querySelector('.edit-painter-wrapper');
        painterUtils.store.createPainterView = createPainterView;
        createPainterView.style.display = 'none';

        const dropDown = createPainterView.querySelector('.studios-dropdown-wrapper');
        const dropDownGallery = createPainterView.querySelector('.galleries-dropdown-wrapper');

        dropDown.addEventListener('click', (event) => {
            const studioField = document.body.querySelector('.edit-painter-studio-name');
            studioField.innerText = event.target.innerText;
            dropDown.classList.add('display-none');
            event.preventDefault();
            event.stopImmediatePropagation();
        });
        dropDownGallery.addEventListener('click', (event) => {
            const galleryField = document.body.querySelector('.edit-painter-gallery-name');
            galleryField.innerText = event.target.innerText;
            dropDownGallery.classList.add('display-none');
            event.preventDefault();
            event.stopImmediatePropagation();
        });

        document.body.addEventListener('click', () => {
            dropDown.classList.add('display-none');
        });
        document.body.addEventListener('click', () => {
            dropDownGallery.classList.add('display-none');
        });
        dropDown.classList.add('display-none');
        dropDownGallery.classList.add('display-none');

        createPainterView.querySelector('.edit-field-studio')
            .addEventListener('click', function (event) {
                const dropDown = document.body.querySelector('.studios-dropdown-wrapper');
                dropDown.classList.remove('display-none');
                const studios = document.body.querySelector('.studios-dropdown');
                studios.innerHTML = '';
                painterUtils.store.studios.forEach((s) => {
                    let flag = 1;
                    painterUtils.store.painters.forEach((p) => {
                        if (p.studio.name === s.name) {
                            if (!(s.name === document.body.querySelector('.edit-painter-studio-name'))) {
                                flag = 0;
                            }
                        }
                    })

                    if (flag === 1) {
                        studios.append(painterUtils.mStudio.createDropDownElement(s.name));
                    }
                });
                event.preventDefault();
                event.stopImmediatePropagation();
            });

        createPainterView.querySelector('.edit-field-gallery')
            .addEventListener('click', function (event) {

                const dropDownGallery = document.body.querySelector('.galleries-dropdown-wrapper');
                dropDownGallery.classList.remove('display-none');
                const galleries = document.body.querySelector('.galleries-dropdown');
                galleries.innerHTML = '';
                painterUtils.store.galleries.forEach((g) => {
                    galleries.append(painterUtils.mGallery.createDropDownElement(g.name));
                });
                event.preventDefault();
                event.stopImmediatePropagation();
            });


        createPainterView.querySelector('.edit-painter-cancel-btn')
            .addEventListener('click', () => {
                painterUtils.spinner.fullScreenOverlay.style.display = 'none';
                painterUtils.store.createPainterView.style.display = 'none';
            });

        createPainterView.querySelector('.edit-painter-save-btn')
            .addEventListener('click', function () {
                painterUtils.spinner.fullScreenOverlay.style.display = 'none';
                painterUtils.store.createPainterView.style.display = 'none';
                const name = document.body.querySelector('.edit-painter-name').value;
                const studioField = document.body.querySelector('.edit-painter-studio-name');
                const galleryField = document.body.querySelector('.edit-painter-gallery-name');
                const paintingsField = document.body.querySelector('.edit-painter-paintings');
                const std = painterUtils.store.studios.find(
                    (s) => s.name === studioField.innerText
                );
                const gllr = painterUtils.store.galleries.find(
                    (g) => g.name === galleryField.innerText
                );
                let painter = {
                    name: name,
                    studio: std,
                    gallery: gllr,
                    paintings: null
                };

                if (painterUtils.store.editPainter) {
                    const editPainter = painterUtils.store.editPainter;
                    painter.id = editPainter.id;
                    painter.paintings = editPainter.paintings;
                    painterUtils.ajax
                        .updatePainter(painter)
                        .then(
                            (data) => {
                                painterUtils.painter.refreshTable();
                            },
                            (error) => console.error(error)
                        );
                } else {
                    painterUtils.ajax
                        .savePainter(painter)
                        .then(
                            (data) => {
                                painterUtils.painter.refreshTable();
                            },
                            (error) => console.error(error)
                        );
                }
            });

        document.body.querySelector('.create-painter')
            .addEventListener('click', () => {
                painterUtils.painter.createNew(false);
            });

        document.body.querySelector('.refresh-painters')
            .addEventListener('click', () => {
                painterUtils.painter.refreshTable();
            });

        painterUtils.store.painterTable = document.querySelector('.table-content');
    };

    painterUtils.store = {
        studios: [],
        galleries: [],
        paintings: [],
        painters: [],
        painterViews: [],
        editPainter: null
    };

    painterUtils.spinner = {};
    painterUtils.spinner.spinnerContainerClassName = 'spinner-container';
    painterUtils.spinner.spinnerClassName = 'spinner';

    painterUtils.spinner.createSpinner = function () {
        if (document.body.querySelector(`.${this.spinnerContainerClassName}`)) {
            return;
        }

        this.fullScreenOverlay = document.querySelector('.full-screen-overlay');
        this.fullScreenOverlay.style.display = 'none';
        const spinnerContainer = document.createElement('div');
        spinnerContainer.classList.add(this.spinnerContainerClassName);

        const spinner = document.createElement('div');
        spinner.classList.add(this.spinnerClassName);
        spinnerContainer.append(spinner);

        spinner.innerHTML = ` <div class="sk-grid">
                                   <div class="sk-grid-cube"></div>
                                   <div class="sk-grid-cube"></div>
                                   <div class="sk-grid-cube"></div>
                                   <div class="sk-grid-cube"></div>
                                   <div class="sk-grid-cube"></div>
                                   <div class="sk-grid-cube"></div>
                                   <div class="sk-grid-cube"></div>
                                   <div class="sk-grid-cube"></div>
                                   <div class="sk-grid-cube"></div>
                               </div>
                             `;
        document.body.prepend(spinnerContainer);
        painterUtils.spinner.spinnerContainer = spinnerContainer;
    };
    painterUtils.spinner.showSpinner = function () {
        this.fullScreenOverlay.style.display = '';
        this.spinnerContainer.style.display = '';
    };
    painterUtils.spinner.hideSpinner = function () {
        this.fullScreenOverlay.style.display = 'none';
        this.spinnerContainer.style.display = 'none';
    };


    painterUtils.ajax = {
        baseUrl: '',
        get studiosUrl() {
            return this.baseUrl + '/studios';
        },
        get paintersUrl() {
            return this.baseUrl + '/painters';
        },
        get paintingsUrl() {
            return this.baseUrl + '/paintings';
        },
        get galleriesUrl() {
            return this.baseUrl + '/galleries';
        },
    };
    painterUtils.ajax.getStudios = function () {
        painterUtils.spinner.showSpinner();

        return window.axios
            .get(this.studiosUrl)
            .then((response) => {
                painterUtils.store.studios = response.data;
                painterUtils.spinner.hideSpinner();
            })
            .catch((err) => {
                console.error(err);
                painterUtils.spinner.hideSpinner();
            });
    };
    painterUtils.ajax.getGalleries = function () {
        painterUtils.spinner.showSpinner();
        return window.axios
            .get(this.galleriesUrl)
            .then((response) => {
                painterUtils.store.galleries = response.data;
                painterUtils.spinner.hideSpinner();
            })
            .catch((err) => {
                console.error(err);
                painterUtils.spinner.hideSpinner();
            });
    };

    painterUtils.ajax.getPaintings = function () {
        painterUtils.spinner.showSpinner();

        return window.axios
            .get(this.paintingsUrl)
            .then((response) => {
                painterUtils.store.paintings = response.data;
                painterUtils.spinner.hideSpinner();
            })
            .catch((err) => {
                console.error(err);
                painterUtils.spinner.hideSpinner();
            });
    };

    painterUtils.ajax.getPainters = function () {
        painterUtils.spinner.showSpinner();
        return window.axios
            .get(this.paintersUrl)
            .then((response) => {
                painterUtils.store.painters = response.data;
                painterUtils.spinner.hideSpinner();
            })
            .catch((err) => {
                console.error(err);
                painterUtils.spinner.hideSpinner();
            });
    };

    //return promise
    painterUtils.ajax.getPainterById = function (id) {
        return window.axios.get(`${this.paintersUrl}/${id}`)
    };

    //return promise
    painterUtils.ajax.deletePainterById = function (id) {
        return window.axios.delete(`${this.paintersUrl}/${id}`);
    };

    //return promise
    painterUtils.ajax.savePainter = function (painter) {
        return window.axios.post(`${this.paintersUrl}`, painter);
    };

    //return promise
    painterUtils.ajax.updatePainter = function (painter) {
        return window.axios.put(`${this.paintersUrl}/${painter.id}`, painter);
    };

    painterUtils.painter = {};
    painterUtils.painter.refreshTable = function () {
        painterUtils.ajax.getPainters().then((data) => {
            painterUtils.painter.cleanTable();
            painterUtils.painter.fillTable();
        })
    };

    painterUtils.painter.edit = function (painter) {
        painterUtils.painter.createNew(true, painter);
    };
    painterUtils.painter.delete = function (painter) {
        painterUtils.spinner.showSpinner();
        painterUtils.ajax.deletePainterById(painter.id)
            .then((response) => {
                painterUtils.spinner.hideSpinner();
                painterUtils.painter.refreshTable();
            })
            .catch((err) => {
                console.error(err);
                painterUtils.spinner.hideSpinner();
            });
    };

    painterUtils.painter.createPainter = function (painter) {
        const viewTemplate = painterUtils.templates.painterRecord;
        viewTemplate.querySelector('.painter-studio').innerText = painter.studio.name;
        viewTemplate.querySelector('.painter-gallery').innerText = painter.gallery.name;
        viewTemplate.querySelector('.painter-name').innerText = painter.name;
        viewTemplate.querySelector('.painter-paintings').innerText = "";
        painter.paintings.forEach(
            (p) => viewTemplate.querySelector('.painter-paintings').innerText =
                viewTemplate.querySelector('.painter-paintings').innerText.concat(p.name).concat("; ")
        );
        let view = document.importNode(viewTemplate, true);
        view.painterId = painter.id;
        view.querySelector('.painter-edit-btn')
            .addEventListener('click', function (event) {
                painterUtils.painter.edit(painter)
            });
        view.querySelector('.painter-delete-btn')
            .addEventListener('click', function () {
                painterUtils.painter.delete(painter);
            });

        painterUtils.store.painterViews.push(view);

        return view;
    };

    painterUtils.painter.createNew = function (edit, painter) {
        painterUtils.store.createPainterView.style.display = '';
        painterUtils.spinner.fullScreenOverlay.style.display = '';
        const nameField = document.body.querySelector('.edit-painter-name');
        const paintingsField = document.body.querySelector('.edit-painter-paintings');
        const studioField = document.body.querySelector('.edit-painter-studio-name');
        const galleryField = document.body.querySelector('.edit-painter-gallery-name');
        if (edit) {

            painter.paintings.forEach(
                (p) => paintingsField.value = paintingsField.value.concat(p.name).concat("; ")
            );
            nameField.value = painter.name;
            studioField.innerText = painter.studio.name;
            galleryField.innerText = painter.gallery.name;
            painterUtils.store.editPainter = painter;
        } else {
            paintingsField.value = '';
            studioField.innerText = '';
            galleryField.innerText = '';
            nameField.value = '';
            painterUtils.store.editPainter = null;
        }
    };

    painterUtils.painter.fillTable = function () {
        painterUtils.store.painters.forEach((painter) => {
            painterUtils.store.painterTable.append(painterUtils.painter.createPainter(painter));
        })
    };

    painterUtils.painter.cleanTable = function () {
        painterUtils.store.painterTable.innerHTML = '';
    };

    painterUtils.mStudio = {};

    painterUtils.mStudio.createDropDownElement = function createStudio(name) {
        const mStudio = document.createElement('div');
        mStudio.classList.add('dropdown-studio');
        mStudio.innerText = name;
        return mStudio;
    };

    painterUtils.mGallery = {};

    painterUtils.mGallery.createDropDownElement = function createStudio(name) {
        const mGallery = document.createElement('div');
        mGallery.classList.add('dropdown-gallery');
        mGallery.innerText = name;
        return mGallery;
    };

    window.painterUtils = painterUtils;
}());
