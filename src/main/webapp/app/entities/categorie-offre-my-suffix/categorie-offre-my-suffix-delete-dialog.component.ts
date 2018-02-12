import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CategorieOffreMySuffix } from './categorie-offre-my-suffix.model';
import { CategorieOffreMySuffixPopupService } from './categorie-offre-my-suffix-popup.service';
import { CategorieOffreMySuffixService } from './categorie-offre-my-suffix.service';

@Component({
    selector: 'jhi-categorie-offre-my-suffix-delete-dialog',
    templateUrl: './categorie-offre-my-suffix-delete-dialog.component.html'
})
export class CategorieOffreMySuffixDeleteDialogComponent {

    categorieOffre: CategorieOffreMySuffix;

    constructor(
        private categorieOffreService: CategorieOffreMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.categorieOffreService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'categorieOffreListModification',
                content: 'Deleted an categorieOffre'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-categorie-offre-my-suffix-delete-popup',
    template: ''
})
export class CategorieOffreMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private categorieOffrePopupService: CategorieOffreMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.categorieOffrePopupService
                .open(CategorieOffreMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
