import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CategorieOffreMySuffix } from './categorie-offre-my-suffix.model';
import { CategorieOffreMySuffixPopupService } from './categorie-offre-my-suffix-popup.service';
import { CategorieOffreMySuffixService } from './categorie-offre-my-suffix.service';

@Component({
    selector: 'jhi-categorie-offre-my-suffix-dialog',
    templateUrl: './categorie-offre-my-suffix-dialog.component.html'
})
export class CategorieOffreMySuffixDialogComponent implements OnInit {

    categorieOffre: CategorieOffreMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private categorieOffreService: CategorieOffreMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.categorieOffre.id !== undefined) {
            this.subscribeToSaveResponse(
                this.categorieOffreService.update(this.categorieOffre));
        } else {
            this.subscribeToSaveResponse(
                this.categorieOffreService.create(this.categorieOffre));
        }
    }

    private subscribeToSaveResponse(result: Observable<CategorieOffreMySuffix>) {
        result.subscribe((res: CategorieOffreMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CategorieOffreMySuffix) {
        this.eventManager.broadcast({ name: 'categorieOffreListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-categorie-offre-my-suffix-popup',
    template: ''
})
export class CategorieOffreMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private categorieOffrePopupService: CategorieOffreMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.categorieOffrePopupService
                    .open(CategorieOffreMySuffixDialogComponent as Component, params['id']);
            } else {
                this.categorieOffrePopupService
                    .open(CategorieOffreMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
